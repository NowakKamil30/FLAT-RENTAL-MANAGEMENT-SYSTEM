import { Backdrop, CircularProgress, Link, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';
import { ActiveModel } from '../type/ActiveModel';
import { setting } from '../setting/setting.json';
import Axios from 'axios';
import Snackbar from '../component/Snackbar';
import { ErrorModel } from '../type/ErrorModel';
import { Box } from '@mui/system';


const VerificationAccountPage= (): JSX.Element => {
    const { backdrop, root } = useStyles();
    const location = useLocation();
    const [fetching, setFetching] = useState<boolean>(false);
    const [error, setError] = useState<ErrorModel>({message: ''});
    const navigate = useNavigate();


    const activeAccountSend = async (token: string): Promise<void> => {
        const { backendURL, verifyToken } = setting.url;
        setFetching(true);
        try {
            await Axios.get(backendURL + verifyToken + '?token=' + token);
        } catch(e) {
            setError({message: (e as Error).message});
        } finally {
            setFetching(false);
        }
    };

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const token: string = queryParams.get('token') || '';
        activeAccountSend(token);
    }, []);

    return (
        <Box
        component='div'
        className={root}>
            <Box component='h1'>
               { error.message.length > 0 ? 'We cannot active your account :( try again': 'account is actived'}
            </Box>
            {error.message.length > 0 ? null :(
            <Link
            component='button'
            variant='subtitle2'
            color='secondary'
            disabled={ fetching }
            onClick={() => navigate('/login')}
                >
                    Login!!
            </Link>)}

            <Backdrop className={ backdrop } open={ fetching }>
                <CircularProgress color='secondary' />
            </Backdrop>
            <Snackbar
            open={ error.message.length > 0 }
            autoHideDuration={ 5000 }
            severity='error'
            title={error.message}
            onClose={() => {}}
            />
        </Box>
    );
};

const useStyles = makeStyles((theme: Theme) =>({
    root: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column'
    },
    backdrop: {
      zIndex: theme.zIndex.drawer + 1,
    }
  }),
);

export default VerificationAccountPage;