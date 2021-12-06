import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router';
import { ReduceType } from '../store/reducer';
import { LoginModel } from '../type/LoginModel';
import { Dispatch } from 'redux';
import { connect, ConnectedProps } from 'react-redux';

interface IMapStateToProps {
  loginModel: LoginModel;
}

const mapStateToProps = (state: ReduceType): IMapStateToProps => ({
  loginModel: state.authorization.loginModel
});

interface IMapDispatcherToProps {

}

const mapDispatcherToProps = (dispatch: Dispatch): IMapDispatcherToProps => (
  {
  }
);

const connector = connect(mapStateToProps, mapDispatcherToProps);

type PropsFromRedux = ConnectedProps<typeof connector>;

const Header: React.FC<PropsFromRedux> = ({
  loginModel
}): JSX.Element => {
  const navigate = useNavigate();

  return (
    <Box component='header'>
      <AppBar>
        <Toolbar>
          <Typography variant='h6' component='div' sx={{ flexGrow: 1 }}>
            Flat Rental Managment System
          </Typography>
          {loginModel?.token?.length < 1 ?
          <>
            <Button 
            color='inherit'
            onClick={()=> navigate('/login')}
            >Login</Button>
            <Button 
            color='inherit'
            onClick={()=> navigate('/register')}
            >Register</Button>
          </> :
          <>
            <Button 
            color='inherit'
            onClick={() => null}
            >sign out</Button>
          </>
        }

        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default connector(Header);