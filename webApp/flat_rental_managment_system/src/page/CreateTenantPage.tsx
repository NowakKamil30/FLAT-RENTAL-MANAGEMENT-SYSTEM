import { CircularProgress, DialogActions, TextareaAutosize, TextField, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import { useFormik } from 'formik';
import React, { useEffect, useState } from 'react';
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
import { Document } from '../type/Document';
import { ExtraCost } from '../type/ExtraCost';
import { Currency } from '../type/Currency';
import { TenantModel } from '../type/TenantModel';
import { useParams } from 'react-router';
import MobileDatePicker from '@mui/lab/MobileDatePicker';
import CustomSelect from '../component/CustomSelect';
import { string } from 'yup/lib/locale';
import DocumentInputController from '../component/documentInput/DocumentInputController';
import ExtraCostInputController from '../component/ExtraCostInput/ExtraCostInputController';

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

const CreateTenantPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {apartmentId} = useParams();
    const {root, form, input} = useStyles();
    const [fetchingCurrencies, setFetchingCurrencies] = useState<boolean>(false);
    const [fetching, setFetching] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [errorCurrencies, setErrorCurrencies] = useState<ErrorModel>({message: ''});
    const [documents, setDocuments] = useState<Document[]>([]);
    const [extraCosts, setExtraCosts] = useState<ExtraCost[]>([]);
    const [curriencies, setCurriencies] = useState<Currency[]>([]);
    const [currency, setCurrency] = useState<string>('')
    const [startDate, setStartDate] = useState<Date>(new Date(Date.now()));
    const [description, setDescription] = useState<string>('');

    const createTenant = async (Tenant: TenantModel) => {
        const { backendURL, tenant } = setting.url;
        setFetching(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                await Axios.post(backendURL + tenant, Tenant, config);
                setIsSuccessMessage(true);
            }
        } catch (e) {
            setError({message: (e as Error).message});
        } finally {
            setFetching(false);
        }
        
    }

    const getCurrencies = async () => {
        const { backendURL, getAllCurrency } = setting.url;
        setFetchingCurrencies(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + getAllCurrency, config);
                setCurriencies(response.data);
            }
        } catch (e) {
            setErrorCurrencies({message: (e as Error).message});
        } finally {
            setFetchingCurrencies(false);
        }
        
    }

    useEffect(() => {
        getCurrencies();
    }, [loginModel, apartmentId]);
    

    const initialValues: TenantModel = {
        id: -1,
        firstName: '',
        lastName: '',
        phoneNumber: '',
        mail: '',
        fee: 0,
        description: '',
        startDate: '',
        currency: {
            id: -1,
            name: '',
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
      } = useFormik<TenantModel>({
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
                    .trim('no start or end with space'),
            phoneNumber: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(9, 'min lenght(9)')
                    .max(9, 'max lenght(9)')
                    .trim('no start or end with space'),
            mail: Yup
                    .string()
                    .strict(true)
                    .required('this field is required')
                    .min(2, 'min lenght(2)')
                    .max(50, 'max lenght(50)')
                    .email()
                    .trim('no start or end with space'),
            fee: Yup
                    .number()
                    .strict(true)
                    .required('this field is required')
                    .moreThan(-1, 'it must be positive number')
                    .lessThan(100000000, 'it is too expensive')
        }),
        onSubmit: (values: TenantModel) => {
            console.log(documents);
            let documentToSend: Document[] = [];
            if (documents.length > 0 && documents[0].document.length > 0) {
                documentToSend = documents.map((document: Document) => (
                  {
                      document: document.document,
                      name: document.name
                }));
                if(documentToSend[documentToSend.length - 1].document.length < 1) {
                    documentToSend.splice(documentToSend.length - 1, 1);
                }
            }

            let extraCostToSend: ExtraCost[] = [];
            if (extraCosts.length > 0 && extraCosts[0].name.length > 0 && extraCosts[0].extraCost > 0) {
                extraCostToSend = extraCosts.map((extraCost: ExtraCost) => (
                  {
                    extraCost: extraCost.extraCost,
                    name: extraCost.name
                }));
  
                if(extraCostToSend[extraCostToSend.length - 1].name.length < 1 || extraCostToSend[extraCostToSend.length - 1].extraCost < 1) {
                    extraCostToSend.splice(extraCostToSend.length - 1, 1);
                }
            }

            if (!currency || currency.length < 1) {
                setErrorCurrencies({...errorCurrencies, message: 'choose currency'});
                return;
            }
            const tenantToSend: TenantModel = {
                ...values,
                description: description,
                documents: documentToSend,
                extraCosts: extraCostToSend,
                currency: {
                    id: currency
                },
                startDate: startDate.toISOString(),
                apartment: {
                    id: apartmentId ? apartmentId : '0'
                }
            }
            createTenant(tenantToSend);
            console.log(tenantToSend);
            
        }    
      });


      return (
        <>
        {!!apartmentId 
        ?  
        isSuccessMessage ?
            <SuccessMessage
            title='Congratulation! create new tenant!'
            path={'/apartment/' + apartmentId}
            linkText='go back to apartment page'
            />
            :
            <Box
            component='div'
            className={root}>
                {fetchingCurrencies 
                ?
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
                id='phoneNumber'
                name='phoneNumber'
                error={ !!errors.phoneNumber }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.phoneNumber }
                label='phone number'
                type='tel'
                color='secondary'
                className={ input }
                helperText={ errors.phoneNumber }
                />
                <TextField
                id='fee'
                name='fee'
                error={ !!errors.fee }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.fee }
                label='fee'
                color='secondary'
                className={ input }
                type='number'
                helperText={ errors.fee }
                />
                <MobileDatePicker
                label="start date"
                inputFormat="MM/dd/yyyy"
                value={startDate}
                onChange={(value) => setStartDate(value ? value : new Date(Date.now()))}
                renderInput={(params) => <TextField {...params} />}
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
                <CustomSelect
                title='currency'
                label='currency'
                defaultValue='None'
                helperText='required'
                value={currency}
                onChange={(e) => setCurrency(e.target.value)}
                collection={curriencies.map(currency => ({key: currency.name, value: currency.id}))}
                />
                <DocumentInputController
                documents={documents}
                setDocuments={setDocuments}
                />
                <ExtraCostInputController
                extraCosts={extraCosts}
                setExtraCosts={setExtraCosts}
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
                 }
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => setError({...error, message: ''})}
            />
            <Snackbar
            open={ errorCurrencies.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorCurrencies.message}
            onClose={() => setErrorCurrencies({...errorCurrencies, message: ''})}
            />
            </Box>
        :
        <SuccessMessage
        title='apartment not found'
        path='/home'
        linkText='go to user page'
        />
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


export default connector(CreateTenantPage);

