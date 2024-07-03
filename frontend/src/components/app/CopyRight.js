/* Copyright.js */
import { Link, Typography } from "@mui/material";

export default function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="/">
                Group 5 Smart Home
            </Link>{' '}
            {new Date().getFullYear()}
        </Typography>
    );
}
