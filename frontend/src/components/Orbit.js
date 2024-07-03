import React, { useEffect } from 'react';
import './Orbit.css';

function Orbit() {
    useEffect(() => {
        const stars = 40;
        const skyStars = document.getElementById("sky__stars");

        function createStars() {
            for (let i = 0; i < stars; i++) {
                let x = Math.floor(Math.random() * 100 + 1);
                let y = Math.floor(Math.random() * 100 + 1);
                const starPoint = document.createElement("div");
                starPoint.style.left = `${x}%`;
                starPoint.style.top = `${y}%`;
                skyStars.appendChild(starPoint);
            }
        }
        createStars();
    }, []);

    return (
        <div id="background">
            <div className="sky">
                <div className="sky__phase sky__dawn"></div>
                <div className="sky__phase sky__noon"></div>
                <div className="sky__phase sky__dusk"></div>
                <div className="sky__phase sky__midnight">
                    <div id="sky__stars"></div>
                </div>
                <div className="orbit">
                    <div className="sun"></div>
                    <div className="moon"></div>
                </div>
            </div>
        </div>
    );
}

export default Orbit;
