import React from 'react';
import { MailSendModel } from '../type/MailSendModel';
import { ResetPassword } from '../type/ResetPassword';
import { setting } from '../setting/setting.json';
import Axios from 'axios';
import { Box } from '@mui/system';

const ResetPasswordPage = (): JSX.Element => {

    const resetPasswordSend = async (mail: string): Promise<MailSendModel> => {
        const { backendURL, changePassword } = setting.url;
        const resetPasswordToServer: ResetPassword = { mail };
        const response = await Axios.post(backendURL + changePassword, resetPasswordToServer);
    
        return response.data as MailSendModel;
    };

    return (
        <Box component='div'>

        </Box>
    )
} 

export default ResetPasswordPage;