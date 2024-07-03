import React, {useEffect, useState} from "react";
import {Link, useNavigate} from 'react-router-dom';
import {Button, TableContainer, Table, TableBody, TableRow, TableCell, Paper, Typography, Box, Collapse, IconButton} from '@mui/material';
import {getRoomsList, getRoomDetails} from '../services/rooms.service';
import AddNewDevice from "./AddNewDevice";

import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

function RoomList() {
    const [rooms, setRooms] = useState([]);
    const [dialogOpen, setDialogOpen] = useState(false);
    const navigate = useNavigate();

    const handleOpenDialog = () => {
        setDialogOpen(true);
    };

    const handleCloseDialog = () => {
        setDialogOpen(false);
    };


    useEffect(() => {
        const fetchRooms = async () => {
            try {
                const data = await getRoomsList();
                setRooms(data);
            } catch (error) {
                console.error('Error:', error);
            }
        };
        fetchRooms();
    }, []);

    const [openRow, setOpenRow] = useState(null);

    const handleRowClick = async (roomName, index) => {
        if (openRow === index) {
            setOpenRow(null);
        } else {
            try {
                const roomDetails = await getRoomDetails(roomName);
                setRooms(prevRooms => {
                    const updatedRooms = [...prevRooms];
                    updatedRooms[index].details = {
                        roomName: roomDetails.roomName,
                        roomFloor: roomDetails.floorNumber,
                        length: roomDetails.roomLength,
                        width: roomDetails.roomWidth,
                        height: roomDetails.roomHeight
                    };
                    return updatedRooms;
                });
                setOpenRow(index);
            } catch (error) {
                console.error('Error fetching room details:', error);
            }
        }
    };

    return (
        <div className="room-list-container">
            <TableContainer component={Paper} style={{ width: '550px', margin: '0 auto' }}>
                <IconButton onClick={() => navigate('/ ')} sx={{ marginBottom: '16px', marginRight: '500px' }}>
                    <ArrowBackIcon />
                </IconButton>
            <Table aria-label="collapsible table">
                    <TableBody>
                        {rooms.map((room, index) => (
                            <React.Fragment key={room.roomName}>
                                <TableRow>
                                    <TableCell style={{textAlign: 'left'}}>
                                        <div style={{display: 'flex', alignItems: 'center'}}>
                                            <IconButton
                                                aria-label="expand room details"
                                                size="small"
                                                onClick={() => handleRowClick(room.roomName, index)}
                                            >
                                                {openRow === index ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                                            </IconButton>
                                            <span
                                                to={`/rooms/${room.roomName}`}
                                                className="room-link"
                                                style={{
                                                    marginLeft: '10px',
                                                    color: 'inherit',
                                                    textDecoration: 'none',
                                                    display: 'inline-block',
                                                    overflow: 'hidden',
                                                    textOverflow: 'ellipsis',
                                                    whiteSpace: 'nowrap',
                                                    verticalAlign: 'middle',
                                                    width: 'calc(100% - 40px)'
                                                }}
                                            >
                                                {room.roomName}
                                            </span>
                                        </div>
                                    </TableCell>
                                </TableRow>
                                <TableRow>
                                    <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={2}>
                                        <Collapse in={openRow === index} timeout="auto" unmountOnExit>
                                            <Box sx={{margin: 1}}>
                                                <Typography variant="h6" gutterBottom component="div">
                                                    Room Details
                                                </Typography>
                                                {room.details && (
                                                    <TableContainer component={Paper}>
                                                        <Table size="small" aria-label="room details">
                                                            <TableBody>
                                                                <TableRow>
                                                                    <TableCell component="th"
                                                                               scope="row">Floor</TableCell>
                                                                    <TableCell>{room.details.roomFloor}</TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell component="th" scope="row">Dimensions (L
                                                                        x W x H)</TableCell>
                                                                    <TableCell>{`${room.details.length}m x ${room.details.width}m x ${room.details.height}m`}</TableCell>
                                                                </TableRow>
                                                                <TableRow>
                                                                    <TableCell align = 'center'>
                                                                        <Link to={`/rooms/${room.roomName}/devices`}
                                                                              style={{textDecoration: 'none'}}>
                                                                            <Button variant="contained" style = {{backgroundColor: '#336666', color: 'white'}}
                                                                                    size="small">
                                                                                View Devices
                                                                            </Button>
                                                                        </Link>
                                                                    </TableCell>
                                                                    <TableCell>
                                                                    <Button
                                                                        variant="contained" style = {{backgroundColor: '#336666', color: 'white'}} size="small"
                                                                        onClick={() => handleOpenDialog(room.roomName)}
                                                                    >
                                                                        Add Device
                                                                    </Button>
                                                                    <AddNewDevice open={dialogOpen} roomName={room.roomName} handleClose={handleCloseDialog} />
                                                                        </TableCell>
                                                                </TableRow>

                                                            </TableBody>
                                                        </Table>
                                                    </TableContainer>
                                                )}
                                            </Box>
                                        </Collapse>
                                    </TableCell>
                                </TableRow>
                            </React.Fragment>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default RoomList;
