import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';
import MainRouter from '../router/MainRouter';

const Main = (): JSX.Element => {
    const {root} = useStyles();

    return (
        <Box 
        component='main'
        className={root}>
            <MainRouter/>
        </Box>
    )
}

const useStyles = makeStyles((theme: Theme) =>{
    return {
        root: {
            marginTop: '20vh',
            flexGrow: 1
        },
    }
});

export default Main;