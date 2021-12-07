import { Link, Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';
import { useNavigate } from 'react-router';

export interface SuccessMessageProps {
    title: string;
    path: string;
    linkText: string;
}

const SuccessMessage: React.FC<SuccessMessageProps> = ({
    title,
    path,
    linkText
}): JSX.Element => {
    const {root, box} = useStyles();
    const navigate = useNavigate();
    return(
        <Box
        component='div'
        className={root}>
            <Box 
            component='div'
            className={box}>
              <Box component='h2'>
                  {title}
              </Box>
              <Link
                component='button'
                variant='subtitle2'
                color='secondary'
                onClick={() => navigate(path)}
                >
                    {linkText}
            </Link>
            </Box>
        </Box>
    )
}

const useStyles = makeStyles((theme: Theme) =>({
    root: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
      },
      box: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column'
      }
    })
  );

export default SuccessMessage;