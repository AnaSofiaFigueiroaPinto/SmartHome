import { Box, Container, Grid, Paper } from "@mui/material";
import './mainContent.css';

export const MainContentContainer = ({ children }) => {
    return (
        <Box
            component="main"
            className="main-box"
            sx={{
                position: 'relative',
                minHeight: '100vh',
                paddingTop: '1px', // Add small top padding
            }}
        >
            <Container maxWidth="lg" className="main-container">
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper className="main-paper">
                            {children}
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
            <Box
                sx={{
                    position: 'absolute',
                    bottom: 0,
                    width: '100%',
                    textAlign: 'center',
                    py: 5,
                }}
            >
            </Box>
        </Box>
    );
};
