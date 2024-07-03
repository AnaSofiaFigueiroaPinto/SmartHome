import React, { useEffect, useState } from 'react';
import { getDeviceMeasurements } from '../services/devices.service';
import ResponsiveDateTimeRangePickers from './DateTimeRangeCalender';
import dayjs from 'dayjs';
import {
    TableContainer, Paper, Table, TableBody, TableCell,
    TableFooter, TablePagination, TableRow, Box, IconButton,
    TableHead
} from '@mui/material';
import {
    FirstPage as FirstPageIcon, KeyboardArrowLeft, KeyboardArrowRight,
    LastPage as LastPageIcon
} from '@mui/icons-material';
import PropTypes from "prop-types";

function CustomPaginationActionsTable(props) {
    const { count, page, rowsPerPage, onPageChange } = props;

    const handleFirstPageButtonClick = (event) => {
        onPageChange(event, 0);
    };

    const handleBackButtonClick = (event) => {
        onPageChange(event, page - 1);
    };

    const handleNextButtonClick = (event) => {
        onPageChange(event, page + 1);
    };

    const handleLastPageButtonClick = (event) => {
        onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
    };

    return (
        <Box sx={{ flexShrink: 0, ml: 2.5 }}>
            <IconButton onClick={handleFirstPageButtonClick} disabled={page === 0} aria-label="first page">
                <FirstPageIcon />
            </IconButton>
            <IconButton onClick={handleBackButtonClick} disabled={page === 0} aria-label="previous page">
                <KeyboardArrowLeft />
            </IconButton>
            <IconButton onClick={handleNextButtonClick} disabled={page >= Math.ceil(count / rowsPerPage) - 1} aria-label="next page">
                <KeyboardArrowRight />
            </IconButton>
            <IconButton onClick={handleLastPageButtonClick} disabled={page >= Math.ceil(count / rowsPerPage) - 1} aria-label="last page">
                <LastPageIcon />
            </IconButton>
        </Box>
    );
}

CustomPaginationActionsTable.propTypes = {
    count: PropTypes.number.isRequired,
    page: PropTypes.number.isRequired,
    rowsPerPage: PropTypes.number.isRequired,
    onPageChange: PropTypes.func.isRequired,
};

function DeviceMeasurements({ deviceId }) {

    const [measurements, setMeasurements] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedDate, setSelectedDate] = useState([dayjs('2024-03-01'), dayjs('2024-06-30')]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);

    useEffect(() => {
        const fetchMeasurements = async () => {
            try {
                const data = await getDeviceMeasurements(deviceId, selectedDate[0].toISOString(), selectedDate[1].toISOString());
                const measurements = data._embedded?.deviceMeasurementsDTOList || [];
                const result = [];
                for (const measurement of measurements) {
                    const details = await getDeviceMeasurements(deviceId, selectedDate[0].toISOString(), selectedDate[1].toISOString());
                    result.push(details);
                }
                console.log('Data:', data);
                setMeasurements(sensorFunctionalityDetails(data));
                setLoading(false);
            } catch (err) {
                setError(err.message);
                setLoading(false);
                console.error('Error:', err);
            }
        };

        fetchMeasurements();
    }, [deviceId, selectedDate]);

    const sensorFunctionalityDetails = (measurementsObj) => {
        return Object.keys(measurementsObj).map(sensorFunctionality => ({
            sensorFunctionality,
            values: measurementsObj[sensorFunctionality].map(item => item.valueWithUnit)
        }));
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    // Check if rows is an array, if not, provide an empty array
    const rowArray = Array.isArray(measurements) ? measurements : [];

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div id="measurements" className="device-measurements-section">
            <div
                component="h1"
                variant="h6"
                color="#336666" // Change the color of the text
                noWrap
                className="title"
                style={{
                    color: '#336666',
                    marginBottom: 5,
                    fontSize: '20px', // Change font size as needed
                    fontFamily: 'Calibri, serif', // Change font family as needed
                    fontWeight: 'bold', // Bold font
                    position: 'absolute', // Position text absolutely
                    top: '15%', // Align text vertically
                    left: '50%', // Align text horizontally
                    transform: 'translateX(-50%)', // Center text horizontally
                }}
            >
                {deviceId} Measurements from {selectedDate[0]?.format('DD-MM-YYYY HH:mm')} to {selectedDate[1]?.format('DD-MM-YYYY HH:mm')}
            </div>
            <div style={{marginTop: '50px', width: '820px'}}>
                <ResponsiveDateTimeRangePickers value={selectedDate} onChange={setSelectedDate}/>
            </div>
            <TableContainer component={Paper}>
                <Table aria-label="custom pagination table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Sensor Functionality</TableCell>
                            <TableCell>Measurement</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {(rowsPerPage > 0
                                ? rowArray.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                : rowArray
                        ).map((row) => (
                            <TableRow key={row.sensorFunctionality}>
                                <TableCell component="th" scope="row">
                                    {row.sensorFunctionality}
                                </TableCell>
                                {row.values.map((value, index) => (
                                    <TableRow key={index}>
                                        {value}
                                    </TableRow>
                                ))}
                            </TableRow>
                        ))}
                    </TableBody>
                    <TableFooter>
                        <TableRow>
                            <TablePagination
                                rowsPerPageOptions={[5, 10, 25, {label: 'All', value: -1}]}
                                colSpan={3}
                                count={rowArray.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                SelectProps={{
                                    inputProps: {
                                        'aria-label': 'rows per page',
                                    },
                                    native: true,
                                }}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                                ActionsComponent={CustomPaginationActionsTable}
                            />
                        </TableRow>
                    </TableFooter>
                </Table>
            </TableContainer>
        </div>
    );
}

export default DeviceMeasurements;