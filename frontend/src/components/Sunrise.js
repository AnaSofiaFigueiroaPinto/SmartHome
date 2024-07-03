import React, { useEffect, useState } from 'react';
import { getSunriseTime } from '../services/house.service';

function Sunrise() {
    const [sunriseTime, setSunriseTime] = useState('');

    useEffect(() => {
        const fetchSunriseTime = async () => {
            try {
                const response = await getSunriseTime(); // Use the imported function
                const formattedTime = convertTime(response);
                console.log(formattedTime);
                setSunriseTime(formattedTime);
            } catch (error) {
                console.error('Error:', error);
            }
        };

        fetchSunriseTime();
    }, []);

    const convertTime = (time) => {
        const hours = Math.floor(time);
        const minutes = Math.round((time - hours) * 100);
        return `${hours}:${minutes.toString().padStart(2, '0')}`;
    };

    return (
        <div id="sunriseTime" className="section">
            <h2>Sunrise Time</h2>
            <p>{sunriseTime}</p>
        </div>
    );
}

export default Sunrise;
