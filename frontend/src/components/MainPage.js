import React from 'react';
import DateTime from "./DateTime";
import Temperature from "./Temperature";
import Humidity from "./Humidity";
import Sunrise from "./Sunrise";
import Sunset from "./Sunset";
import Orbit from "./Orbit";
import Copyright from "./app/CopyRight";

import dateImage from '../images/calendar.png';
import sunriseImage from '../images/sunrise.png';
import sunsetImage from '../images/sunset.png';

function MainPage() {
    return (
        <>
            <p className="icon"></p>
            <div className="content-container">
                <div className="date-humidity-container">
                    <div className="image-text-container">
                        <img src={dateImage} alt="Date" style={{ width: '100px', height: '100px' }} />
                        <DateTime />
                    </div>
                    <div className="image-text-container">
                        <Temperature/>
                        <Humidity />
                    </div>
                </div>
                <div className="sunrise-sunset-container">
                    <div className="image-text-container">
                        <img src={sunriseImage} alt="Sunrise" style={{width: '100px', height: '100px'}}/>
                        <Sunrise title="Sunrise"/>
                    </div>
                    <div className="image-text-container">
                        <img src={sunsetImage} alt="Sunset" style={{width: '100px', height: '100px'}}/>
                        <Sunset title="Sunset"/>
                    </div>
                </div>
            </div>
            <Orbit />
            <Copyright />
        </>
    );
}

export default MainPage;