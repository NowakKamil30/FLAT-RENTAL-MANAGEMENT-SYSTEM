import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';

const NotFoundRoutePage = (): JSX.Element => {
    const {root, title} = useStyles();
    return (
        <Box
        component='div'
        className={root}>
            <Box
            component='h2'
            className={title}>
                Page not found
            </Box>
        </Box>
    )
}

const useStyles = makeStyles((theme: Theme) =>({
    root: {
        flexGrow: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'column'
      },
      input: {
        height: 90
      },
      title: {
        fontSize: 30
      }
    })
  );

  export default NotFoundRoutePage;