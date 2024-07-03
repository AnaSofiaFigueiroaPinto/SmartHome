import React, { useEffect } from 'react';
import Button from '@mui/material/Button';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import { useAsyncFn } from 'react-use';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import { addSensorToDevice, fetchSensorFunctionalities } from '../services/devices.service';

export default function AddNewSensor({ open, deviceName, handleClose }) {
    const [success, setSuccess] = React.useState(false);
    const [selectedFunctionality, setSelectedFunctionality] = React.useState('');
    const [functionalityOptions, setFunctionalityOptions] = React.useState([]);
    const [sensorName, setSensorName] = React.useState('');
    const [state, saveNewSensor] = useAsyncFn(async (payload) => {
        try {
            console.log('Sending payload to backend:', payload);
            const result = await addSensorToDevice(payload);
            console.log('Received response from backend:', result);
            setSuccess(true);
            setTimeout(() => {
                setSuccess(false);
                handleClose();
            }, 2000);
            return result;
        } catch (error) {
            console.error('Error communicating with backend:', error);
            throw error;
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await fetchSensorFunctionalities();
                const sensorFunctionalities = data._embedded.sensorFunctionalityDTOList.map(item => item.sensorFunctionalityName);
                setFunctionalityOptions(sensorFunctionalities);
            } catch (error) {
                console.error('Error fetching sensor functionalities:', error);
                setFunctionalityOptions([]);
            }
        };

        fetchData();
    }, []);

    const getErrorMessage = (error) => {
        if (error.response && error.response.data) {
            return JSON.stringify(error.response.data);
        } else if (error.message) {
            return error.message;
        } else {
            return "An error occurred";
        }
    };

    return (
        <Dialog
            open={open}
            onClose={handleClose}
            PaperProps={{
                component: 'form',
                onSubmit: (event) => {
                    event.preventDefault();
                    const payload = {
                        deviceID: deviceName,
                        sensorName: sensorName,
                        functionalityID: selectedFunctionality
                    };
                    saveNewSensor(payload);
                },
            }}
        >
            <DialogTitle>Add new Sensor</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    <p>Add a new sensor to the device `{deviceName}`.</p>
                </DialogContentText>
                {(state.loading === false && state.error != null) && (
                    <Stack sx={{ width: '100%' }} spacing={2}>
                        <Alert severity="error" sx={{ color: '#336666' }}>{getErrorMessage(state.error)}</Alert>
                    </Stack>
                )}
                {success && (
                    <Stack sx={{ width: '100%' }} spacing={2}>
                        <Alert severity="success" sx={{ color: '#336666' }}>Sensor added successfully!</Alert>
                    </Stack>
                )}
                <TextField
                    autoFocus
                    required
                    margin="dense"
                    id="sensorName"
                    name="sensorName"
                    label="Sensor Name"
                    type="text"
                    fullWidth
                    variant="standard"
                    value={sensorName}
                    onChange={(e) => setSensorName(e.target.value)}
                    InputLabelProps={{
                        style: { color: 'black', borderBottomColor: '#336666' },
                    }}
                    InputProps={{
                        style: {
                            color: 'black',
                            '& .MuiInput-underline:before': {
                                borderBottomColor: '#336666',
                            },
                            '& .MuiInput-underline:after': {
                                borderBottomColor: '#336666',
                            },
                            '& .MuiInput-underline:hover:not(.Mui-disabled):before': {
                                borderBottomColor: '#336666',
                            },
                        },
                    }}
                />
                <Select
                    required
                    value={selectedFunctionality}
                    onChange={(e) => setSelectedFunctionality(e.target.value)}
                    displayEmpty
                    fullWidth
                    variant="standard"
                    sx={{
                        color: 'black',
                        '& .MuiSelect-select': {
                            borderBottomColor: '#336666',
                        },
                        '&:hover .MuiSelect-select': {
                            borderBottomColor: '#336666',
                        },
                    }}
                    MenuProps={{
                        PaperProps: {
                            style: {
                                maxHeight: 200,
                                width: 250,
                                position: 'absolute',
                                top: '100%',
                            },
                        },
                        anchorOrigin: {
                            vertical: 'bottom',
                            horizontal: 'left',
                        },
                        transformOrigin: {
                            vertical: 'top',
                            horizontal: 'left',
                        },
                        getContentAnchorEl: null,
                    }}
                >
                    <MenuItem value="" disabled>
                        Select Sensor Functionality
                    </MenuItem>
                    {functionalityOptions.map((option, index) => (
                        <MenuItem key={index} value={option}>
                            {option}
                        </MenuItem>
                    ))}
                </Select>
            </DialogContent>
            <DialogActions>
                <Button
                    variant='contained'
                    onClick={handleClose}
                    sx={{ backgroundColor: '#336666', '&:hover': { backgroundColor: '#336666' } }}
                >
                    Cancel
                </Button>
                <Button
                    variant='contained'
                    type="submit"
                    sx={{ backgroundColor: '#336666', '&:hover': { backgroundColor: '#336666' } }}
                >
                    Add new sensor
                </Button>
            </DialogActions>
        </Dialog>
    );
}
