import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import { Dispatch } from 'redux';
import React, { useEffect } from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { checkAuthLocalStorage } from '../store/action/AuthorizationAction';
import { AuthorizationType } from '../store/type/AuthorizationType';
import Footer from './Footer';
import Header from './Header';
import Main from './Main';

interface MapDispatcherToProps {
    checkAuthLocalStorage: () => AuthorizationType;
  }
  
  const mapDispatcherToProps = (dispatch: Dispatch): MapDispatcherToProps => ({
    checkAuthLocalStorage: () => (
        dispatch(checkAuthLocalStorage())
    )
  });
  
  const connector = connect(null, mapDispatcherToProps);
  
  type PropsFromRedux = ConnectedProps<typeof connector>;

const Layout: React.FC<PropsFromRedux> = (): JSX.Element => {
    const {root} = useStyles();
    useEffect(() => { checkAuthLocalStorage(); });
    
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

export default connector(Layout);