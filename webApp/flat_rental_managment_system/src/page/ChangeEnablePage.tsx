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

const ChangeEnablePage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {loginUserId} = useParams();
    const {root} = useStyles();
    const [fetching, setFetching] = useState<boolean>(false);
    const [fetchingEnable, setFetchingEnable] = useState<boolean>(false);
    const [isSuccessMessage, setIsSuccessMessage] = useState<boolean>(false);
    const [isEnable, setIsEnable] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const [errorEnable, setErrorEnable] = useState<ErrorModel>({message: ''});


    const getUserEnableInfo = async () => {
      const { backendURL, loginUser } = setting.url;
      setFetchingEnable(true);
      try {
          if (loginModel.id !== -1) {
              const config = {
                  headers: {
                      Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                  }
                }
              const response = await Axios.get(backendURL + loginUser + '/' + loginUserId, config);
              const loginUserData = response.data as LoginUserData;
              setIsEnable(loginUserData.isEnable);
          }
      } catch (e) {
        setErrorEnable({message: 'error when try download user information'});
      } finally {
        setFetchingEnable(false);
      }
    }

    const updateUserEnable = async () => {
      const { backendURL, loginUserEnable } = setting.url;
      setFetching(true);
      try {
          if (loginModel.id !== -1) {
              const config = {
                  headers: {
                      Authorization: `Bearer ${loginModel.token ? loginModel.token : localStorage.getItem('token')}`,
                  }
                }
              const response = await Axios.post(backendURL + loginUserEnable + '/' + loginUserId, {
                isEnable: isEnable
              }, config);
              setIsSuccessMessage(true);
          }
      } catch (e) {
        setError({message: 'error when try update, check your internet!'});
      } finally {
        setFetching(false);
      }
    }
   

    useEffect(() => {
      getUserEnableInfo();
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
                {fetching || fetchingEnable
                ?
                <CircularProgress color='secondary' size={80} />
                :
                <>
                  <FormGroup>
                      <FormControlLabel control={<Switch
                  checked={isEnable}
                  size='medium'
                  color='secondary'
                  onChange={(e) => setIsEnable(e.target.checked)}
                  inputProps={{ 'aria-label': 'controlled' }}
                  />}
                  label={isEnable ? 'active account' : 'inactive account'}/>
                  </FormGroup>
                  <Button
                  color='secondary'
                  onClick={updateUserEnable}>
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
            open={ errorEnable.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={errorEnable.message}
            onClose={() => setErrorEnable({...errorEnable, message: ''})}
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


export default connector(ChangeEnablePage);
