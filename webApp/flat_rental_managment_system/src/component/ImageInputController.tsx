import React, { useState } from 'react';
import ImageInput, { ImageInputProps } from './ImageInput';
import { ImageModel } from '../type/ImageModel';
import { Box } from '@mui/system';
import { Button } from '@mui/material';

export interface ImageInputControllerProps {
    images: ImageModel[];
    setImages: (images: ImageModel[]) => void;
}

const ImageInputController: React.FC<ImageInputControllerProps> = ({
    images,
    setImages
}): JSX.Element => {
    return (
        <Box
        component='div'>
            <Button 
            color='secondary'
            disabled={images[images.length - 1]?.photo.length < 1}
            onClick={() => {
                setImages([...images, {photo: '', title: '', id: Math.random()}]);
            }}
            >
                Add next image
            </Button>
            {images.map((image, i) => <ImageInput
                key={image.id}
                imageModel={image}
                onChange={(e) => {
                    const fileReader = new FileReader();
                    console.log(image);
                    if(e.target.files.length > 0) {
                        fileReader.readAsDataURL(e.target.files[0]);
                        fileReader.onload = () => {
                            images[images.findIndex(imageItem => image.id === imageItem.id)] = {photo: fileReader.result+'', title: e.target.files[0].name, id: image.id}
                            console.log(images);
                            setImages([...images]);
                        }
                    }
                }}
                onDeleteClick={() => {
                    images.splice(images.findIndex(imageItem => image.id === imageItem.id), 1)
                    setImages([...images])
                }}
            />)}
        </Box>
    )
};

export default ImageInputController;