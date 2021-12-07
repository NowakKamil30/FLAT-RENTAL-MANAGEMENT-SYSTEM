import Axios from 'axios';
import { AnyAction } from 'redux';
import { ThunkAction } from 'redux-thunk';
import { LoginModel } from '../../type/LoginModel';
import {
    registerFetching,
    registerMessage,
    registerToReducer,
    signInFetching,
    signInMessage,
    signInToReducer
 } from '../action/AuthorizationAction';
import { LoginUser } from '../../type/LoginUser';
import { RegisterUser } from '../../type/RegisterUser';
import { RegisterUserToSend } from '../../type/RegisterUserToSend';
import { setting } from "../../setting/setting.json";

const signInSend = async (user: LoginUser): Promise<LoginModel> => {
    const { backendURL, login } = setting.url;
    const loginUserToServer: LoginUser = { mail: user.mail, password: user.password };
    const response = await Axios.post(backendURL + login, loginUserToServer);
    return response.data as LoginModel;
};

const registerSend = async (registerUser: RegisterUser): Promise<LoginModel> => {
    const { backendURL, register } = setting.url;
    const registerUserToService: RegisterUserToSend = {
        mail: registerUser.mail,
        password: registerUser.password,
        userData: {
            firstName: registerUser.firstName,
            lastName: registerUser.lastName
        },
    };
    const response = await Axios.post(backendURL + register, registerUserToService);

    return response.data as LoginModel;
};

export const  signIn = (
    user: LoginUser,
    successAction?: () => void,
    errorAction?: () => void
    ): ThunkAction<void, {}, {}, AnyAction> => (
    async dispatch => {
        dispatch(signInFetching(true));
        try {
            const loginModel = await signInSend(user);
            localStorage.setItem('token', loginModel.token);
            localStorage.setItem('role', loginModel.role);
            localStorage.setItem('id', loginModel.id+'');
            localStorage.setItem('mail', loginModel.mail);
            dispatch(signInToReducer(loginModel));
            successAction && successAction();
        } catch (e) {
            dispatch(signInMessage({
                message: (e as Error).message
            }));
            errorAction && errorAction();
        } finally {
            dispatch(signInFetching(false));
        }
    }
);

export const register = (
    registerUser: RegisterUser,
    successAction?: () => void,
    errorAction?: () => void): ThunkAction<void, {}, {}, AnyAction> => (
        async dispatch => {
            dispatch(registerFetching(true));
            try {
                const loginModel: LoginModel = await registerSend(registerUser);
                dispatch(registerToReducer(true));
                successAction && successAction();
            } catch(e) {
                dispatch(registerMessage({
                    message: (e as Error).message
                }));
                errorAction && errorAction();
            } finally {
                dispatch(registerFetching(false));
            }
        }
    );