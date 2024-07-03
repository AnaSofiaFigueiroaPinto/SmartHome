import React, { useEffect, useState } from 'react';
import {getHumidity} from "../services/house.service";

function Humidity() {
    const [humidityPercentage, setHumidityPercentage] = useState('');

    useEffect(() => {
        const fetchHumidity = async () => {
            try {
                const response = await getHumidity(); // Use the imported function
                console.log(response);
                setHumidityPercentage(response);
            } catch (error) {
                console.error('Error:', error);
            }
        };

        fetchHumidity();
    }, []);

    return (
        <div id="humidityPercentage" className="section">
            <h2>Humidity Percentage</h2>
            <p>{humidityPercentage} %</p>
        </div>
    );
}

export default Humidity;
