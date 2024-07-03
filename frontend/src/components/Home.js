import React, {useEffect, useState} from 'react';
import './Home.css';
import {getHouse} from '../services/house.service';
import {IconButton, Button} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useNavigate} from 'react-router-dom'; // Import useNavigate React Router

function Home() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate(); // Hook useNavigate

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await getHouse();
                setData(result);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error.message}</p>;
    if (!data || !data.body) return <p>Data not found.</p>;

    return (
        <div className="home-container">
            {/* IconButton arrow back */}
            <IconButton onClick={() => navigate('/')}>
                <ArrowBackIcon/>
            </IconButton>

            <div style={{textAlign: 'center'}}>
                <h1> Location </h1>
                <div className="home-details">
                    <p><strong>Street:</strong> {data.body.street}</p>
                    <p><strong>Door Number:</strong> {data.body.doorNumber}</p>
                    <p><strong>Zip Code:</strong> {data.body.zipCode}</p>
                    <p><strong>City:</strong> {data.body.city}</p>
                    <p><strong>Country:</strong> {data.body.country}</p>
                    <p><strong>Latitude:</strong> {data.body.latitude}</p>
                    <p><strong>Longitude:</strong> {data.body.longitude}</p>
                </div>
            </div>

            <div style={{display: 'flex', flexDirection: 'column'}}>
                <Button variant="contained" onClick={() => navigate('/rooms')}
                        style={{backgroundColor: '#336666', color: 'white', margin: '10px'}}>
                    View All Rooms
                </Button>

                <Button variant="contained" onClick={() => navigate('/house/House001/devices')}
                        style={{backgroundColor: '#336666', color: 'white', margin: '10px'}}>
                    View All Devices
                </Button>
            </div>
        </div>
    );
}

export default Home;
