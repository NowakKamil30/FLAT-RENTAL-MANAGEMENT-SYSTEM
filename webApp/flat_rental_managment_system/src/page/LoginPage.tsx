import { Box } from '@mui/system';
import React from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { signInMessage } from '../store/action/AuthorizationAction';
import { signIn } from '../store/operation/AuthorizationOperation';
import { ReduceType } from '../store/reducer';
import { AuthorizationType } from '../store/type/AuthorizationType';
import { ErrorModel } from '../type/ErrorModel';
import * as Yup from 'yup';
import { useFormik } from 'formik';
import { DialogActions, Link, TextField, Theme } from '@mui/material';
import { useNavigate } from 'react-router';
import HandlerButton from '../component/HandlerButton';
import InputPassword from '../component/InputPassword';
import { makeStyles } from '@mui/styles';
import { LoginUser } from '../type/LoginUser';
import Snackbar from '../component/Snackbar';

interface MapDispatcherToProps {
    login: (
      loginUser: LoginUser,
      successAction?: () => void,
      errorAction?: () => void
      ) => void;
    changeSignInError: (error: ErrorModel) => AuthorizationType;
  }

  interface  MapStateToProps {
    fetching: boolean;
    error: ErrorModel;
  }

const mapDispatcherToProps = (dispatch: ThunkDispatch<{}, {}, any>): MapDispatcherToProps => ({
    login: async (
      loginUser: LoginUser,
      successAction?: () => void,
      errorAction?: () => void
      ) => (
      await dispatch(signIn(
        loginUser,
        successAction,
        errorAction
        ))
    ),
    changeSignInError: (error: ErrorModel) => (
      dispatch(signInMessage(error))
    )
})

const mapStateToProps = (state: ReduceType): MapStateToProps => ({
    fetching: state.authorization.fetching,
    error: state.authorization.error,
  });

const connector = connect(mapStateToProps, mapDispatcherToProps);

type PropsFromRedux = ConnectedProps<typeof connector>;


const LoginPage: React.FC<PropsFromRedux> = ({
    error,
    fetching,
    login,
    changeSignInError
}): JSX.Element => {
    const { form, input, root } = useStyles();
    const navigate = useNavigate();

    const initialValues: LoginUser = {
      mail: '',
      password: '',
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
    } = useFormik({
      initialValues,
      validationSchema: Yup.object().shape({
        mail: Yup
                  .string()
                  .strict(true)
                  .email("it is not a valid mail adress")
                  .required('this field is required')
                  .min(2, 'min lenght(2)')
                  .max(60, 'max lenght(60)')
                  .trim('no start or end with space'),
        password: Yup
                  .string()
                  .strict(true)
                  .required('this field is required')
                  .min(8, 'min lenght(8)')
                  .max(20, 'max lenght(20)')
                  .trim('no start or end with space')
      }),
      onSubmit: (values: LoginUser) => {
        console.log(values);
        login(values, () => {
          navigate('/home');
        }, () => {
          resetForm();
        });
       }
    });
    return (
        <Box 
        component='div'
        className={root}
        >
            <form
            noValidate
            onSubmit={ handleSubmit }
            className={ form }
            >
                <TextField
                id='mail'
                name='mail'
                error={ !!errors.mail }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.mail }
                label='Mail'
                color='secondary'
                className={ input }
                helperText={ errors.mail }
                />
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
                <DialogActions>
                <Link
                component='button'
                variant='subtitle2'
                color='secondary'
                disabled={ fetching }
                onClick={() => navigate('/forgottenPassword')}
                >
                    forgotten password?
                </Link>
                </DialogActions>
            <DialogActions>
            <HandlerButton
                    type='submit'
                    disabled = { !isValid || touched === {} || !dirty }
                    color='secondary'
                    title='login'
                    isFetching={ fetching }
                />
            </DialogActions>
            </form>
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => changeSignInError({...error, message: ''})}
            />
        </Box>
    );
};

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

export default connector(LoginPage);