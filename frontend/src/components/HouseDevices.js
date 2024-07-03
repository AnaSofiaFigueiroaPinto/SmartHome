import { useParams, Link } from "react-router-dom";
import { useAsync } from "react-use";
import { getHouseDevices } from "../services/house.service";
import * as React from "react";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import Button from '@mui/material/Button';
import DeviceMeasurements from "./DeviceMeasurements";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate } from 'react-router-dom';
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import { FormControlLabel, FormGroup, Switch } from "@mui/material";
import { deactivateDevice } from "../services/devices.service";
import { useState, useEffect } from "react";
import AddNewSensor from "./AddNewSensor";
import AddNewActuator from "./AddNewActuator";

export default function HouseDevices() {
    const { houseId } = useParams();
    const navigate = useNavigate();
    const [addSensorOpenDialog, setAddSensorOpen] = useState(false);
    const [addActuatorOpenDialog, setAddActuatorOpen] = useState(false);
    const [currentDevice, setCurrentDevice] = useState("");
    const [devices, setDevices] = useState([]);

    const { loading, error, value: initialDevices } = useAsync(async () => {
        return await getHouseDevices(houseId);
    }, [houseId]);

    useEffect(() => {
        if (initialDevices) {
            setDevices(initialDevices);
        }
    }, [initialDevices]);

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error loading devices: {error.message}</p>;
    }

    const handleViewMeasurementsClick = (deviceId) => {
        <DeviceMeasurements deviceId={deviceId}></DeviceMeasurements>;
        console.log(`View measurements for ${deviceId}`);
    };

    const handleAddSensorOpenDialog = (deviceName) => {
        setCurrentDevice(deviceName);
        setAddSensorOpen(true);
    };

    const handleAddSensorCloseDialog = () => {
        setAddSensorOpen(false);
    };

    const handleAddActuatorOpenDialog = (deviceName) => {
        setCurrentDevice(deviceName);
        setAddActuatorOpen(true);
    };

    const handleAddActuatorCloseDialog = () => {
        setAddActuatorOpen(false);
    };

    const handleDeactivateSwitch = async (device) => {
        if (device.status === 'ACTIVE') {
            const response = await deactivateDevice(device.deviceName);
            if (response) {
                setDevices((prevDevices) =>
                    prevDevices.map((d) =>
                        d.deviceName === device.deviceName ? { ...d, status: 'DEACTIVATED' } : d
                    )
                );
            }
        }
    };

    return (
        <div className="house-devices">
            <Box sx={{ display: 'flex', justifyContent: 'flex-start', mb: 2 }}>
                <IconButton onClick={() => navigate('/')}>
                    <ArrowBackIcon />
                </IconButton>
            </Box>
            <Paper style={{ height: 490, width: '100%', overflow: 'auto' }}>
                <Table stickyHeader sx={{ borderCollapse: 'separate', tableLayout: 'fixed' }}>
                    <TableHead>
                        <TableRow style={{ backgroundColor: '#DDEFEE' }}>
                            <TableCell sx={{ fontSize: '1.2rem' }}>Device</TableCell>
                            <TableCell sx={{ fontSize: '1.2rem' }}>Room</TableCell>
                            <TableCell sx={{ fontSize: '1.2rem' }}>Status</TableCell>
                            <TableCell sx={{ fontSize: '1.2rem' }}>Actions</TableCell>
                            <TableCell>
                                <Link to="/actuatorfunctionality/BlindSetter?type=map" style={{ textDecoration: 'none' }}>
                                    <Button
                                        variant="contained"
                                        style={{ backgroundColor: '#336666', color: 'white', margin: '10px' }}
                                    >
                                        View Blind Rollers
                                    </Button>
                                </Link>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {devices.map((row) => (
                            <TableRow key={row.deviceName}>
                                <TableCell>{row.deviceName}</TableCell>
                                <TableCell>{row.roomName}</TableCell>
                                <TableCell>
                                    <FormGroup>
                                        <FormControlLabel
                                            control={
                                                <Switch
                                                    checked={row.status === 'ACTIVE'}
                                                    onChange={() => handleDeactivateSwitch(row)}
                                                    disabled={row.status === 'DEACTIVATED'}
                                                    sx={{
                                                        '& .MuiSwitch-switchBase.Mui-checked': {
                                                            color: '#336666',
                                                            '&:hover': {
                                                                backgroundColor: 'rgba(51, 102, 102, 0.08)',
                                                            },
                                                        },
                                                        '& .MuiSwitch-switchBase.Mui-checked + .MuiSwitch-track': {
                                                            backgroundColor: '#336666',
                                                        },
                                                    }}
                                                />
                                            }
                                            label={row.status}
                                        />
                                    </FormGroup>
                                </TableCell>
                                <TableCell>
                                    <Box display="flex" flexDirection="column" gap={1}>
                                        <Box display="flex" gap={1}>
                                            <Button
                                                variant="contained"
                                                style={{
                                                    backgroundColor: '#336666',
                                                    color: 'white',
                                                    whiteSpace: 'nowrap',
                                                    minWidth: '115px',
                                                    ...(row.status === 'DEACTIVATED' ? { opacity: 0.5 } : {})
                                                }}
                                                onClick={() => handleAddSensorOpenDialog(row.deviceName)}
                                                disabled={row.status === 'DEACTIVATED'}
                                            >
                                                Add Sensor
                                            </Button>
                                            <Button
                                                variant="contained"
                                                style={{
                                                    backgroundColor: '#336666',
                                                    color: 'white',
                                                    whiteSpace: 'nowrap',
                                                    minWidth: '132px',
                                                    ...(row.status === 'DEACTIVATED' ? { opacity: 0.5 } : {})
                                                }}
                                                onClick={() => handleAddActuatorOpenDialog(row.deviceName)}
                                                disabled={row.status === 'DEACTIVATED'}
                                            >
                                                Add Actuator
                                            </Button>
                                        </Box>
                                        <Link to={`/devices/${row.deviceName}`} style={{ textDecoration: 'none' }}>
                                            <Button
                                                variant="contained"
                                                style={{ backgroundColor: '#336666', color: 'white', whiteSpace: 'nowrap' }}
                                                onClick={() => handleViewMeasurementsClick(row.deviceName)}
                                            >
                                                View Measurements
                                            </Button>
                                        </Link>
                                    </Box>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>
            <AddNewSensor open={addSensorOpenDialog} deviceName={currentDevice} handleClose={handleAddSensorCloseDialog} />
            <AddNewActuator open={addActuatorOpenDialog} deviceName={currentDevice} handleClose={handleAddActuatorCloseDialog} />
        </div>
    );
}
