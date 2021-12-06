import React, { useEffect } from 'react';
import { Navigate, Routes, Route } from 'react-router';
import { ReduceType } from '../store/reducer';
import { Role } from '../type/Role';
import { Dispatch } from 'redux';
import { checkAuthLocalStorage } from '../store/action/AuthorizationAction';
import { connect, ConnectedProps } from 'react-redux';
import { AuthorizationType } from '../store/type/AuthorizationType';
import LoginPage from '../page/LoginPage';

interface IMapStateToProps {
    role: Role;
}

interface MapDispatcherToProps {
    checkAuthLocalStorage: () => AuthorizationType;
}
  

const MapStateToProps = (state: ReduceType): IMapStateToProps => ({
    role: state.authorization.loginModel.role
});

const mapDispatcherToProps = (dispatch: Dispatch): MapDispatcherToProps => ({
    checkAuthLocalStorage: () => (
        dispatch(checkAuthLocalStorage())
    )
  });


const connector = connect(MapStateToProps, mapDispatcherToProps);

type PropsFromRedux = ConnectedProps<typeof connector>;

const MainRouter: React.FC<PropsFromRedux> = ({
    role,
    checkAuthLocalStorage
}): JSX.Element => {
    useEffect(() => {
        checkAuthLocalStorage();
    });

    return (
        <Routes>
            <Route path='/' element = {<Navigate to='login'/>}/>
            <Route path='/login' element={
                <PrivateRoute
                redirectPath='/home'
                isAuth={!role || !localStorage.getItem('role') || role === Role.NO_AUTH}
                >
                    <LoginPage/>
                </PrivateRoute>
            }
            />
        </Routes>
    )
};

interface PrivateRouteProps {
    isAuth: boolean;
    redirectPath: string;
    children: JSX.Element;
}

const PrivateRoute = ({isAuth, children, redirectPath }: PrivateRouteProps): JSX.Element => {
    return isAuth ? children : <Navigate to={redirectPath}/>;
} 

export default connector(MainRouter);