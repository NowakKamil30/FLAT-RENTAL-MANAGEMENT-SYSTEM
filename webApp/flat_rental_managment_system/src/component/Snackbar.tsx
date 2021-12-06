import React from 'react';
import { Alert, Snackbar as Snack } from '@mui/material';

interface SnackbarProps {
    severity: 'success' | 'info' | 'warning' | 'error' | undefined;
    open: boolean;
    autoHideDuration?: number;
    onClose: () => void;
    title: string;
}

const Snackbar: React.FC<SnackbarProps> = ({
severity,
open,
autoHideDuration,
onClose,
title
}): JSX.Element => {

    return (
        <Snack
        open={ open }
        autoHideDuration={ autoHideDuration || 10_000 }
        onClose={ onClose }
        >
          <Alert
          severity={ severity }
          onClose={ onClose }
          >
            {title}
          </Alert>
        </Snack>
    );
};


export default Snackbar;

