import React, { useState } from 'react';
import { FormControl, FormHelperText, IconButton, Input, InputAdornment, InputLabel } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';

interface InputPasswordProps {
    id?: string;
    name?: string;
    title: string;
    password: string | undefined;
    className: string;
    isError?: boolean;
    error?: string;
    color?: 'primary' | 'secondary' | undefined;
    onChange?: (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
    onBlur?: (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
}

const InputPassword: React.FC<InputPasswordProps> = ({
    id,
    name,
    title,
    password,
    className,
    isError,
    error,
    color,
    onChange,
    onBlur
}): JSX.Element => {
    const [isShowPassword, setIsShowPassword] = useState<boolean>(false);

    return (
        <FormControl
        className={ className }
        color={ color }
        error={ isError }>
          <InputLabel htmlFor='standard-adornment-password'>
              {title}
          </InputLabel>
          <Input
            id={ id }
            name={ name }
            type={isShowPassword? 'text' : 'password'}
            value={password}
            onChange={ onChange }
            onBlur={ onBlur }
            error={ isError }
            color={ color }
            endAdornment={
              <InputAdornment position='end'>
                <IconButton
                  aria-label='toggle password visibility'
                  onClick={ () => setIsShowPassword(!isShowPassword) }
                >
                  {isShowPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            }
          />
          <FormHelperText error={ isError }> {error} </FormHelperText>
        </FormControl>
    );
};

export default InputPassword;