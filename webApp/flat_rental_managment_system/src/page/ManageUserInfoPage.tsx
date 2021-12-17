import { Button, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';
import { connect, ConnectedProps } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import SuccessMessage from '../component/SuccessMessage';
import { useNavigate, useParams } from 'react-router';


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

const ManageUserInfoPage: React.FC<PropsFromRedux> = ({
    loginModel
}): JSX.Element => {
    const {loginUserId} = useParams();
    const navigate = useNavigate();
    const {root} = useStyles();

      return (
          <>
          {
              !!loginUserId
              ?
              <Box
              component='div'
              className={root}>
                <Button
                variant="outlined"
                color='secondary'
                style={{
                    width: 200,
                    margin: 10
                }}
                onClick={() => navigate('/update-role/' + loginUserId)}>
                    Change user role
                </Button>
                <Button
                variant="outlined"
                color='secondary'
                style={{
                    width: 200,
                    margin: 10
                }}
                onClick={() => navigate('/change-enable/' + loginUserId)}>
                    Ban/Unban user
                </Button>
              </Box>
              :
              <SuccessMessage
              title='user not found'
              path='/admin-panel'
              linkText='go to admin panel'
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
      alignItems: 'center',
      flexDirection: 'column'
    }
  }
));


export default connector(ManageUserInfoPage);