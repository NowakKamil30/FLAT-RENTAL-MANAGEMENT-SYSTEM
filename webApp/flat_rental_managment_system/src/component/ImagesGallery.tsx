import { Box, ImageList, ImageListItem, ImageListItemBar, Pagination, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { padding } from '@mui/system';
import React from 'react';
import { ImageModel } from '../type/ImageModel';

export interface ImageGalleryProps {
    images: ImageModel[];
    title: string;
    page: number;
    maxPage: number;
    onPageChange: (event: any, value: any) => void;
}

const ImageGallery: React.FC<ImageGalleryProps> = ({
    images,
    title,
    page,
    maxPage,
    onPageChange
}): JSX.Element => {
    const styles = useStyles();
    console.log(images);
    return (
        <Box
        component='div'
        style={{marginTop: 30}}>
            <Box
            component='h2'
            className={styles.title}> 
            {title.toUpperCase()}
            </Box>
            {images.map((image) => (
                <Box
                className={styles.box}
                component='div'>
                    <Box
                    component='h4'
                    className={styles.imageTitle}>
                        {image.title}
                    </Box>
                    <div
                    className={styles.image}
                    style={{
                        backgroundImage: `url(${image.photo})`
                        }}
                    >
                    </div>
                </Box>
            ))}
            {maxPage < 2 ? 
                null 
                : 
                <Pagination 
                page={page} 
                count={maxPage} 
                variant="outlined" 
                onChange={onPageChange}/> }
        </Box>
    );
};

const useStyles = makeStyles((theme: Theme) =>({
    title: {
      fontSize: 30
    },
    imageTitle: {
        color: theme.palette.primary.main,
        fontSize: 20,
        textAlign: 'center'
    },
    image: {
        height: 200,
        width: 400,
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'contain',
        backgroundPosition: 'center',
    },
    box: {
        backgroundColor: theme.palette.secondary.main,
        padding: 10,
        marginBottom: 5
    }
  })
);

export default ImageGallery;