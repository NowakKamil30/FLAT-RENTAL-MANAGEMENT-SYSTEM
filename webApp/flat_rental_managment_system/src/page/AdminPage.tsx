import { Box, Theme } from '@mui/system';
import React, { useEffect, useState } from 'react';
import Axios from 'axios';
import { setting } from '../setting/setting.json';
import { ErrorModel } from '../type/ErrorModel';
import { makeStyles } from '@mui/styles';
import { connect, ConnectedProps } from 'react-redux';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import { ThunkDispatch } from 'redux-thunk';
import Snackbar from '../component/Snackbar';
import { Button, CircularProgress } from '@mui/material';
import { useNavigate } from 'react-router';
import PersonIcon from '@mui/icons-material/Person';
import CustomListComponent from '../component/List/CustomListComponent';
import { UserInfo } from '../type/UserInfo';
import { createUserInfoLabel } from '../util/createUserInfoLabel';

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

const AdminPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {root} = useStyles();
    const navigate = useNavigate();
    const [maxPage, setMaxPage] = useState<number>(0);
    const [page, setPage] = useState<number>(0);
    const [fetchingUserInfos, setFetchingUserInfos] = useState<boolean>(false);
    const [errorUserInfos, setErrorUserInfos] = useState<ErrorModel>({message: ''});
    const [userInfos, setUserInfos] = useState<UserInfo[]>([]);

    const getUserInfos = async (): Promise<void> => {
        const { backendURL, userDataInfo } = setting.url;
        setFetchingUserInfos(true);
        try {
            if (loginModel.id !== -1) {
                let config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + userDataInfo + '?page=' + page, config);
                const userInfos = response.data.content as UserInfo[];
                setMaxPage(response.data.totalPages);
                setPage(response.data.pageable.pageNumber + 1);
                setUserInfos(userInfos);
            }
        } catch (e) {
            setErrorUserInfos({message: (e as Error).message});
        } finally {
            setFetchingUserInfos(false);
        }
    };

    useEffect(() =>{
        getUserInfos();
    }, [loginModel, page]);

    return (
        <Box
        component='div'
        className={root}>
            <Button
            variant="outlined"
            color='secondary'
            onClick={() => navigate('/create-currency')}>
                create new currency
            </Button>
            {
                fetchingUserInfos ?
                <CircularProgress color='secondary' size={80} />
                :
                <CustomListComponent
                title='Users'
                listTypes={userInfos.map(userInfo => ({
                    title: createUserInfoLabel(userInfo),
                    path: '/user-info-managment/' + userInfo.loginUserId,
                    icon: <PersonIcon color="primary" />,
                }))}
                maxPage={maxPage}
                page={page}
                onPageChange={(event, value) => setPage(value)}
                />
            }
            <Snackbar
            open={ errorUserInfos.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorUserInfos.message}
            onClose={() => {setErrorUserInfos({...errorUserInfos, message: ''})}}
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
      }
    })
  );

export default connector(AdminPage);