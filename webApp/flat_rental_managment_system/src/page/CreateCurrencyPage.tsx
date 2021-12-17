import { DialogActions, TextField, Theme } from '@mui/material';
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
import { ErrorModel } from '../type/ErrorModel';
import SuccessMessage from '../component/SuccessMessage';
import HandlerButton from '../component/HandlerButton';
import Snackbar from '../component/Snackbar';
import { Currency } from '../type/Currency';


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

const CreateCurrencyPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root, form, input} = useStyles();
    const [fetching, setFetching] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});


    const createCurrency = async (currencyObject: Currency) => {
        const { backendURL, currency } = setting.url;
        setFetching(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                await Axios.post(backendURL + currency, currencyObject, config);
                setIsSuccessMessage(true);
            }
        } catch (e) {
            setError({message: (e as Error).message});
        } finally {
            setFetching(false);
        }
    }

    const initialValues: Currency = {
        name: ''
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
      } = useFormik<Currency>({
        initialValues,
        validationSchema: Yup.object().shape({
            name: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(30, 'max lenght(30)')
                    .trim('no start or end with space')
        }),
        onSubmit: (values: Currency) => {
            createCurrency(values);   
        }    
      });


      return (
        <>
        {
        isSuccessMessage ?
            <SuccessMessage
            title='Congratulation! create new currency!'
            path={'/admin-panel'}
            linkText='go back to admin page'
            />
            :
            <Box
            component='div'
            className={root}>
                <Box
                component='h2'>
                    Create Currency
                </Box>
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
                label='currency name'
                color='secondary'
                className={ input }
                helperText={ errors.name }
                />
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
      alignItems: 'center',
      flexDirection: 'column'
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


export default connector(CreateCurrencyPage);

