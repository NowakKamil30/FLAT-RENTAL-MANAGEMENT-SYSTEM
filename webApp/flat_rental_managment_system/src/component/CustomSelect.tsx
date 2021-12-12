import { FormControl, FormHelperText, InputLabel, MenuItem, Select } from '@mui/material';
import React from 'react';

export interface CustomSelectProps {
  title: string;
  value: string;
  label: string;
  onChange: (e: any) => void;
  defaultValue: string;
  collection: {key: string, value: any}[];
  helperText: string;
}

const CustomSelect: React.FC<CustomSelectProps> = ({
  title,
  value,
  label,
  defaultValue,
  collection,
  helperText,
  onChange
}): JSX.Element => {

    return (
        <FormControl required sx={{ m: 1, minWidth: 120 }}>
        <InputLabel id={title+label+collection.length + 'label'}>{title}</InputLabel>
        <Select
          labelId={title+label+collection.length + 'label'}
          id={title+label+collection.length}
          value={value}
          label={label}
          onChange={onChange}
        >
          <MenuItem value="">
            <em>{defaultValue}</em>
          </MenuItem>
          {collection.map((item: {key: string, value: any}) => <MenuItem 
          key={item.value}
          value={item.value}>
            {item.key}</MenuItem>)}
        </Select>
        <FormHelperText>{helperText}</FormHelperText>
      </FormControl>
    )
}

export default CustomSelect;