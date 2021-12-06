import { Role } from './Role';

export interface LoginModel {
    id: number;
    token: string;
    role: Role;
    mail: string;
}