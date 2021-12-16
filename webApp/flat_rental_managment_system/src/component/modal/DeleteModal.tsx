import { Box, Button, CircularProgress } from '@mui/material';
import React from 'react';
import Modal from './Modal';
import CheckIcon from '@mui/icons-material/Check';
import CancelIcon from '@mui/icons-material/Cancel';

export interface DeleteModalProps {
    onClose: () => void;
    onConfirm: () => void;
    title: string;
    confirmButtonTitle: string;
    cancelButtonTitle: string;
    fetching: boolean;
    showConfirmButton: boolean;
}

const DeleteModal: React.FC<DeleteModalProps> = ({
    onClose,
    onConfirm,
    title,
    cancelButtonTitle,
    confirmButtonTitle,
    fetching,
    showConfirmButton
}): JSX.Element => {

    return (
        <Modal
        children={!fetching
            ?
            <Box
            component='div'>
                <Box
                component='div'
                style={{
                    textAlign: 'center',
                    fontSize: 20
                }}>
                        {title}
                </Box>
                <Box
                style={{marginTop: 30}}
                component='div'>
                    {showConfirmButton 
                    ?
                    <Button
                    style={{margin: 10}}
                    color='secondary'
                    size='medium'
                    variant="contained"
                    onClick={onConfirm}>
                        <CheckIcon/> {confirmButtonTitle}
                    </Button>
                    : null}
                    <Button
                    color='secondary'
                    size='medium'
                    variant="contained"
                        onClick={onClose}>
                        <CancelIcon/> {cancelButtonTitle}
                    </Button>
                </Box>
            </Box>
            :
            <CircularProgress color='secondary' size={40} />
        }
        />
    )
}

export default DeleteModal;