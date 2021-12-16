import { Box, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React from 'react';
import ReactDOM from 'react-dom';

export interface ModalProps {
    children: JSX.Element;
}

const modals: HTMLElement = document.getElementById('modals')!;
const Modal: React.FC<ModalProps> = ({
    children
}) => {
    const {background, modalStyle} = useStyles();

    return ReactDOM.createPortal(
        <Box
        className={background}
        component='div'>
            <Box
            className={modalStyle}
            component='div'>
                {children}
            </Box>
        </Box>,
    modals)
}

const useStyles = makeStyles((theme: Theme) =>({
    background: {
        position: 'fixed',
        height: '100vh',
        width: '100vw',
        backgroundColor: 'rgba(0,0,0,.7)',
        zIndex: 90
      },
      modalStyle: {
        padding: 10,
        position: 'fixed',
        display:'flex',
        justifyContent:'center',
        alignItems: 'center',
        backgroundColor: theme.palette.primary.main,
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        zIndex: 100
      }
    })
  );

  export default Modal;