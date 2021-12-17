import { Button, CircularProgress, DialogActions, FormControlLabel, FormGroup, TextareaAutosize, TextField, Theme } from '@mui/material';
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
import DocumentInputController from '../component/documentInput/DocumentInputController';
import ExtraCostInputController from '../component/ExtraCostInput/ExtraCostInputController';
import Switch from '@mui/material/Switch';

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

const UpdateTenantPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {tenantId} = useParams();
    const {root, form, input} = useStyles();
    const [fetchingCurrencies, setFetchingCurrencies] = useState<boolean>(false);
    const [fetchingTenant, setFetchingTenant] = useState<boolean>(false);
    const [fetchingDocuments, setFetchingDocuments] = useState<boolean>(false);
    const [fetchingExtraCosts, setFetchingExtraCosts] = useState<boolean>(false);
    const [fetching, setFetching] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [errorCurrencies, setErrorCurrencies] = useState<ErrorModel>({message: ''});
    const [errorTenant, setErrorTenant] = useState<ErrorModel>({message: ''});
    const [errorDocuments, setErrorDocuments] = useState<ErrorModel>({message: ''});
    const [errorExtraCosts, setErrorExtraCosts] = useState<ErrorModel>({message: ''});
    const [documents, setDocuments] = useState<Document[]>([]);
    const [extraCosts, setExtraCosts] = useState<ExtraCost[]>([]);
    const [curriencies, setCurriencies] = useState<Currency[]>([]);
    const [currency, setCurrency] = useState<string>('')
    const [startDate, setStartDate] = useState<Date>(new Date(Date.now()));
    const [endDate, setEndDate] = useState<Date | null | undefined>(null);
    const [paidDate, setPaidDate] = useState<Date | null | undefined>(null);
    const [isActive, setIsActive] = useState<boolean | undefined>(false);
    const [description, setDescription] = useState<string>('');

    const updateTenant = async (tenantModel: TenantModel) => {
        const { backendURL, tenant } = setting.url;
        setFetching(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                  console.log(tenantModel);
                await Axios.put(backendURL + tenant + '/' + tenantId, tenantModel, config);
                setIsSuccessMessage(true);
            }
        } catch (e) {
            setError({message: 'something goes wrong, try again!'});
        } finally {
            setFetching(false);
        }
        
    }

    const getTenant = async() => {
        const { backendURL, tenant } = setting.url;
        setFetchingTenant(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + tenant + '/' + tenantId, config);
                const tenantModel: TenantModel = response.data as TenantModel;
                console.log(tenantModel);
                setValues({...tenantModel, });
                setIsActive(tenantModel.isActive);
                setPaidDate(tenantModel.paidDate ? new Date(tenantModel.paidDate) : null);
                setStartDate(tenantModel.startDate ? new Date(tenantModel.startDate) : new Date(Date.now()));
                setEndDate(tenantModel.endDate ? new Date(tenantModel.endDate) : null);
                setDescription(tenantModel.description);
                setCurrency(tenantModel.currency.id+'');
            }
        } catch (e) {
            setErrorTenant({message: 'cannot download data'});
        } finally {
            setFetchingTenant(false);
        } 
    }

    const getDocuments = async () => {
        const { backendURL, documentForTenant } = setting.url;
        setFetchingDocuments(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + documentForTenant + '/' + tenantId + '/all', config);
                setDocuments(response.data);
            }
        } catch (e) {
            setErrorDocuments({message: 'cannot download data'});
        } finally {
            setFetchingDocuments(false);
        }
    }

    const getExtraCosts = async () => {
        const { backendURL, extraCostsByTenant } = setting.url;
        setFetchingExtraCosts(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + extraCostsByTenant + '/' + tenantId + '/all', config);
                setExtraCosts(response.data);
            }
        } catch (e) {
            setErrorExtraCosts({message: 'cannot download data'});
        } finally {
            setFetchingExtraCosts(false);
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
            setErrorCurrencies({message: 'create download data'});
        } finally {
            setFetchingCurrencies(false);
        }
    }

    useEffect(() => {
        getCurrencies();
        getTenant();
        getExtraCosts();
        getDocuments();
    }, [loginModel, tenantId]);
    

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
                    .lessThan(100000000, 'it is too expensive'),
            dayToPay: Yup
                    .number()
                    .integer('is should be integer')
                    .strict(true)
                    .required('this field is required')
                    .moreThan(-1, 'it must be positive number')
                    .lessThan(31, 'it is not valid day')
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
                apartment: {
                  id: values.apartmentId!  
                },
                startDate: startDate.toISOString(),
                paidDate: paidDate?.toISOString(),
                endDate: endDate?.toISOString(),
                isActive: isActive
            }
            updateTenant(tenantToSend);  
        }    
      });


      return (
        <>
        {!!tenantId 
        ?  
        isSuccessMessage ?
            <SuccessMessage
            title='Congratulation! Update tenant!'
            path={'/tenant/' + tenantId}
            linkText='go back to tenant page'
            />
            :
            <Box
            component='div'
            className={root}>
                {fetchingCurrencies || fetchingTenant || fetchingDocuments || fetchingExtraCosts
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
                <TextField
                id='dayToPay'
                name='dayToPay'
                error={ !!errors.dayToPay }
                onChange={ handleChange }
                onBlur={ handleBlur }
                value={ values.dayToPay }
                label='dayToPay'
                color='secondary'
                className={ input }
                type='number'
                helperText={ errors.dayToPay }
                />
                <FormGroup>
                    <FormControlLabel control={<Switch
                checked={isActive}
                size='medium'
                color='secondary'
                onChange={(e) => setIsActive(e.target.checked)}
                inputProps={{ 'aria-label': 'controlled' }}
                />}
                label={isActive ? 'tenant active' : 'tenant inactive'}/>
                </FormGroup>
                <TextareaAutosize
                id='description'
                name='description'
                aria-label='description'
                minRows={3}
                value={description}
                style={{marginTop: 10, marginBottom: 10}}
                onChange={(e)=>setDescription(e.target.value)}
                placeholder="description"
                />
                <MobileDatePicker
                label="end date"
                inputFormat="MM/dd/yyyy"
                value={endDate}
                onChange={(value) => setEndDate(value)}
                renderInput={(params) => <TextField {...params} />}
                />
                <Button
                color='secondary'
                onClick={() => setEndDate(null)}
                >
                    unset end date
                </Button>
                <MobileDatePicker
                label="paid date"
                inputFormat="MM/dd/yyyy"
                value={paidDate}
                onChange={(value) => setPaidDate(value)}
                renderInput={(params) => <TextField {...params} />}
                />
                <Button
                color='secondary'
                onClick={() => setPaidDate(null)}
                >
                    unset paid date
                </Button>
                <MobileDatePicker
                label="start date"
                inputFormat="MM/dd/yyyy"
                value={startDate}
                onChange={(value) => setStartDate(value ? value : new Date(Date.now()))}
                renderInput={(params) => <TextField {...params} />}
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
                    title='update'
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
            <Snackbar
            open={ errorTenant.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorTenant.message}
            onClose={() => setErrorTenant({...errorTenant, message: ''})}
            />
            <Snackbar
            open={ errorExtraCosts.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorExtraCosts.message}
            onClose={() => setErrorExtraCosts({...errorExtraCosts, message: ''})}
            />
            <Snackbar
            open={ errorDocuments.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorDocuments.message}
            onClose={() => setErrorDocuments({...errorDocuments, message: ''})}
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


export default connector(UpdateTenantPage);
