import React, { useState, useEffect } from 'react';

const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString('en-EU', options);
};

const formatTime = (date) => {
    return date.toLocaleTimeString('en-EU', { hour: '2-digit', minute: '2-digit', hour12: false });
};

const DateTime = () => {
    const [currentDate, setCurrentDate] = useState(new Date());

    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentDate(new Date());
        }, 30000); // Update every 30 seconds

        return () => clearInterval(timer); // Cleanup on unmount
    }, []);

    return (
        <>
            <h3>{formatDate(currentDate)}</h3>
            <p>{formatTime(currentDate)}</p>
        </>
    );
};

export default DateTime;
