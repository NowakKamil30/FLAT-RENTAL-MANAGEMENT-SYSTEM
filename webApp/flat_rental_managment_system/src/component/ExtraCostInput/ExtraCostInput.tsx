import { Box, Button, TextField } from '@mui/material';
import React from 'react';
import DeleteIcon from '@mui/icons-material/Delete';
import { ExtraCost } from '../../type/ExtraCost';

export interface ExtraCostInputProps {
    onChangeName: (e: any) => void;
    onChangeExtraPrice: (e: any) => void;
    onDeleteClick: () => void;
    extraCost: ExtraCost;
}

const ExtraCostInput: React.FC<ExtraCostInputProps> = ({
    extraCost,
    onChangeName,
    onChangeExtraPrice,
    onDeleteClick
}): JSX.Element => {
    return (
        <Box
        component='div'
        style={{marginBottom: 10}}
        >
            <Box>
            <TextField
            onChange={onChangeName}
            value={ extraCost.name }
            label='name'
            color='secondary'
            style={{height: 90}}
            />
            <TextField
            onChange={onChangeExtraPrice}
            value={ extraCost.extraCost }
            label='price'
            color='secondary'
            style={{height: 90}}
            type='number'
            />
            </Box>
            <Button 
            color="secondary" 
            variant="contained" 
            component="span"
            onClick={onDeleteClick}
            style={{width: '100%'}}>
                <DeleteIcon /> delete
            </Button>
        </Box>
    )
}

export default ExtraCostInput;