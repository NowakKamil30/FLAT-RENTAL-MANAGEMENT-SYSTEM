import { Box, CircularProgress, DialogActions, Link, TextField, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useEffect, useState } from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { ReduceType } from '../store/reducer';
import { ErrorModel } from '../type/ErrorModel';
import { LoginModel } from '../type/LoginModel';
import { setting } from '../setting/setting.json';
import { useNavigate } from 'react-router';
import * as Yup from 'yup';
import { useFormik } from 'formik';
import Axios from 'axios';
import { User } from '../type/User';
import Snackbar from '../component/Snackbar';
import HandlerButton from '../component/HandlerButton';
import SuccessMessage from '../component/SuccessMessage';

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

const EditUserDataPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root, form, input} = useStyles();
    const [getFetchingUserData, setGetFetchingUserData] = useState<boolean>(false);
    const [fetchingUserData, setFetchingUserData] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const getUserData = async (): Promise<void> => {
        const { backendURL, userData } = setting.url;
        setGetFetchingUserData(true);
        try {
            if (loginModel.id !== -1) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + userData + '/' + loginModel.id, config);
                const user: User = response.data;
                setValues({firstName: user.firstName, lastName: user.lastName});
            }
        } catch (e) {
            setError({message: (e as Error).message});
        } finally {
            setGetFetchingUserData(false);
        }
    };

    const updateUserData = async (user: User): Promise<void> => {
        const { backendURL, userData } = setting.url;
        setFetchingUserData(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const userToSend: User = {firstName: user.firstName, lastName: user.lastName}
                const response = await Axios.put(backendURL + userData + '/' + loginModel.id, userToSend, config);
                const userFromServer: User = response.data;
                setIsSuccessMessage(true);
            }
        } catch (e) {
            setError({message: (e as Error).message});
        } finally {
            setFetchingUserData(false);
        }
    };

    useEffect(() => {
        getUserData();
    }, [loginModel]);

    const initialValues: User = {
        firstName: '',
        lastName: ''
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
    } = useFormik<User>({
      initialValues,
      validationSchema: Yup.object().shape({
          firstName: Yup
                  .string()
                  .strict(true)
                  .required('this field is required')
                  .min(2, 'min lenght(2)')
                  .max(30, 'max lenght(30)')
                  .trim('no start or end with space'),
          lastName: Yup
                  .string()
                  .strict(true)
                  .required('this field is required')
                  .min(2, 'min lenght(2)')
                  .max(30, 'max lenght(30)')
                  .trim('no start or end with space')
      }),
      onSubmit: (values: User) => {
        updateUserData(values);
      }    
    });

    return (
        <>
        {isSuccessMessage ?
            <SuccessMessage
            title='Congratulation! Changed your data!'
            path='/home'
            linkText='go back to account page'
            />
            :
            <Box
            component='div'
            className={root}>
                {getFetchingUserData ?
                <CircularProgress color='secondary' size={80} />
                :
                <form
                noValidate
                onSubmit={ handleSubmit }
                className={ form }
                >
                <TextField
                id='firstName'
                name='firstName'
                error={ !!errors.firstName }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.firstName }
                label='first name'
                color='secondary'
                className={ input }
                helperText={ errors.firstName }
                />
                <TextField
                id='lastName'
                name='lastName'
                error={ !!errors.lastName }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.lastName }
                label='last name'
                color='secondary'
                className={ input }
                helperText={ errors.lastName }
                />
                <DialogActions>
                <HandlerButton
                    type='submit'
                    disabled = { !isValid || touched === {} || !dirty }
                    color='secondary'
                    title='update'
                    isFetching={ fetchingUserData }
                />
                </DialogActions>
            </form>
            }
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

export default connector(EditUserDataPage);