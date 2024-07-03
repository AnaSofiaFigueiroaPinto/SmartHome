import React, { useState, useEffect } from 'react';
import './Temperature.css'; // Ensure the correct path to your CSS file
import { getTemperature } from '../services/house.service';
import config from '../config/config';
import temperatureImage from "../images/temperature.png"; // Import the configuration object
import temperatureDown from "../images/temperatureDown.png";

const units = { Celsius: "°C", Fahrenheit: "°F" };

const getColorForTemperature = (temperature) => {
    if (temperature <= 5) {
        return '#3dcadf'; // Light Blue
    } else if (temperature <= 15) {
        return '#00ff00'; // Green
    } else if (temperature <= 28) {
        return '#ffa500'; // Orange
    } else {
        return '#ff4500'; // Red
    }
};

const Temperature = () => {
    const [temperature, setTemperature] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        const fetchTemperature = async () => {
            const currentHour = new Date().getHours();
            try {
                const temperature = await getTemperature(currentHour); // Use the imported function
                console.log('API response:', temperature); // Log the API response for debugging
                if (typeof temperature === 'number') {
                    setTemperature(temperature);
                } else {
                    console.error('Invalid temperature data:', temperature);
                    setError(true); // Set error flag to true
                }
            } catch (error) {
                console.error('Error fetching temperature:', error);
                setError(true); // Set error flag to true
            } finally {
                setLoading(false);
            }

        };

        fetchTemperature();
        const temperatureReload = config.temperatureReload;
        const intervalId = setInterval(fetchTemperature, temperatureReload);

        return () => clearInterval(intervalId);
    }, []);

    if (loading) {
        return (
            <div>
                <img src={temperatureImage} alt="Humidity" style={{ width: '35px', height: '100px' }} />
                <div>Loading...</div>
            </div>
        );
    }

    if (error) {
        return (
            <div>
                <img src={temperatureDown} alt="Humidity" style={{width: '85px', height: '100px'}}/>
                <div>Weather Services</div>
                <div>Unavailable</div>
            </div>
        );
    }


    const temp = temperature !== null ? temperature : 0;
    const color = getColorForTemperature(temp);

    return (
        <div className="wrapper">
            <div className="thermometer" style={{ '--thermometer-color': color }}>
                <div className="graduations"></div>
                <div
                    className="temperature-bar"
                    style={{
                        background: `linear-gradient(to top, #3dcadf, ${color} 100%)`,
                        height: `${(temp / 40) * 100}%`,
                    }}
                    data-value={`${temp.toFixed(1)} ${units.Celsius}`}
                ></div>
            </div>
        </div>
    );
};


export default Temperature;
