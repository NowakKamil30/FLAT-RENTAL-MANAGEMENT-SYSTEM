export interface RegisterUserToSend {
    mail: string;
    password: string;
    user: {
        firstName: string;
        lastName: string;
    }
}