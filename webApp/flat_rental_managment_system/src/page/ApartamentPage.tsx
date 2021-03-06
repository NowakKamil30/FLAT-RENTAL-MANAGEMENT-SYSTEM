import { Box } from '@mui/system';
import React, { useEffect, useState } from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { useNavigate, useParams } from 'react-router';
import { ThunkDispatch } from 'redux-thunk';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import SuccessMessage from '../component/SuccessMessage';
import ApartmentCard from '../component/ApartmentCard';
import { Apartment } from '../type/Apartment';
import { makeStyles } from '@mui/styles';
import { CircularProgress, Theme } from '@mui/material';
import { setting } from '../setting/setting.json';
import Axios from 'axios';
import { ErrorModel } from '../type/ErrorModel';
import Snackbar from '../component/Snackbar';
import { TenantModel } from '../type/TenantModel';
import PersonIcon from '@mui/icons-material/Person';
import { createTenantLabel } from '../util/createTenantLabel';
import { ListType } from '../component/List/ListType';
import CustomListComponent from '../component/List/CustomListComponent';
import { ImageModel } from '../type/ImageModel';
import ImageGallery from '../component/ImagesGallery';
import DeleteModal, { DeleteModalProps } from '../component/modal/DeleteModal';

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

const ApartamentPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root} = useStyles();
    const navigate = useNavigate();
    const {apartmentId} = useParams();
    const [showModal, setShowModal] = useState<boolean>(false);
    const [modal, setModal] = useState<DeleteModalProps>({
        fetching: false,
        onClose: () => setShowModal(false),
        onConfirm: () => {},
        title: '',
        confirmButtonTitle: 'delete',
        cancelButtonTitle: 'cancel',
        showConfirmButton: true, 
    });
    const [maxPage, setMaxPage] = useState<number>(0);
    const [page, setPage] = useState<number>(0);
    const [maxPageForImages, setMaxPageForImages] = useState<number>(0);
    const [pageForImages, setPageForImages] = useState<number>(0);
    const [errorApartment, setErrorApartment] = useState<ErrorModel>({message: ''});
    const [errorForImages, setErrorForImages] = useState<ErrorModel>({message: ''});
    const [fetchingApartment, setFetchingApartment] = useState<boolean>(false);
    const [fetchingForImages, setFetchingForImages] = useState<boolean>(false);
    const [sendReminderInfo, setSendReminder] = useState<ErrorModel>({message: ''});
    const [fetchingSendReminder, setFetchingSendReminder] = useState<boolean>(false);
    const [errorReminder, setErrorReminder] = useState<ErrorModel>({message: ''});
    const [apartment, setApartment] = useState<Apartment>({
        name: '',
        description: '',
        city: '',
        street: '',
        houseNumber: '',
        id: -1,
        postcode: '',
        country: ''
    });
    const [images, setImages] = useState<ImageModel[]>([]);
    const [errorTenants, setErrorTenants] = useState<ErrorModel>({message: ''});
    const [fetchingTenants, setFetchingTenants] = useState<boolean>(false);
    const [tenants, setTenants] = useState<TenantModel[]>([]);

    const getApartment = async (): Promise<void> => {
        const { backendURL, apartment } = setting.url;
        setFetchingApartment(true);
        try {
            if (!!apartmentId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + apartment + '/' + apartmentId, config);
                setApartment(response.data as Apartment);
            }
        } catch (e) {
            setErrorApartment({message: 'something goes wrong, try again!'});
        } finally {
            setFetchingApartment(false);
        }
    };

    const sendReminder = async (): Promise<void> => {
        const { backendURL, sendReminder } = setting.url;
        setFetchingSendReminder(true);
        try {
            if (!!apartmentId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                await Axios.get(backendURL + sendReminder + '/' + apartmentId, config);
                setSendReminder({message: 'send reminder!!'});
            }
        } catch (e) {
            setErrorReminder({message: 'something goes wrong, try again!'});
        } finally {
            setFetchingSendReminder(false);
        }
    };

    const getTenents = async (): Promise<void> => {
        const { backendURL, tenantByApartment } = setting.url;
        setFetchingTenants(true);
        try {
            if (!!apartmentId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + tenantByApartment + '/' + apartmentId + "?page=" + page, config);
                const tenantsFromServer = response.data.content as TenantModel[];
                setTenants(tenantsFromServer);
                setMaxPage(response.data.totalPages);
                setPage(response.data.pageable.pageNumber + 1);
            }
        } catch (e) {
            setErrorTenants({message: 'something goes wrong, try again!'});
        } finally {
            setFetchingTenants(false);
        }
    };

    const deleteTenant = async(id: number) => {
        const { backendURL, tenant } = setting.url;
        setModal({...modal, fetching: true});
        let response;
        try {
            if (loginModel.id !== -1) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                response = await Axios.delete(backendURL + tenant + '/' + id, config);
                getTenents();
            }
        } catch (e) {
            setErrorTenants({message: (e as Error).message});
        } finally {
            setModal({...modal, fetching: false, showConfirmButton: false, title: response?.status == 200 ? 'tenant was deleted' : 'error :/'});
        }
    }

    const getImages = async (): Promise<void> => {
        const { backendURL, imageByApartment } = setting.url;
        setFetchingForImages(true);
        try {
            if (!!apartmentId) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + imageByApartment + '/' + apartmentId + "?page=" + page, config);
                const images = response.data.content as ImageModel[];
                setImages(images);
                setMaxPageForImages(response.data.totalPages);
                setPageForImages(response.data.pageable.pageNumber + 1);
            }
        } catch (e) {
            setErrorForImages({message: 'something goes wrong, try again!'});
        } finally {
            setFetchingForImages(false);
        }
    };

    useEffect(() => {
        getApartment();
    }, [loginModel, apartmentId]);

    useEffect(() => {
        getTenents();
    }, [loginModel, apartmentId, page]);

    useEffect(() => {
        getImages();
    }, [loginModel, apartmentId, pageForImages]);

    return (
        <>
            {!!apartmentId ? 
            <Box
            component='div'
            className={root}>
                {fetchingApartment ? 
                <CircularProgress color='secondary' size={80} />    
                :
                <ApartmentCard
                title='APARTMENT'
                buttonTitle='edit'
                fetching={fetchingSendReminder}
                onButtonClick={() => navigate('/update-apartment/' + apartmentId)}
                apartment={apartment}
                secondButtonTitle='send reminder'
                onSecondButtonClick={() => sendReminder()}
                />
                }
                {fetchingTenants ? 
                <CircularProgress color='secondary' size={80} />
                :
                <CustomListComponent
                listTypes={tenants.map((tenant: TenantModel) => ({
                    title: createTenantLabel(tenant),
                    path: '/tenant/' + tenant.id,
                    onDeleteAction: () => {
                        setShowModal(true);
                        setModal({
                        ...modal,
                        showConfirmButton: true,
                        fetching: false,
                        title: `Do you want to delete tenant ${tenant.firstName + ' '  + tenant.lastName}?`,
                        onConfirm: () => {
                            deleteTenant(tenant.id);
                        }})},
                    icon: <PersonIcon color="primary" />
                })) as ListType[]}
                title='my tenents'
                maxPage={maxPage}
                page={page}
                onCreactItemClick={() => {navigate('/create-tenant/' + apartmentId)}}
                onPageChange={(event, value) => setPage(value)}
                />
                }
                {fetchingForImages ?
                 <CircularProgress color='secondary' size={80} />
                :
                <ImageGallery
                    onPageChange={(event, value) => setPageForImages(value)}
                    title='gallery'
                    images={images}
                    page={page}
                    maxPage={maxPageForImages}
                />
                }
            
            <Snackbar
            open={ errorApartment.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorApartment.message}
            onClose={() => {setErrorApartment({...errorApartment, message: ''})}}
            />
            <Snackbar
            open={ errorTenants.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorTenants.message}
            onClose={() => {setErrorTenants({...errorTenants, message: ''})}}
            />
            <Snackbar
            open={ errorForImages.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorForImages.message}
            onClose={() => {setErrorForImages({...errorForImages, message: ''})}}
            />
            <Snackbar
            open={ errorReminder.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorReminder.message}
            onClose={() => {setErrorReminder({...errorReminder, message: ''})}}
            />
            <Snackbar
            open={ sendReminderInfo.message.length > 0 }
            autoHideDuration={ 8000 }
            severity='success'
            title={sendReminderInfo.message}
            onClose={() => {setSendReminder({...sendReminderInfo, message: ''})}}
            />
            </Box>
            :
            <SuccessMessage
            title='apartment not found'
            path='/home'
            linkText='go to user page'
            />
            }
            {
            showModal
            ?
            <DeleteModal
            {...modal}/>
            :
            null
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

export default connector(ApartamentPage);