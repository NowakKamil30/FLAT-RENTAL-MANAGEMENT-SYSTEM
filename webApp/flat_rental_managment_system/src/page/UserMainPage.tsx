import { Box, Theme } from '@mui/system';
import React, { useEffect, useState } from 'react';
import Axios from 'axios';
import { setting } from '../setting/setting.json';
import { ErrorModel } from '../type/ErrorModel';
import { User } from '../type/User';
import { makeStyles } from '@mui/styles';
import HomeIcon from '@mui/icons-material/Home';
import UserCard from '../component/UserCard';
import { connect, ConnectedProps } from 'react-redux';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import { ThunkDispatch } from 'redux-thunk';
import Snackbar from '../component/Snackbar';
import { CircularProgress } from '@mui/material';
import CustomList from '../component/List/CustomList';
import { ListType } from '../component/List/ListType';
import { Apartment } from '../type/Apartment';
import { createAdress } from '../util/createAdress';
import { useNavigate } from 'react-router';

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

const UserMainPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root, title} = useStyles();
    const navigate = useNavigate();
    const [fetchingUserData, setFetchingUserData] = useState<boolean>(false);
    const [fetchingApartments, setFetchingApartments] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [errorFromApartments, setErrorFromApartments] = useState<ErrorModel>({message: ''});
    const [listTypes, setListTypes] = useState<ListType[]>([]);
    const [user, setUser] = useState<User>({firstName: '', lastName: '', createUserData: '', activeAccountData: ''});
    const getUserData = async (): Promise<void> => {
        const { backendURL, userData } = setting.url;
        setFetchingUserData(true);
        try {
            if (loginModel.id !== -1) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + userData + '/' + loginModel.id, config);
                setUser(response.data);
            }
        } catch (e) {
            setError({message: (e as Error).message});
        } finally {
            setFetchingUserData(false);
        }
    };

    const getApartments = async (): Promise<void> => {
        const { backendURL, apartmentsByUserData } = setting.url;
        setFetchingApartments(true);
        try {
            if (loginModel.id !== -1) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + apartmentsByUserData + '/' + loginModel.id, config);
                const apartments = response.data.content as Apartment[];
                setListTypes(apartments.map((apartment: Apartment) => ({
                    title: apartment.name + ' ' + createAdress(apartment),
                    path: '/apartment/' + apartment.id,
                    icon: <HomeIcon color="primary" />
                })) as ListType[]);
            }
        } catch (e) {
            setErrorFromApartments({message: (e as Error).message});
        } finally {
            setFetchingApartments(false);
        }
    };

    useEffect(() => {
        getUserData();
        getApartments();
    }, [loginModel]);
    return (
        <Box
        component='div'
        className={root}>
            {fetchingUserData ?
            <CircularProgress color='secondary' size={80} />
            :
            <UserCard
            onButtonClick={() => navigate('/edit-user')}
            user={user}
            buttonTitle='Edit'
            title='Account'
            />}
            {
                fetchingApartments ?
                <CircularProgress color='secondary' size={80} />
                :
                <Box 
                component='div'
                >
                    <Box
                    component='h2'
                    className={title}> 
                        Your apartments
                    </Box>
                    <CustomList
                    listTypes={listTypes}
                    />
                </Box>
            }

            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => {setError({...error, message: ''})}}
            />
            <Snackbar
            open={ errorFromApartments.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorFromApartments.message}
            onClose={() => {setErrorFromApartments({...errorFromApartments, message: ''})}}
            />
        </Box>
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

export default connector(UserMainPage);