export interface RegisterUserToSend {
    mail: string;
    password: string;
    userData: {
        firstName: string;
        lastName: string;
    }
}