import { Box } from '@mui/system';
import React, { useEffect, useState } from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { useNavigate, useParams } from 'react-router';
import { ThunkDispatch } from 'redux-thunk';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import SuccessMessage from '../component/SuccessMessage';
import { makeStyles } from '@mui/styles';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import { CircularProgress, Theme } from '@mui/material';
import { setting } from '../setting/setting.json';
import Axios from 'axios';
import { ErrorModel } from '../type/ErrorModel';
import Snackbar from '../component/Snackbar';
import { TenantModel } from '../type/TenantModel';
import ArticleIcon from '@mui/icons-material/Article';
import { ListType } from '../component/List/ListType';
import CustomListComponent from '../component/List/CustomListComponent';
import { ExtraCost } from '../type/ExtraCost';
import { Document } from '../type/Document';
import TenantCard from '../component/TenantCard';
import { createExtraCostLabel } from '../util/createExtraCostLabel';
import { createDocumentLabel } from '../util/createDocumentLabel';
import { ExtraCostSum } from '../type/ExtraCostSum';
import CustomListDocumentComponent from '../component/List/CustomListDocumentComponent';

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

const TenantPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root} = useStyles();
    const navigate = useNavigate();
    const {tenantId} = useParams();
    const [maxPageDocuments, setMaxPageDocuments] = useState<number>(0);
    const [pageDocuments, setPageDocuments] = useState<number>(0);
    const [maxPageExtraCost, setMaxPageExtraCost] = useState<number>(0);
    const [pageExtraCost, setPageExtraCost] = useState<number>(0);
    const [errorDocuments, setErrorDocuments] = useState<ErrorModel>({message: ''});
    const [errorExtraCosts, setErrorExtraCosts] = useState<ErrorModel>({message: ''});
    const [errorTenant, setErrorTenant] = useState<ErrorModel>({message: ''});
    const [fetchingTenant, setFetchingTenant] = useState<boolean>(false);
    const [fetchingDocuments, setFetchingDocuments] = useState<boolean>(false);
    const [fetchingExtraCosts, setFetchingExtraCosts] = useState<boolean>(false);
    const [tenant, setTenant] = useState<TenantModel>({
        id: -1,
        firstName: '',
        lastName: '',
        phoneNumber: '',
        mail: '',
        fee: 0,
        isPaid: false,
        isActive: true,
        description: '',
        endDate: '',
        startDate: '',
        paidDate: '',
        currency: {
            id: -1,
            name: '',
        }
    });
    const [docuemnts, setDocuments] = useState<Document[]>([]);
    const [extraCosts, setExtraCosts] = useState<ExtraCost[]>([]);
    const [extraCostSum, setExtraCostSum] = useState<ExtraCostSum>({price: 0});

    const getTenant = async (): Promise<void> => {
        const { backendURL, tenant } = setting.url;
        setFetchingTenant(true);
        try {
            if (!!tenantId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + tenant + '/' + tenantId, config);
                setTenant(response.data as TenantModel);
            }
        } catch (e) {
            setErrorTenant({message: (e as Error).message});
        } finally {
            setFetchingTenant(false);
        }
    };

    const getDocuments = async (): Promise<void> => {
        const { backendURL, documentForTenant } = setting.url;
        setFetchingDocuments(true);
        try {
            if (!!tenantId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + documentForTenant + '/' + tenantId + "?page=" + pageDocuments, config);
                setDocuments(response.data.content as Document[]);
                setMaxPageDocuments(response.data.totalPages);
                setPageDocuments(response.data.pageable.pageNumber + 1);
            }
        } catch (e) {
            setErrorDocuments({message: (e as Error).message});
        } finally {
            setFetchingDocuments(false);
        }
    };

    const getExtraCosts = async (): Promise<void> => {
        const { backendURL, extraCostsByTenant } = setting.url;
        setFetchingExtraCosts(true);
        try {
            if (!!tenantId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + extraCostsByTenant + '/' + tenantId + "?page=" + pageExtraCost, config);
                setExtraCosts(response.data.content as ExtraCost[])
                setMaxPageExtraCost(response.data.totalPages);
                setPageExtraCost(response.data.pageable.pageNumber + 1);
            }
        } catch (e) {
            setErrorExtraCosts({message: (e as Error).message});
        } finally {
            setFetchingExtraCosts(false);
        }
    };

    const getExtraCostSum = async (): Promise<void> => {
        const { backendURL, extraCostsByTenant } = setting.url;
        if (!!tenantId) {
            let config = {
                headers: {
                    Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                }
            }
            const response = await Axios.get(backendURL + extraCostsByTenant + '/' + tenantId + '/cost', config);
            setExtraCostSum(response.data as ExtraCostSum)
        }
    };

    useEffect(() => {
        getTenant();
        getExtraCostSum();
    }, [loginModel, tenantId]);

    useEffect(() => {
        getDocuments();
    }, [loginModel, tenantId, pageDocuments]);

    useEffect(() => {
        getExtraCosts();
    }, [loginModel, tenantId, pageExtraCost]);

    return (
        <>
            {!!tenantId ? 
            <Box
            component='div'
            className={root}>
                {fetchingTenant ? 
                <CircularProgress color='secondary' size={80} />    
                :
                <TenantCard
                title='TENANT'
                buttonTitle='edit'
                onButtonClick={() => navigate('/update-tenant/' + tenantId)}
                tenant={tenant}
                extraCostSum={extraCostSum.price}
                />
                }
                {fetchingDocuments ? 
                <CircularProgress color='secondary' size={80} />
                :
                <CustomListDocumentComponent
                listTypes={docuemnts.map((document: Document) => ({
                    title: createDocumentLabel(document),
                    path: document.document,
                    icon: <ArticleIcon color="primary" />
                })) as ListType[]}
                title='documents'
                maxPage={maxPageDocuments}
                page={pageDocuments}
                onPageChange={(event, value) => setPageDocuments(value)}
                />
                }
                {fetchingExtraCosts ? 
                <CircularProgress color='secondary' size={80} />
                :
                <CustomListComponent
                listTypes={extraCosts.map((extraCost: ExtraCost) => ({
                    title: createExtraCostLabel(extraCost, tenant.currency?.name ? tenant.currency?.name : ''),
                    path: '/tenant/' + extraCost.id,
                    icon: <AttachMoneyIcon color="primary" />
                })) as ListType[]}
                title='Extra costs'
                maxPage={maxPageExtraCost}
                page={pageExtraCost}
                onPageChange={(event, value) => setPageExtraCost(value)}
                />
                }
            
            <Snackbar
            open={ errorTenant.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorTenant.message}
            onClose={() => {setErrorTenant({...errorTenant, message: ''})}}
            />
            <Snackbar
            open={ errorDocuments.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorDocuments.message}
            onClose={() => {setErrorDocuments({...errorDocuments, message: ''})}}
            />
            <Snackbar
            open={ errorExtraCosts.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorExtraCosts.message}
            onClose={() => {setErrorExtraCosts({...errorExtraCosts, message: ''})}}
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
    );
};

const useStyles = makeStyles((theme: Theme) =>({
    root: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column'
      }
    })
  );

export default connector(TenantPage);