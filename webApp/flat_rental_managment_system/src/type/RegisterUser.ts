export interface RegisterUser {
    mail: string;
    firstName: string;
    lastName: string;
    password: string;
    isAcceptedStatute?: boolean
}