
import { Button, CircularProgress } from '@mui/material';
import React from 'react';

interface HandlerButtonProps {
    disabled: boolean;
    isFetching: boolean;
    type: 'submit' | 'button';
    color: 'inherit' | 'primary' | 'secondary' | undefined;
    title: string;
}

const HandlerButton: React.FC<HandlerButtonProps> = ({
    disabled,
    isFetching,
    type,
    color,
    title
}): JSX.Element => {

    return (
        <>
        {!isFetching ? (
            <Button
            type={ type }
            disabled={ disabled }
            color={ color }>
            {title}
            </Button>
        ) : (
            <CircularProgress color={ color } />
        )}
        </>
    );
};

export default HandlerButton;