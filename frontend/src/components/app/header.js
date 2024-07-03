import React from 'react';
import { Toolbar, Typography } from "@mui/material";
import { SIDEBAR_WIDTH } from "./sidebar-menu";
import MuiAppBar from '@mui/material/AppBar';
import { styled } from '@mui/system';
import { ROUTES } from '../../constants/routes';
import './pageHeader.css';
import {Route, Routes} from "react-router-dom";


const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: SIDEBAR_WIDTH,
        width: `calc(100% - ${SIDEBAR_WIDTH}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
    background: 'transparent', // Define o background como transparente
    boxShadow: 'none', // Remove a sombra
    position: 'absolute', // Posiciona o AppBar de forma absoluta
    left: 0, // Alinha o AppBar ao lado esquerdo da página
    right: 0, // Alinha o AppBar ao lado direito da página
}));

export const PageHeader = ({ isOpen, onClickButton }) => {

    return (
        <AppBar position="fixed" open={isOpen}>
            <Toolbar className="toolbar">
                <Typography
                    component="h1"
                    variant="h6"
                    color="#336666" //Alterar cor do Welcome home!
                    noWrap
                    className="title"
                    sx={{
                        fontSize: '50px', // Altera o tamanho da fonte conforme desejado
                        fontFamily: 'Calibri, serif', // Altera o tipo de fonte conforme desejado
                        fontWeight: 'bold', // Espessura (bold)
                        textShadow: '2px 2px 4px rgba(0, 0, 0, 0.5)', // Sombra do texto
                        position: 'absolute', // Posiciona o texto de forma absoluta
                        top: '30%', // Alinha o texto ao centro verticalmente
                        left: '44%', // Alinha o texto ao centro horizontalmente
                        transform: 'translateX(-50%)', // Centraliza o texto horizontalmente
                    }}
                >
                    {/* uses the react router to read the browser URL*/}
                    <Routes>
                        <Route path={ROUTES.DASHBOARD} element="Welcome Home!" />
                        <Route path={ROUTES.HOUSE} element="Welcome to Your House!" />
                        <Route path={ROUTES.ROOMS} element="Explore Your Rooms!" />
                        <Route path={ROUTES.DEVICES} element="Manage Your Devices!" />
                        <Route path={ROUTES.ROOM_DEVICES} element="Manage Your Room Devices!" />
                        <Route path={ROUTES.MEASUREMENTS} element="Measurements" />
                        <Route path={ROUTES.BLINDROLLER} element="View Your Blind Rollers!" />
                    </Routes>
                </Typography>
            </Toolbar>
        </AppBar>
    );
};
