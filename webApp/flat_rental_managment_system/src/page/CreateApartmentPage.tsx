import { DialogActions, TextareaAutosize, TextField, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import { useFormik } from 'formik';
import React, { useState } from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import Axios from 'axios';
import * as Yup from 'yup';
import { setting } from '../setting/setting.json';
import { ApartmentToServer } from '../type/ApartmentToServer';
import { ErrorModel } from '../type/ErrorModel';
import SuccessMessage from '../component/SuccessMessage';
import HandlerButton from '../component/HandlerButton';
import Snackbar from '../component/Snackbar';
import ImageInputController from '../component/imageInput/ImageInputController';
import { ImageModel } from '../type/ImageModel';
import { ImageToSend } from '../type/ImageToSend';

interface IMapDispatcherToProps {}

interface IMapStateToProps {
    loginModel: LoginModel
}

const mapDispatcherToProps = (dispatch: ThunkDispatch<{}, {}, any>): IMapDispatcherToProps => ({});

const mapStateToProps = (state: ReduceType): IMapStateToProps => ({
    loginModel: state.authorization.loginModel
});

const connector = connect(mapStateToProps, mapDispatcherToProps);

type PropsFromRedux = ConnectedProps<typeof connector>;

const CreateApartmentPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root, form, input} = useStyles();
    const [fetching, setFetching] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [images, setImages] = React.useState<ImageModel[]>([]);
    const [description, setDescription] = useState<string>('');

    const createApartment = async (apartmentToSend: ApartmentToServer) => {
        const { backendURL, apartment } = setting.url;
        setFetching(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                await Axios.post(backendURL + apartment, apartmentToSend, config);
                setIsSuccessMessage(true);
            }
        } catch (e) {
            setError({message: 'something goes wrong, try again!'});
        } finally {
            setFetching(false);
        }
        
    }

    const initialValues: ApartmentToServer = {
        name: '',
        description: '',
        country: '',
        city: '',
        postcode: '',
        street: '',
        houseNumber: '',
        images: [],
        userData: {
            id: loginModel.id
        }

    }

    const {
        values,
        errors,
        isValid,
        touched,
        dirty,
        handleBlur,
        handleChange,
        handleSubmit,
        setValues,
      } = useFormik<ApartmentToServer>({
        initialValues,
        validationSchema: Yup.object().shape({
            name: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space'),
            country: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space'),
            postcode: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space'),
            city: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space'),
            street: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space'),
            houseNumber: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space')
        }),
        onSubmit: (values: ApartmentToServer) => {
          let imagesToSend: ImageToSend[] = [];
          if (images.length > 0 && images[0].photo.length > 0) {
            imagesToSend = images.map((image: ImageModel) => (
                {
                    photo: image.photo,
                    title: image.title
              }));

              if(imagesToSend[imagesToSend.length - 1].photo.length < 1) {
                imagesToSend.splice(imagesToSend.length - 1, 1);
              }
          }
          const apartment: ApartmentToServer = {
              ...values, 
              userData: {
                id: loginModel.id
              }, 
              description: description, 
              images: imagesToSend
          }
          createApartment(apartment);
        }    
      });


      return (
        <>
        {isSuccessMessage ?
            <SuccessMessage
            title='Congratulation! create new apartment!'
            path='/home'
            linkText='go back to account page'
            />
            :
            <Box
            component='div'
            className={root}>
                <form
                noValidate
                onSubmit={ handleSubmit }
                className={ form }
                >
                <TextField
                id='name'
                name='name'
                error={ !!errors.name }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.name }
                label='name'
                color='secondary'
                className={ input }
                helperText={ errors.name }
                />
                <TextField
                id='country'
                name='country'
                error={ !!errors.country }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.country }
                label='country'
                color='secondary'
                className={ input }
                helperText={ errors.country }
                />
                <TextField
                id='postcode'
                name='postcode'
                error={ !!errors.postcode }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.postcode }
                label='postcode'
                color='secondary'
                className={ input }
                helperText={ errors.postcode }
                />
                <TextField
                id='city'
                name='city'
                error={ !!errors.city }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.city }
                label='city'
                color='secondary'
                className={ input }
                helperText={ errors.city }
                />
                <TextField
                id='street'
                name='street'
                error={ !!errors.street }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.street }
                label='street'
                color='secondary'
                className={ input }
                helperText={ errors.street }
                />
                <TextField
                id='houseNumber'
                name='houseNumber'
                error={ !!errors.houseNumber }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.houseNumber }
                label='houseNumber'
                color='secondary'
                className={ input }
                helperText={ errors.houseNumber }
                />
                <TextareaAutosize
                id='description'
                name='description'
                aria-label='description'
                minRows={3}
                value={description}
                onChange={(e)=>setDescription(e.target.value)}
                placeholder="description"
                />
                <ImageInputController
                images={images}
                setImages={setImages}/>
                <DialogActions>
                <HandlerButton
                    type='submit'
                    disabled = { !isValid || touched === {} || !dirty }
                    color='secondary'
                    title='create'
                    isFetching={ fetching }
                />
                </DialogActions>
            </form>
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => setError({...error, message: ''})}
            />
            </Box>
        }
        </>
    )
}

const useStyles = makeStyles((theme: Theme) =>({
    root: {
      flexGrow: 1,
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center'
    },
    input: {
      height: 90,
    },
    form: {
      maxWidth: 800,
      display: 'flex',
      flexDirection: 'column',
      minHeight: '30vh'
    }
  }
));


export default connector(CreateApartmentPage);