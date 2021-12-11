import React, { useEffect } from 'react';
import { Navigate, Routes, Route } from 'react-router';
import { ReduceType } from '../store/reducer';
import { Role } from '../type/Role';
import { Dispatch } from 'redux';
import { checkAuthLocalStorage } from '../store/action/AuthorizationAction';
import { connect, ConnectedProps } from 'react-redux';
import { AuthorizationType } from '../store/type/AuthorizationType';
import LoginPage from '../page/LoginPage';
import RegisterPage from '../page/RegisterPage';
import StatutePage from '../page/StatutePage';
import VerificationAccountPage from '../page/VerificationAccountPage';
import ResetPasswordPage from '../page/ResetPasswordPage';
import ChangePasswordPage from '../page/ChangePasswordPage';
import NotFoundRoutePage from '../page/NotFoundRoutePage';
import UserMainPage from '../page/UserMainPage';
import EditUserDataPage from '../page/EditUserDataPage';
import ApartamentPage from '../page/ApartamentPage';
import CreateApartmentPage from '../page/CreateApartmentPage';
import UpdateApartmentPage from '../page/UpdateApartmentPage';

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
            <Route path='/register' element={
                <PrivateRoute
                redirectPath='/home'
                isAuth={!role || !localStorage.getItem('role') || role === Role.NO_AUTH}
                >
                    <RegisterPage/>
                </PrivateRoute>
            }
            />
            <Route path='/verification-account' element={
                <PrivateRoute
                redirectPath='/home'
                isAuth={!role || !localStorage.getItem('role') || role === Role.NO_AUTH}
                >
                    <VerificationAccountPage/>
                </PrivateRoute>
            }
            />
            <Route path='/forgotten-password' element={
                <PrivateRoute
                redirectPath='/home'
                isAuth={!role || !localStorage.getItem('role') || role === Role.NO_AUTH}
                >
                    <ResetPasswordPage/>
                </PrivateRoute>
            }
            />
            <Route path='/change-password' element={
                <PrivateRoute
                redirectPath='/home'
                isAuth={!role || !localStorage.getItem('role') || role === Role.NO_AUTH}
                >
                    <ChangePasswordPage/>
                </PrivateRoute>
            }
            />
            <Route path='/home' element={
                <PrivateRoute
                redirectPath='/login'
                isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                >
                    <UserMainPage/>
                </PrivateRoute>
            }
            />
            <Route path='/home' element={
                <PrivateRoute
                redirectPath='/login'
                isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                >
                    <UserMainPage/>
                </PrivateRoute>
            }
            />
            <Route path='/edit-user' element={
                <PrivateRoute
                redirectPath='/login'
                isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                >
                    <EditUserDataPage/>
                </PrivateRoute>
            }
            />
            <Route path='/apartment' element={
                <PrivateRoute
                redirectPath='/login'
                isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                >
                    <ApartamentPage/>
                </PrivateRoute>
            }
            > 
                <Route path=':apartmentId' element={
                    <PrivateRoute
                    redirectPath='/login'
                    isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                    >
                        <ApartamentPage/>
                    </PrivateRoute>
                    }
                />
            </Route>
            <Route path='/create-apartment' element={
                <PrivateRoute
                redirectPath='/login'
                isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                >
                    <CreateApartmentPage/>
                </PrivateRoute>
            }
            />
            <Route path='/update-apartment' element={
                <PrivateRoute
                redirectPath='/login'
                isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                >
                    <UpdateApartmentPage/>
                </PrivateRoute>
            }
            > 
                <Route path=':apartmentId' element={
                    <PrivateRoute
                    redirectPath='/login'
                    isAuth={(!!role && (role === Role.ADMIN || role === Role.USER)) || (!!localStorage.getItem('role') && (localStorage.getItem('role') === Role.USER || localStorage.getItem('role') === Role.ADMIN))}
                    >
                        <UpdateApartmentPage/>
                    </PrivateRoute>
                    }
                />
            </Route>
            <Route path='/statute' element = {<StatutePage/>}/>
            <Route path='*' element={<NotFoundRoutePage/>}/>
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