import React from 'react';
import { FormControlLabel, Checkbox as Check } from '@mui/material';

type Color = 'primary' | 'secondary' | 'default';
interface CheckboxProps {
    color: Color;
    title: string;
    value: boolean | undefined;
    id?: string;
    name?: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Checkbox: React.FC<CheckboxProps> = ({
    color,
    title,
    value,
    id,
    name,
    onChange
}) => {

    return (
        <FormControlLabel
        control={ <Check
            id={ id }
            name={ name }
            checked={ value }
            color={ color }
            onChange={ onChange }
            /> }
        label={title}
        />
    );
};

export default Checkbox;