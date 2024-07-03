import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slider from '@mui/material/Slider';
import Box from '@mui/material/Box';
import { getCurrentState, updateState } from '../services/blindroller.service';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';

const marks = [
    { value: 0, label: 'CLOSE' },
    { value: 100, label: 'OPEN' },
];

function valuetext(value) {
    return `${value}%`;
}

export default function ManageBlindRoller({ open, handleClose, blindRoller }) {
    const [currentPosition, setCurrentPosition] = React.useState(0);
    const [sliderValue, setSliderValue] = React.useState(0);
    const [loading, setLoading] = React.useState(true);
    const [error, setError] = React.useState(null);
    const [alertMessage, setAlertMessage] = React.useState(null);

    React.useEffect(() => {
        if (blindRoller && blindRoller.deviceName) {
            const fetchCurrentState = async () => {
                try {
                    const currentState = await getCurrentState(blindRoller.deviceName);
                    const numericValue = parseInt(currentState, 10);
                    if (!isNaN(numericValue)) {
                        setCurrentPosition(numericValue);
                        setSliderValue(numericValue);
                    } else {
                        throw new Error("Invalid current state value");
                    }
                } catch (err) {
                    setError(err);
                } finally {
                    setLoading(false);
                }
            };

            fetchCurrentState();
        } else {
            setError(new Error("Invalid device ID"));
            setLoading(false);
        }
    }, [blindRoller]);

    const handleSubmit = async () => {
        if (!blindRoller || !blindRoller.deviceName) {
            setError(new Error("Invalid device ID"));
            return;
        }

        try {
            const newStateValue = sliderValue;
            await updateState(blindRoller.deviceName, newStateValue);
            setAlertMessage("New position set successfully!");
            setCurrentPosition(newStateValue);
        } catch (err) {
            setError(err);
            setAlertMessage("Failed to set new position");
        }
    };

    const handleAlertClose = () => {
        setAlertMessage(null);
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>
                {blindRoller && blindRoller.deviceName ? `${blindRoller.deviceName}` : ""}
            </DialogTitle>
            <DialogContent>
                <Grid container spacing={2} direction="column">
                    <Grid item>
                        <Typography variant="h6" gutterBottom>
                            Current position: {currentPosition}%
                        </Typography>
                    </Grid>
                    <Grid item>
                        <DialogContentText>
                            Open or close the blind roller:
                        </DialogContentText>
                    </Grid>
                    <Grid item>
                        <Box sx={{ width: 350, mt: 2 }}>
                            <Slider
                                aria-label="Always visible"
                                value={sliderValue}
                                onChange={(event, newValue) => setSliderValue(newValue)}
                                getAriaValueText={valuetext}
                                step={10}
                                marks={marks}
                                valueLabelDisplay="on"
                                sx={{
                                    '& .MuiSlider-thumb': {
                                        backgroundColor: '#336666',
                                    },
                                    '& .MuiSlider-track': {
                                        backgroundColor: '#336666',
                                    },
                                    '& .MuiSlider-rail': {
                                        backgroundColor: '#336666',
                                    },
                                    '& .MuiSlider-markLabel': {
                                        color: '#336666',
                                    },
                                    '& .MuiSlider-valueLabel': {
                                        color: '#336666',
                                        backgroundColor: 'rgb(186,227,227)', // Background color with opacity
                                        padding: '4px 8px', // Adjust padding for better visibility
                                    },
                                }}
                            />
                        </Box>
                    </Grid>
                    <Grid item>
                        {loading && <p>Loading current state...</p>}
                        {error && <p>Error: {error.message}</p>}
                    </Grid>
                </Grid>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} style={{ backgroundColor: '#336666', color: 'white' }}>Cancel</Button>
                <Button variant="contained" onClick={handleSubmit} style={{ backgroundColor: '#336666', color: 'white' }}>Submit</Button>
            </DialogActions>
            {alertMessage && (
                <Dialog open={true} onClose={handleAlertClose}>
                    <DialogContent>
                        <DialogContentText>{alertMessage}</DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleClose} style={{ backgroundColor: '#336666', color: 'white' }}>OK</Button>
                    </DialogActions>
                </Dialog>
            )}
        </Dialog>
    );
}
