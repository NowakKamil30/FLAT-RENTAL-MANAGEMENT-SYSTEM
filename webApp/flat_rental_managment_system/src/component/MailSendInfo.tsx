import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';

const MailSendInfo = (): JSX.Element => {
    const {root, box} = useStyles();
    return(
        <Box
        component='div'
        className={root}>
            <Box 
            component='div'
            className={box}>
              <Box component='h2'>
                  Check your mail!!
              </Box>
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

export default MailSendInfo;