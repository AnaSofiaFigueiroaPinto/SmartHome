import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import IconButton from '@mui/material/IconButton';
import Collapse from '@mui/material/Collapse';
import Box from '@mui/material/Box';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { useAsync } from 'react-use';
import { getBlindRollerList } from '../services/blindroller.service';
import { getDeviceDetails } from '../services/devices.service';
import {Button, Paper} from "@mui/material";
import ManageBlindRoller from "./ManageBlindRoller";
import { useState } from "react";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate, useParams } from 'react-router-dom';
import { ROUTES } from '../constants/routes';

export default function BasicTable() {
    const { houseID } = useParams();
    const [openRows, setOpenRows] = React.useState({});
    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedBlindRoller, setSelectedBlindRoller] = useState(null);
    const navigate = useNavigate();

    const handleOpenDialog = (blindRoller) => {
        setSelectedBlindRoller(blindRoller);
        setDialogOpen(true);
    };

    const handleCloseDialog = () => {
        setDialogOpen(false);
        setSelectedBlindRoller(null);
    };

    const { value, loading, error } = useAsync(async () => {
        try {
            const response = await getBlindRollerList();
            // Assuming response has the structure: { _embedded: { blindRollerDTOList: [...] } }
            const blindRollerList = response._embedded?.blindRollerDTOList || [];
            const result = [];
            for (const blindRoller of blindRollerList) {
                const details = await getDeviceDetails(blindRoller.deviceName);
                result.push(details);
            }
            return result;
        } catch (err) {
            console.error("Error fetching blind roller details:", err);
            throw err;
        }
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error loading data: {error.message}</p>;
    }

    if (!value || !Array.isArray(value)) {
        return <p>No data available</p>;
    }

    const handleRowClick = (deviceName) => {
        setOpenRows((prevState) => ({
            ...prevState,
            [deviceName]: !prevState[deviceName],
        }));
    };

    return (
        <div className="blind-roller">
            <Box sx={{ display: 'flex', justifyContent: 'flex-start', mb: 2 }}>
                <IconButton onClick={() => navigate(`${ROUTES.DEVICES.replace(':houseID', houseID)}`)}>
                    <ArrowBackIcon />
                </IconButton>
            </Box>
        <React.Fragment>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell />
                        <TableCell>Device </TableCell>
                        <TableCell>Room </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {value.map((blindRoller) => (
                        <React.Fragment key={blindRoller.deviceName}>
                            <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                                <TableCell>
                                    <IconButton
                                        aria-label="expand row"
                                        size="small"
                                        onClick={() => handleRowClick(blindRoller.deviceName)}
                                    >
                                        {openRows[blindRoller.deviceName] ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                                    </IconButton>
                                </TableCell>
                                <TableCell component="th" scope="row">
                                    {blindRoller.deviceName}
                                </TableCell>
                                <TableCell>{blindRoller.roomName}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
                                    <Collapse in={openRows[blindRoller.deviceName]} timeout="auto" unmountOnExit>
                                        <Box sx={{ margin: 1 }}>
                                            <Table size="small" aria-label="details">
                                                <TableBody>
                                                    <TableRow>
                                                        <TableCell>
                                                            <Button
                                                                variant="contained" style={{ backgroundColor: '#336666', color: 'white' }} size="small"
                                                                onClick={() => handleOpenDialog(blindRoller)}
                                                            >
                                                                Manage Blind Roller
                                                            </Button>
                                                        </TableCell>
                                                    </TableRow>
                                                </TableBody>
                                            </Table>
                                        </Box>
                                    </Collapse>
                                </TableCell>
                            </TableRow>
                        </React.Fragment>
                    ))}
                </TableBody>
            </Table>
            {selectedBlindRoller && (
                <ManageBlindRoller
                    open={dialogOpen}
                    blindRoller={selectedBlindRoller}
                    handleClose={handleCloseDialog}
                />
            )}
        </React.Fragment>
            </div>
    );
}
