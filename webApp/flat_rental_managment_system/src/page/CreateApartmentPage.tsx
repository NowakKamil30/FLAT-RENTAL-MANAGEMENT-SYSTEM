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
import { ApartmentToServer } from '../type/ApartmentToServer';
import { ErrorModel } from '../type/ErrorModel';
import SuccessMessage from '../component/SuccessMessage';
import HandlerButton from '../component/HandlerButton';
import Snackbar from '../component/Snackbar';
import ImageInput from '../component/ImageInput';
import ImageInputController from '../component/ImageInputController';
import { ImageModel } from '../type/ImageModel';

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
                    .trim('no start or end with space'),
            description: Yup
                    .string()
                    .strict(true)
                    .max(300, 'max lenght(300)')
                    .trim('no start or end with space')
        }),
        onSubmit: (values: ApartmentToServer) => {
          
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