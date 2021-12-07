import { Box } from '@mui/system';
import { useFormik } from 'formik';
import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router';
import { ErrorModel } from '../type/ErrorModel';
import * as Yup from 'yup';
import { ChangePassword } from '../type/ChangePassword';
import HandlerButton from '../component/HandlerButton';
import InputPassword from '../component/InputPassword';
import { makeStyles } from '@mui/styles';
import { Link, Theme } from '@mui/material';
import Snackbar from '../component/Snackbar';
import Axios from 'axios';
import { setting } from '../setting/setting.json';


const ChangePasswordPage = (): JSX.Element => {
    const { input, root, form, box, title } = useStyles();
    const location = useLocation();
    const [fetching, setFetching] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [isShowSuccessPage, setIsShowSuccessPage] = useState<boolean>(false);
    const navigate = useNavigate();

    const changePasswordSend = async (password: string, token: string): Promise<void> => {
        const { backendURL, resetPassword } = setting.url;
        const changePassword: ChangePassword = { password };
        setFetching(true);
        try {
            await Axios.post(backendURL + resetPassword + '?token=' + token, changePassword);
            setIsShowSuccessPage(true);
        } catch(e) {
            setError({message: (e as Error).message});
            resetForm();
        } finally {
            setFetching(false);
        }
    };

    const initialValues: ChangePassword  = {
        password: ''
      };
      const {
        values,
        errors,
        isValid,
        touched,
        dirty,
        handleBlur,
        handleChange,
        handleSubmit,
        resetForm,
      } = useFormik<ChangePassword>({
        initialValues,
        validationSchema: Yup.object().shape({
            password: Yup
                                .string()
                                .strict(true)
                                .required('this field is required')
                                .min(8, 'min lenght(8)')
                                .max(20, 'max lenght(20)')
                                .trim('no start or end with space')

        }),
        onSubmit: (values: ChangePassword) => {
            const queryParams = new URLSearchParams(location.search);
            const token = queryParams.get('token') || '';
            changePasswordSend(values.password, token);
        }
      });
    
    return (
        <>
        {!isShowSuccessPage ?
            <Box 
            component='div'
            className={root}>
                <Box
                component='h2'
                className={title}>
                    Set new password
                </Box>
                <form
                    noValidate
                    onSubmit={ handleSubmit }
                    className={ form }
                    >
                    <InputPassword
                    id='password'
                    name='password'
                    isError={ !!errors.password }
                    onChange={ handleChange }
                    onBlur={ handleBlur }
                    password={ values.password }
                    title='password'
                    error = { errors.password }
                    color='secondary'
                    className={ input }
                    />
                    <HandlerButton
                        type='submit'
                        disabled = { !isValid || touched === {} || !dirty }
                        color='secondary'
                        title='send'
                        isFetching={ fetching }
                    />
                    </form>
                    <Snackbar
                    open={ error.message.length > 0 }
                    autoHideDuration={ 5000 }
                    severity='error'
                    title={error.message}
                    onClose={() => setError({...error, message: ''})}
            />
            </Box>
            :
            <Box
            component='div'
            className={root}>
                <Box 
                component='div'
                className={box}>
                  <Box 
                  component='h2'
                  className={title}>
                      Password was changed!!
                  </Box>
                  <Link
                    component='button'
                    variant='subtitle2'
                    color='secondary'
                    disabled={ fetching }
                    onClick={() => navigate('/login')}
                    >
                        Login!!
                    </Link>
                </Box>
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
        alignItems: 'center',
        flexDirection: 'column'
      },
      input: {
        height: 90
      },
      form: {
        maxWidth: 800,
        display: 'flex',
        flexDirection: 'column',
        minHeight: '30vh'
      },
      title: {
        fontSize: 30
      },
      box: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column'
      }
    })
  );

  export default ChangePasswordPage;