import React, { useState } from 'react';
import { ResetPassword } from '../type/ResetPassword';
import { setting } from '../setting/setting.json';
import Axios from 'axios';
import { Box } from '@mui/system';
import * as Yup from 'yup';
import { useFormik } from 'formik';
import { ErrorModel } from '../type/ErrorModel';
import { DialogActions, TextField, Theme } from '@mui/material';
import HandlerButton from '../component/HandlerButton';
import makeStyles from '@mui/styles/makeStyles';
import Snackbar from '../component/Snackbar';
import MailSendInfo from '../component/MailSendInfo';

const ResetPasswordPage = (): JSX.Element => {
    const { form, input, root } = useStyles();
    const [fetching, setFetching] = useState<boolean>(false);
    const [isMailSend, seiIsMailSend] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});

    const resetPasswordSend = async (resetPassword: ResetPassword): Promise<void> => {
        const { backendURL, changePassword } = setting.url;
        setFetching(true);
        try {
            await Axios.post(backendURL + changePassword, resetPassword);
            seiIsMailSend(true);
        } catch (e) {
            setError({message: 'something goes wrong, try again!'});
            resetForm();
        } finally {
            setFetching(false);
        }
    };

    const initialValues: ResetPassword = {
      mail: ''
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
    } = useFormik<ResetPassword>({
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
      }),
      onSubmit: (values: ResetPassword) => {
        resetPasswordSend(values);
      }
    });

    return (
        <>
        {!isMailSend ?
            <Box 
            component='div'
            className={ root }>
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
                <DialogActions>
                <HandlerButton
                    type='submit'
                    disabled = { !isValid || touched === {} || !dirty }
                    color='secondary'
                    title='reset password'
                    isFetching={ fetching }
                />
                </DialogActions>
                </form>
                <Snackbar
                open={ error.message.length > 0 }
                autoHideDuration={ 5000 }
                severity='error'
                title={error.message}
                onClose={() => {}}
                />
            </Box>
        :
            <MailSendInfo/>
            }
        </>
    )
};

const useStyles = makeStyles((theme: Theme) =>({
    root: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
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
  }
));

export default ResetPasswordPage;