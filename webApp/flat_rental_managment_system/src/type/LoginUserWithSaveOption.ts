import { LoginUser } from "./LoginUser";

export interface LoginUserWithSaveOption extends LoginUser {
    isSaved: boolean;
}