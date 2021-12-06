import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';

const Footer = () => {
    const {root, text} = useStyles();

    return (
        <Box
        component='footer'
        className={root}
        >
            <Box
            component='p'
            className={text}>
                Kamil Nowak
            </Box>
        </Box>
    );
};

const useStyles = makeStyles((theme: Theme) => {
    return {
        root: {
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            paddingTop: 5,
            paddingBottom: 5,
            background: theme.palette.primary.main
        },
        text: {
            fontSize: 18,
        }
    }
})

export default Footer;