import { ErrorModel } from '../../type/ErrorModel';
import { LoginModel } from '../../type/LoginModel';
import { Role } from '../../type/Role';
import { AuthorizationType, CHECK_AUTH_LOCAL_STORAGE, SIGN_IN, SIGN_IN_ERROR, SIGN_IN_FETCHING } from '../type/AuthorizationType';


export interface AuthorizationReducerState {
    loginModel: LoginModel;
    error: ErrorModel;
    fetching: boolean
}

const INITIAL_STATE: AuthorizationReducerState = {
    loginModel: {role: Role.NO_AUTH, mail: '', id: -1, token: ''},
    error: {message: ''},
    fetching: false
}

export const AuthorizationReducer = (state: AuthorizationReducerState = INITIAL_STATE, action: AuthorizationType): AuthorizationReducerState => {
    console.log(action.type);
    switch(action.type) {
        case CHECK_AUTH_LOCAL_STORAGE: {
            return checkAuthLocalStorage(state);
        }

        case SIGN_IN: {
            return { ...state, loginModel: action.payload };
        }

        case SIGN_IN_FETCHING: {
            console.log("fetch");
            
            return { ...state, fetching: action.payload };
        }

        case SIGN_IN_ERROR: {
            console.log(action.payload);
            
            return { ...state, error: action.payload };
        }

        default: {
            return state;
        }
    }
}

const checkAuthLocalStorage = (state: AuthorizationReducerState) => {
    const token = localStorage.getItem('token');
    if ( token && token.length > 0) {
        const role = localStorage.getItem('role');
        if (role && (role === Role.ADMIN || role === Role.USER)) {
            const id: number = Number(localStorage.getItem('id'));
            if (id && id > 0) {
                const mail = localStorage.getItem('mail');
                if (mail && mail?.length > 0) {
                    return {...state, loginModel: {token, role, id, mail}}
                }
            }
        }
    }

    return state;
};

const removeLoginDataFromLocalStorage = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('id');
    localStorage.removeItem('mail');
};