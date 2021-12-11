import React, { useState } from 'react';
import ImageInput, { ImageInputProps } from './ImageInput';
import { ImageModel } from '../../type/ImageModel';
import { Box } from '@mui/system';
import { Button } from '@mui/material';
import Snackbar from '../Snackbar';
import { ErrorModel } from '../../type/ErrorModel';

export interface ImageInputControllerProps {
    images: ImageModel[];
    setImages: (images: ImageModel[]) => void;
}

const ImageInputController: React.FC<ImageInputControllerProps> = ({
    images,
    setImages
}): JSX.Element => {
    const [error, setError] = useState<ErrorModel>({message: ''});
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
                    if(e.target.files.length > 0) {
                        if (e.target.files[0].size > 4194304) {
                            setError({...error, message: 'image is to big'})
                            return null;
                        } else if (e.target.files[0].size <= 50) {
                            setError({...error, message: 'image is to small'})
                            return null;
                        } else if (!String(e.target.files[0].type).startsWith('image')) {
                            setError({...error, message: 'it is not image'})
                            return null;
                        }
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
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => setError({...error, message: ''})}
            />
        </Box>
    )
};

export default ImageInputController;