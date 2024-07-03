import React, { useEffect, useState } from 'react';
import {getSunsetTime} from "../services/house.service";

function Sunset() {
    const [sunsetTime, setSunsetTime] = useState('');


    useEffect(() => {
        const fetchSunsetTime = async () => {
            try {
                const response = await getSunsetTime(); // Use the imported function
                const formatedTime = convertTime(response);
                console.log(formatedTime);
                setSunsetTime(formatedTime);
            } catch (error) {
                console.error('Error:', error);
            }
        };

        fetchSunsetTime();
    }, []);

    const convertTime = (time) => {
        const hours = Math.floor(time);
        const minutes = Math.round((time - hours) * 100); // Convert fractional part to minutes
        return `${hours}:${minutes.toString().padStart(2, '0')}`; // Ensure minutes are two digits
    };

    return (
        <div id="sunsetTime" className="section">
            <h2>Sunset Time</h2>
            <p>{sunsetTime}</p>
        </div>
    );
}

export default Sunset;
