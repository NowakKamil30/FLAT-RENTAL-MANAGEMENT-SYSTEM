import { Button, CircularProgress, FormControlLabel, FormGroup, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React, { useEffect, useState } from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import Axios from 'axios';
import { setting } from '../setting/setting.json';
import { ErrorModel } from '../type/ErrorModel';
import SuccessMessage from '../component/SuccessMessage';
import Snackbar from '../component/Snackbar';
import { useParams } from 'react-router';
import Switch from '@mui/material/Switch';
import { LoginUserData } from '../type/LoginUserData';
import CustomSelect from '../component/CustomSelect';

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

const UpdateRolePage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {loginUserId} = useParams();
    const {root} = useStyles();
    const [fetching, setFetching] = useState<boolean>(false);
    const [fetchingRole, setFetchingRole] = useState<boolean>(false);
    const [fetchingRoles, setFetchingRoles] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [errorRoles, setErrorRoles] = useState<ErrorModel>({message: ''});
    const [errorRole, setErrorRole] = useState<ErrorModel>({message: ''});
    const [roles, setRoles] = useState<string[]>([]);
    const [role, setRole] = useState<string>('');


    const getRole = async () => {
      const { backendURL, loginUser } = setting.url;
      setFetchingRole(true);
      try {
          if (loginModel.id !== -1) {
              const config = {
                  headers: {
                      Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                  }
                }
              const response = await Axios.get(backendURL + loginUser + '/' + loginUserId, config);
              const loginUserData = response.data as LoginUserData;
              setRole(loginUserData.role);
          }
      } catch (e) {
        setErrorRole({message: 'error when try download user information'});
      } finally {
        setFetchingRole(false);
      }
    }

    const getRoles = async () => {
        const { backendURL, role } = setting.url;
        setFetchingRoles(true);
        try {
            if (loginModel.id !== -1) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                    }
                  }
                const response = await Axios.get(backendURL + role, config);
                setRoles(response.data);
            }
        } catch (e) {
            setErrorRoles({message: 'error when try download user information'});
        } finally {
            setFetchingRoles(false);
        }
      }

    const updateRole = async () => {
      const { backendURL, loginUserRole } = setting.url;
      setFetching(true);
      try {
          if (loginModel.id !== -1) {
              const config = {
                  headers: {
                      Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                  }
                }
                if (role.length > 0) {
                      await Axios.post(backendURL + loginUserRole + '/' + loginUserId, {
                        role: role
                      }, config);
                      setIsSuccessMessage(true);
                } else {
                    setError({message: 'please choose correct type'});
                }
          }
      } catch (e) {
        setError({message: 'error when try update, check your internet!'});
      } finally {
        setFetching(false);
      }
    }
   

    useEffect(() => {
        getRole();
        getRoles();
    }, [loginModel, loginUserId]);
    


      return (
        <>
        {!!loginUserId 
        ?  
        isSuccessMessage ?
            <SuccessMessage
            title='Congratulation! Action complited!'
            path={'/admin-panel'}
            linkText='go back to admin panel'
            />
            :
            <Box
            component='div'
            className={root}>
                {fetching || fetchingRole || fetchingRoles
                ?
                <CircularProgress color='secondary' size={80} />
                :
                <>
                    <CustomSelect
                    title='role'
                    label='role'
                    defaultValue='None'
                    helperText='required'
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    collection={roles.map(role => ({key: role, value: role}))}
                    />
                    <Button
                    color='secondary'
                    onClick={updateRole}>
                        update
                    </Button>
                </>
                 }
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => setError({...error, message: ''})}
            />
            <Snackbar
            open={ errorRole.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorRole.message}
            onClose={() => setErrorRole({...errorRole, message: ''})}
            />
            <Snackbar
            open={ errorRoles.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorRoles.message}
            onClose={() => setErrorRoles({...errorRoles, message: ''})}
            />
            </Box>
        :
        <SuccessMessage
        title='user not found'
        path='/admin-panel'
        linkText='go to admin page'
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


export default connector(UpdateRolePage);
