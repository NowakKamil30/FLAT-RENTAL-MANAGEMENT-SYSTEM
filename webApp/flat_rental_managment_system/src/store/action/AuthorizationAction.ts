import { ErrorModel } from '../../type/ErrorModel';
import { LoginModel } from '../../type/LoginModel';
import { AuthorizationType, CHECK_AUTH_LOCAL_STORAGE, REGISTER, REGISTER_ERROR, REGISTER_FETCHING, SIGN_IN, SIGN_IN_ERROR, SIGN_IN_FETCHING, SIGN_OUT } from '../type/AuthorizationType';

export const checkAuthLocalStorage = (): AuthorizationType => ({
    type: CHECK_AUTH_LOCAL_STORAGE,
});

export const signInToReducer = (loginModel: LoginModel): AuthorizationType => ({
    type: SIGN_IN,
    payload: loginModel,
});

export const signInFetching = (isFetching: boolean): AuthorizationType => ({
    type: SIGN_IN_FETCHING,
    payload: isFetching,
});

export const signInMessage = (info: ErrorModel): AuthorizationType => ({
    type: SIGN_IN_ERROR,
    payload: info,
});

export const signOut = (): AuthorizationType => ({
    type: SIGN_OUT,
});

export const registerToReducer = (isRegisterCorrect: boolean): AuthorizationType => ({
    type: REGISTER,
    payload: isRegisterCorrect,
});

export const registerFetching = (isFetching: boolean): AuthorizationType => ({
    type: REGISTER_FETCHING,
    payload: isFetching,
});

export const registerMessage = (info: ErrorModel): AuthorizationType => ({
    type: REGISTER_ERROR,
    payload: info,
});