import * as React from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import {useAsync} from "react-use";
import {Fragment, useEffect, useState} from "react";
// Import Material-Ui components
import {Table, TableBody, TableCell, TableHead, TableRow, Typography} from '@mui/material';
import {Button, IconButton, Switch, Collapse, Box} from '@mui/material';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import {FormGroup, FormControlLabel} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
// Import services that fetch backend data
import {getRoomDevices, getRoomDetails} from "../services/rooms.service";
import {getDeviceDetails, deactivateDevice} from "../services/devices.service";
// Import other components
import AddNewSensor from "./AddNewSensor";
import AddNewActuator from "./AddNewActuator";
import DeviceMeasurements from "./DeviceMeasurements";


export default function BasicTable() {
    const {roomID} = useParams();
    const [roomName, setRoomName] = useState("");
    const [devices, setDevices] = useState([]);
    const [addSensorOpenDialog, setAddSensorOpen] = useState(false);
    const [addActuatorOpenDialog, setAddActuatorOpen] = useState(false);
    const [currentDevice, setCurrentDevice] = useState("");
    const [openDevice, setOpenDevice] = useState(null);
    const navigate = useNavigate();

    // Get room Devices and respective details
    const {value, loading} = useAsync(async () => {
        const devicesList = await getRoomDevices(roomID);
        const result = [];
        for (const device of devicesList) {
            result.push(await getDeviceDetails(device.deviceName));
        }
        return result;
    }, [roomID]);

    // Fetch room details to get the room name
    useEffect(() => {
        const fetchRoomDetails = async () => {
            const roomDetails = await getRoomDetails(roomID);
            setRoomName(roomDetails.roomName);
        };
        fetchRoomDetails();
    }, [roomID]);

    useEffect(() => {
        if (value) {
            setDevices(value);
        }
    }, [value]);

    if (loading) {
        return <p>Loading...</p>;
    }

    const handleDeactivateSwitch = async (device) => {
        if (device.status === 'ACTIVE') {
            const response = await deactivateDevice(device.deviceName);
            if (response) {
                // Update the device status locally if the API request is successful
                const updatedDevices = devices.map((d) =>
                    d.deviceName === device.deviceName ? {...d, status: 'DEACTIVATED'} : d
                );
                setDevices(updatedDevices);
            }
        }
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

    const handleDeviceToggle = (deviceName) => {
        setOpenDevice(openDevice === deviceName ? null : deviceName);
    };

    return (
        <Fragment>
            <Box sx={{width: 450, margin: 'auto', backgroundColor: 'white', padding: 2, position: 'relative'}}>
                <Box sx={{display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: '16px'}}>
                    <IconButton onClick={() => navigate('/rooms')} sx={{ position: 'absolute', left: 0 }}>
                        <ArrowBackIcon />
                    </IconButton>
                    <Typography variant="h6">
                        {roomName}
                    </Typography>
                </Box>

                <Table sx={{width: '100%'}}>
                    <TableBody>
                        {devices.map((device) => (
                            <DeviceItem
                                key={device.deviceName}
                                device={device}
                                handleDeactivateSwitch={handleDeactivateSwitch}
                                handleAddSensorOpenDialog={handleAddSensorOpenDialog}
                                handleAddActuatorOpenDialog={handleAddActuatorOpenDialog}
                                isOpen={openDevice === device.deviceName}
                                onToggle={() => handleDeviceToggle(device.deviceName)}
                            />
                        ))}
                    </TableBody>
                </Table>
                <AddNewSensor open={addSensorOpenDialog} deviceName={currentDevice}
                              handleClose={handleAddSensorCloseDialog}/>
                <AddNewActuator open={addActuatorOpenDialog} deviceName={currentDevice}
                                handleClose={handleAddActuatorCloseDialog}/>
            </Box>
        </Fragment>
    );
}

const DeviceItem = ({device, handleDeactivateSwitch, handleAddSensorOpenDialog, handleAddActuatorOpenDialog, isOpen, onToggle}) => {
    const [open, setOpen] = React.useState(false)

    const handleViewMeasurementsClick = (deviceId) => {
        <DeviceMeasurements deviceId={deviceId} ></DeviceMeasurements>
        console.log(`View measurements for ${deviceId}`);
    };

    return (
        <React.Fragment>
            <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={onToggle}
                    >
                        {isOpen ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">
                    {device.deviceName}
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={isOpen} timeout="auto" unmountOnExit>
                        <Box sx={{margin: 1}}>
                            <Table size="small" aria-label="purchases">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Model</TableCell>
                                        <TableCell>{device.model}</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    <TableRow>
                                        <TableCell>Status</TableCell>
                                        <TableCell>
                                            <FormGroup>
                                                <FormControlLabel
                                                    control={
                                                        <Switch
                                                            checked={device.status === 'ACTIVE'}
                                                            onChange={() => handleDeactivateSwitch(device)}
                                                            disabled={device.status === 'DEACTIVATED'}
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
                                                    label={device.status}
                                                />
                                            </FormGroup>
                                        </TableCell>
                                    </TableRow>
                                    <TableRow>
                                        <TableCell colSpan={2} align="center">
                                            <Button
                                                variant="contained"
                                                style={{
                                                    backgroundColor: '#336666',
                                                    color: 'white', ...(device.status === 'DEACTIVATED' ? {opacity: 0.5} : {})
                                                }}
                                                onClick={() => handleAddSensorOpenDialog(device.deviceName)}
                                                disabled={device.status === 'DEACTIVATED'} // Disable button if status is DEACTIVATED
                                            >
                                                Add Sensor
                                            </Button>
                                            <Button
                                                variant="contained"
                                                style={{
                                                    backgroundColor: '#336666',
                                                    color: 'white', ...(device.status === 'DEACTIVATED' ? {opacity: 0.5} : {})
                                                }}
                                                onClick={() => handleAddActuatorOpenDialog(device.deviceName)}
                                                sx={{marginLeft: 2}}
                                                disabled={device.status === 'DEACTIVATED'} // Disable button if status is DEACTIVATED
                                            >
                                                Add Actuator
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                    <TableRow>
                                        <TableCell colSpan={2}> {}
                                            <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                                            <Link to={`/devices/${device.deviceName}`}>
                                                <Button
                                                    variant="contained"
                                                    style={{
                                                        backgroundColor: '#336666',
                                                        color: 'white', ...(device.status === 'DEACTIVATED' ? {opacity: 0.5} : {})
                                                    }}
                                                    sx={{marginLeft: 2}}
                                                    onClick={() => handleViewMeasurementsClick(device.deviceName)}
                                                >
                                                    View Measurements
                                                </Button>
                                            </Link>
                                            </Box>
                                        </TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>);
}