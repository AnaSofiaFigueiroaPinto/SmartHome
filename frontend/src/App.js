import React from 'react';
import './App.css';
import Rooms from './components/Rooms';
import Home from './components/Home';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import { SidebarMenu } from './components/app/sidebar-menu';
import { MainContentContainer } from './components/app/main-content-container';
import { PageHeader } from './components/app/header';
import RoomDevices from './components/RoomDevices';
import HouseDevices from "./components/HouseDevices";
import BlindRollerDevices from './components/BlindRollerDevices';
import Measurements from "./components/Measurements";
import MainPage from './components/MainPage';


function App() {
    const [open, setOpen] = React.useState(true);
    const toggleDrawer = () => setOpen(!open);

    return (
        <BrowserRouter>
        <div id="App">
            <main id="App-main">
                <PageHeader isOpen={open} onClickButton={toggleDrawer} />
                <SidebarMenu isOpen={open} onClickButton={toggleDrawer} />
                <MainContentContainer className="main-content">
                    <div className="central-box">
                        <Routes>
                            <Route path="/" element={<MainPage/>}/>
                            <Route path="/house" element={<Home/>}/>
                            <Route path="/rooms" element={<Rooms/>}/>
                            <Route path="/house/:houseID/devices" element={<HouseDevices/>}/>
                            <Route path="/rooms/:roomID/devices" element={<RoomDevices/>}/>
                            <Route path="/devices/:deviceId" element={<Measurements/>}/>
                            <Route path="/actuatorfunctionality/BlindSetter" element={<BlindRollerDevices/>}/>
                        </Routes>
                    </div>
                </MainContentContainer>
            </main>
        </div>
        </BrowserRouter>
    );
}

export default App;