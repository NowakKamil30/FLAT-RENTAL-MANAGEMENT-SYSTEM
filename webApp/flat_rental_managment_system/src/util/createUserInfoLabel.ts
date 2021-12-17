import { UserInfo } from "../type/UserInfo";


export const createUserInfoLabel = (userInfo: UserInfo): string => (
    `${userInfo.firstName} ${userInfo.lastName}(${userInfo.mail})`
)