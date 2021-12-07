import { useFormik } from "formik";
import { connect, ConnectedProps } from "react-redux";
import { ThunkDispatch } from "redux-thunk";
import { registerMessage } from "../store/action/AuthorizationAction";
import { register } from "../store/operation/AuthorizationOperation";
import { ReduceType } from "../store/reducer";
import { AuthorizationType } from "../store/type/AuthorizationType";
import { ErrorModel } from "../type/ErrorModel";
import { RegisterUser } from "../type/RegisterUser";
import * as Yup from 'yup';
import { useNavigate } from "react-router";
import Snackbar from "../component/Snackbar";
import { Box } from "@mui/system";
import { DialogActions, Link, TextField, Theme } from "@mui/material";
import InputPassword from "../component/InputPassword";
import { makeStyles } from "@mui/styles";
import Checkbox from "../component/Checkbox";
import HandlerButton from "../component/HandlerButton";
import { useState } from "react";

interface IMapDispatcherToProps {
    register: (
      registerUser: RegisterUser,
      successAction?: () => void,
      errorAction?: () => void
      ) => void;
      changeRegisterMessage: (message: ErrorModel) => AuthorizationType;
}

interface IMapStateToProps {
    isRegisterFetching: boolean;
    error: ErrorModel;
}

const mapDispatcherToProps = (dispatch: ThunkDispatch<{}, {}, any>): IMapDispatcherToProps => ({
    register: async (
      registerUser: RegisterUser,
      successAction?: () => void,
      errorAction?: () => void
      ) => (
        await dispatch(register(
          registerUser,
          successAction,
          errorAction
        ))
      ),
      changeRegisterMessage: (error: ErrorModel) => (
        dispatch(registerMessage(error))
      )
});

const mapStateToProps = (state: ReduceType): IMapStateToProps => ({
    isRegisterFetching: state.authorization.fetching,
    error: state.authorization.error
});

const connector = connect(mapStateToProps, mapDispatcherToProps);

type PropsFromRedux = ConnectedProps<typeof connector>;

const RegisterPage: React.FC<PropsFromRedux> = ({
    isRegisterFetching,
    error,
    register,
    changeRegisterMessage
}): JSX.Element => {
  const { form, input, root, box } = useStyles();
  const navigate = useNavigate();
  const [isShowSuccessPage, setIsShowSuccessPage] = useState<boolean>(false);

  const initialValues: RegisterUser = {
    mail: '',
    password: '',
    firstName: '',
    lastName: '',
    isAcceptedStatute: false,
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
    setValues,
  } = useFormik({
    initialValues,
    validationSchema: Yup.object().shape({
        mail: Yup
                .string()
                .strict(true)
                .required('this field is required')
                .min(2, 'min lenght(2)')
                .max(60, 'max lenght(60)')
                .email("it is not a valid mail adress")
                .trim('no start or end with space'),
        password: Yup
                .string()
                .strict(true)
                .required('this field is required')
                .min(8, 'min lenght(8)')
                .max(20, 'max lenght(20)')
                .trim('no start or end with space'),
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
                .trim('no start or end with space'),
        isAcceptedStatute: Yup
                          .bool()
                          .default(false)
    }),
    onSubmit: (values: RegisterUser) => {
      register(values, () => {
        resetForm();
        setIsShowSuccessPage(true);
      });
    }
  });

      return (
          <>
          {!isShowSuccessPage ?
          <Box 
          component='div'
          className={root}>
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
                label='mail'
                color='secondary'
                className={ input }
                helperText={ errors.mail }
                />
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
                <Checkbox
                id='isAcceptedStatute'
                name='isAcceptedStatute'
                value={ values.isAcceptedStatute }
                onChange={ (e) => setValues({ ...values, isAcceptedStatute: !values.isAcceptedStatute})}
                title='accept statute'
                color='secondary'
                />
                <DialogActions>
                <Link
                component='button'
                variant='subtitle2'
                color='secondary'
                disabled={ isRegisterFetching }
                onClick={() => navigate('/statute')}
                >
                    read statute
                </Link>
                </DialogActions>
                <DialogActions>
                <HandlerButton
                    type='submit'
                    disabled = { !isValid || touched === {} || !dirty || !values.isAcceptedStatute }
                    color='secondary'
                    title='register'
                    isFetching={ isRegisterFetching }
                />
                </DialogActions>
            </form>
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => changeRegisterMessage({...error, message: ''})}
            />
          </Box>
          : 
          <Box 
          component='div'
          className={root}>
              <Box 
              component='div'
              className={box}>
                <Box component='h2'>
                    Check your mail!!
                </Box>
              </Box>
          </Box>}
          </>
      );
  };

  const useStyles = makeStyles((theme: Theme) =>({
    root: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
      },
      box: {
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
      }
    })
  );

export default connector(RegisterPage);