/*sidebar-menu.js*/
import React, { useState } from 'react';
import { Menu as MenuIcon, Dashboard, House, BedroomParent, DeviceThermostat } from "@mui/icons-material"; // Adicionando as importações dos ícones
import { Divider, IconButton, List, ListItemButton, ListItemIcon, ListItemText, Toolbar, styled } from "@mui/material";
import MuiDrawer from '@mui/material/Drawer';

export const SIDEBAR_WIDTH = 240;

// Decorate the default Material-UI Drawer Paper component with custom styles
const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme, open }) => ({
        '& .MuiDrawer-paper': {
            position: 'relative',
            whiteSpace: 'nowrap',
            width: SIDEBAR_WIDTH,
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen,
            }),
            boxSizing: 'border-box',
            backgroundColor: 'transparent', // Definindo a cor de fundo como transparente
            boxShadow: 'none', // Removendo a sombra
            border: 'none', // Removendo as bordas
            ...(!open && {
                overflowX: 'hidden',
                transition: theme.transitions.create('width', {
                    easing: theme.transitions.easing.sharp,
                    duration: theme.transitions.duration.leavingScreen,
                }),
                width: theme.spacing(7),
                [theme.breakpoints.up('sm')]: {
                    width: theme.spacing(9),
                },
            }),
        },
    }),
);

export const SidebarMenu = () => {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDrawer = () => {
        setIsOpen(!isOpen);
    };

    return (
        <Drawer variant="permanent" open={isOpen}>
            <Toolbar
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'flex-end',
                    px: [1],
                }}
            >
                <IconButton onClick={toggleDrawer} sx={{ color: '#336666' }}> {/* Definindo a cor do ícone do menu */}
                    <MenuIcon />
                </IconButton>
            </Toolbar>
            <Divider />
            <List component="nav" style={{ display: isOpen ? 'block' : 'none' }}>
                <ListItemButton component={'a'} href="/">
                    <ListItemIcon sx={{ color: '#336666' }}>
                        <Dashboard /> {/* Dashboard */}
                    </ListItemIcon>
                    <ListItemText primary="My Home" />
                </ListItemButton>
                <ListItemButton component={'a'} href="/house">
                    <ListItemIcon sx={{ color: '#336666' }}>
                        <House /> {/* House */}
                    </ListItemIcon>
                    <ListItemText primary="House" />
                </ListItemButton>
                <ListItemButton component={'a'} href="/rooms">
                    <ListItemIcon sx={{ color: '#336666' }}>
                        <BedroomParent /> {/* BedroomParent */}
                    </ListItemIcon>
                    <ListItemText primary="Rooms" />
                </ListItemButton>
                <ListItemButton component={'a'} href="/house/House001/devices">
                    <ListItemIcon sx={{ color: '#336666' }}>
                        <DeviceThermostat /> {/* DeviceThermostat */}
                    </ListItemIcon>
                    <ListItemText primary="Devices" />
                </ListItemButton>
            </List>
        </Drawer>
    );
};
