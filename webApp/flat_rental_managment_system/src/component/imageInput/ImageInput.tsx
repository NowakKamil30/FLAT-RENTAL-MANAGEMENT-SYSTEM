import { Box, Button } from '@mui/material';
import React from 'react';
import AddIcon from '@mui/icons-material/Add';
import { ImageModel } from '../../type/ImageModel';
import DeleteIcon from '@mui/icons-material/Delete';

export interface ImageInputProps {
    onChange: (e: any) => void;
    onDeleteClick: () => void;
    imageModel: ImageModel;
}

const ImageInput: React.FC<ImageInputProps> = ({
    imageModel,
    onChange,
    onDeleteClick
}): JSX.Element => {
    return (
        <Box
        component='div'
        style={{marginBottom: 10}}
        >
            <label htmlFor={"upload-photo" + imageModel.id}>
                <input
                style={{ display: "none" }}
                id={"upload-photo" + imageModel.id}
                name={"upload-photo" + imageModel.id}
                type="file"
                onChange={onChange}
                />
                <Button 
                color="secondary" 
                variant="contained" 
                component="span"
                style={{width: '100%'}}>
                    <AddIcon /> upload photo
                </Button>
                <Box component='h3' color='secondary'>
                    {imageModel.title}
                </Box>
                <div
                style={{
                    backgroundImage: `url(${imageModel.photo})`, 
                    height: 200, 
                    width: '100%',
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: 'contain'
                    }}
                />
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

export default ImageInput;