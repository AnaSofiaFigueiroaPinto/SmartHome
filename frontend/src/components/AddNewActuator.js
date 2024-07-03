import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import {Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from '@mui/material';
import {addActuatorToDevice, fetchActuatorFunctionalities} from '../services/devices.service';
import {useAsyncFn, useAsync} from 'react-use';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import {MenuItem, Select, InputLabel, FormControl} from "@mui/material";

export default function AddNewActuator({open, deviceName, handleClose}) {
    const [success, setSuccess] = React.useState(false);
    const [functionalities, setFunctionalities] = React.useState([]);
    const [selectedFunctionality, setSelectedFunctionality] = React.useState('');

    const fetchFunctionalities = async () => {
        try {
            const data = await fetchActuatorFunctionalities();
            const actuatorFunctionalities = data._embedded.actuatorFunctionalityDTOList.map(item => ({
                id: item.id,
                name: item.actuatorFunctionalityName
            }));
            setFunctionalities(actuatorFunctionalities);
        } catch (error) {
            console.error("Failed to fetch actuator functionalities:", error);
            setFunctionalities([]);
        }
    };

    React.useEffect(() => {
        fetchFunctionalities();
    }, []);

    const [state, saveNewActuator] = useAsyncFn(async (payload) => {
        try {
            const result = await addActuatorToDevice(payload);
            setSuccess(true);
            setTimeout(() => {
                setSuccess(false);
                handleClose();
            }, 2000);
            return result;
        } catch (error) {
            throw error;
        }
    }, []);
    console.log(state.error);

    const handleSubmit = (event) => {
        event.preventDefault();
        const formElements = event.target.elements;

        const payload = {
            deviceName: deviceName,
            actuatorName: formElements.name.value,
            actuatorFunctionalityName: selectedFunctionality,
        };

        if (selectedFunctionality === 'IntegerSetter') {
            payload.upperLimitInt = parseInt(formElements.upperLimitInt.value) || 0;
            payload.lowerLimitInt = parseInt(formElements.lowerLimitInt.value) || 0;
        }

        if (selectedFunctionality === 'DecimalSetter') {
            payload.upperLimitDecimal = parseFloat(formElements.upperLimitDecimal.value) || 0.0;
            payload.lowerLimitDecimal = parseFloat(formElements.lowerLimitDecimal.value) || 0.0;
            payload.precision = parseInt(formElements.precision.value) || 0;
        }

        saveNewActuator(payload);
    };

    return (
        <Dialog
            open={open}
            onClose={handleClose}
            PaperProps={{
                component: 'form',
                onSubmit: handleSubmit,
            }}
        >
            <DialogTitle>Add new Actuator</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    <p>Add a new actuator to the device `{deviceName}`.</p>
                </DialogContentText>
                {
                    (state.loading === false && state.error != null)
                    && (
                        <Stack sx={{width: '100%'}} spacing={2}>
                            <Alert severity="error">{state.error?.response.data}</Alert>
                        </Stack>
                    )}
                {success && (
                    <Stack sx={{width: '100%'}} spacing={2}>
                        <Alert severity="success">Actuator added successfully!</Alert>
                    </Stack>
                )}
                <TextField
                    autoFocus
                    required
                    margin="dense"
                    id="name"
                    name="name"
                    label="Actuator Name"
                    type="text"
                    fullWidth
                    variant="standard"
                    InputLabelProps={{
                        style: { color: 'black' }, // Cor do label
                    }}
                    InputProps={{
                        style: {
                            color: 'black',
                        },
                        classes: {
                            underline: 'black',
                        },
                    }}
                />
                <FormControl fullWidth variant="standard" margin="dense" required>
                    <InputLabel id="actuatorFunctionality" id="actuatorFunctionality"
                                sx={{
                                    color: 'black',
                                    '&.Mui-focused': {
                                        color: 'black',
                                    },
                                }}
                    >Actuator Functionality Name</InputLabel>
                    <Select
                        labelId="actuatorFunctionality"
                        id="actuatorFunctionalityName"
                        value={selectedFunctionality}
                        onChange={(e) => setSelectedFunctionality(e.target.value)}
                        label="Actuator Functionality Name"
                    >
                        {functionalities.map((functionality) => (
                            <MenuItem key={functionality.id} value={functionality.name}>
                                {functionality.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
                {selectedFunctionality === 'IntegerSetter' && (
                    <>
                        <TextField
                            required
                            margin="dense"
                            id="upperLimitInt"
                            label="Maximum Limit (integer)"
                            type="number"
                            fullWidth
                            variant="standard"
                        />
                        <TextField
                            required
                            margin="dense"
                            id="lowerLimitInt"
                            label="Lower Limit (integer)"
                            type="number"
                            fullWidth
                            variant="standard"
                        />
                    </>
                )}
                {selectedFunctionality === 'DecimalSetter' && (
                    <>
                        <TextField
                            required
                            margin="dense"
                            id="upperLimitDecimal"
                            label="Maximum Limit (decimal)"
                            type="number"
                            inputProps={{ step: "0.01" }}
                            fullWidth
                            variant="standard"
                        />
                        <TextField
                            required
                            margin="dense"
                            id="lowerLimitDecimal"
                            label="Lower Limit (decimal)"
                            type="number"
                            inputProps={{ step: "0.01" }}
                            fullWidth
                            variant="standard"
                        />
                        <TextField
                            required
                            margin="dense"
                            id="precision"
                            label="Actuator Precision"
                            type="number"
                            fullWidth
                            variant="standard"
                        />
                    </>
                )}
            </DialogContent>
            <DialogActions>
                <Button
                    variant='contained'
                    onClick={handleClose}
                    sx={{backgroundColor: '#336666', '&:hover': {backgroundColor: '#336666'}}}
                >
                    Cancel
                </Button>
                <Button
                    variant='contained'
                    type="submit"
                    sx={{backgroundColor: '#336666', '&:hover': {backgroundColor: '#336666'}}}
                >
                    Add new actuator
                </Button>
            </DialogActions>
        </Dialog>
    );
}