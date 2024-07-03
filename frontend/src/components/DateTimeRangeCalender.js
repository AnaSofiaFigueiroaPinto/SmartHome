import * as React from 'react';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDateTimeRangePicker } from '@mui/x-date-pickers-pro/DesktopDateTimeRangePicker';
import { Box, Typography } from '@mui/material';
import TextField from "@mui/material/TextField";

export default function ResponsiveDateTimeRangePickers({ value, onChange }) {
    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 5 }}>
                <Box>
                    <Typography variant="h6" sx={{ color: '#336666', marginBottom: 3, marginTop: 5 }}>
                        Choose Your Date Range
                    </Typography>
                    <DesktopDateTimeRangePicker
                        value={ value }
                        onChange={ onChange }
                        format="DD-MM-YYYY HH:mm"
                        renderInput={(startProps, endProps) => (
                            <>
                                <TextField
                                    {...startProps}
                                    sx={{
                                        '& .MuiInputBase-input': { color: '#336666 !important' }, // Color for input text
                                        '& .MuiOutlinedInput-root': { borderColor: '#336666 !important' }, // Color for border
                                        '& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#336666 !important' }, // Color for focused border
                                        '& .MuiInputLabel-root': { color: '#336666 !important' }, // Color for label
                                        '& .MuiInputLabel-root.Mui-focused': { color: '#336666 !important' } // Color for focused label
                                    }}
                                />
                                <Box sx={{ mx: 2, color: '#336666' }}> to </Box>
                                <TextField
                                    {...endProps}
                                    sx={{
                                        '& .MuiInputBase-input': { color: '#336666 !important' }, // Color for input text
                                        '& .MuiOutlinedInput-root': { borderColor: '#336666 !important' }, // Color for border
                                        '& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline': { borderColor: '#336666 !important' }, // Color for focused border
                                        '& .MuiInputLabel-root': { color: '#336666 !important' }, // Color for label
                                        '& .MuiInputLabel-root.Mui-focused': { color: '#336666 !important' } // Color for focused label
                                    }}
                                />
                            </>
                        )}
                    />
                </Box>
            </Box>
        </LocalizationProvider>
    );
}