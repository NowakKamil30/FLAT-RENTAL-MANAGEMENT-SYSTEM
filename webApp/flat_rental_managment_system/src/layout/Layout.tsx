/* eslint-disable @typescript-eslint/no-unused-vars */
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';
import Footer from './Footer';
import Header from './Header';
import Main from './Main';

const Layout = (): JSX.Element => {
    const {root} = useStyles();
    return (
        <Box 
        component='div' 
        className={root}>
            <Header/>
            <Main/>
            <Footer/>
        </Box>
    )
};

const useStyles = makeStyles((theme: Theme) => {
    return {
        root: {
            display: 'flex',
            flexDirection: 'column',
            flex: 1,
            minHeight: '100vh',
            backgroundColor: theme.palette.background.paper
        },
    }
})

export default Layout;