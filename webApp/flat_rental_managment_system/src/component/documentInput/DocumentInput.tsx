import { Box, Button } from '@mui/material';
import React from 'react';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import { Document } from '../../type/Document';

export interface DocumentInputProps {
    onChange: (e: any) => void;
    onDeleteClick: () => void;
    document: Document;
}

const DocumentInput: React.FC<DocumentInputProps> = ({
    document,
    onChange,
    onDeleteClick
}): JSX.Element => {
    return (
        <Box
        component='div'
        style={{marginBottom: 10}}
        >
            <label htmlFor={"upload-document" + document.id}>
                <input
                style={{ display: "none" }}
                id={"upload-document" + document.id}
                name={"upload-document" + document.id}
                type="file"
                onChange={onChange}
                />
                <Button 
                color="secondary" 
                variant="contained" 
                component="span"
                style={{width: '100%'}}>
                    <AddIcon /> upload document
                </Button>
                <Box component='h3' color='secondary'>
                    {document.name}
                </Box>
            </label>
            <Button 
            color="secondary" 
            variant="contained" 
            component="span"
            onClick={onDeleteClick}
            style={{width: '100%'}}>
                <DeleteIcon /> delete
            </Button>
        </Box>
    )
}

export default DocumentInput;