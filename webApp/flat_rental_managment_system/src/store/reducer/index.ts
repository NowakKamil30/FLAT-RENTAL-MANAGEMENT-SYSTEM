import { combineReducers } from 'redux';
import { AuthorizationReducer, AuthorizationReducerState } from './AuthorizationReducer';

export default combineReducers({
    authorization: AuthorizationReducer
});

export interface ReduceType {
    authorization: AuthorizationReducerState
}