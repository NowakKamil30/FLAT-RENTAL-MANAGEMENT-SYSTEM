import React, { useState } from 'react';
import { Box } from '@mui/system';
import { Button } from '@mui/material';
import Snackbar from '../Snackbar';
import { ErrorModel } from '../../type/ErrorModel';
import { ExtraCost } from '../../type/ExtraCost';
import ExtraCostInput from './ExtraCostInput';

export interface ExtraCostInputControllerProps {
    extraCosts: ExtraCost[];
    setExtraCosts: (extraCosts: ExtraCost[]) => void;
}

const ExtraCostInputController: React.FC<ExtraCostInputControllerProps> = ({
    extraCosts,
    setExtraCosts
}): JSX.Element => {
    const [error, setError] = useState<ErrorModel>({message: ''});
    return (
        <>
        {
            !!extraCosts ?
            <Box
            component='div'>
                <Button 
                color='secondary'
                disabled={extraCosts[extraCosts.length - 1]?.name.length < 1 || extraCosts[extraCosts.length - 1]?.extraCost < 1}
                onClick={() => {
                    setExtraCosts([...extraCosts, {name: '', extraCost: 0, id: Math.random()}]);
                }}
                >
                    Add next extra cost
                </Button>
                {extraCosts?.map((extraCost) => <ExtraCostInput
                    key={extraCost.id}
                    extraCost={extraCost}
                    onChangeName={(e) => {
                            extraCosts[extraCosts.findIndex(extraCostItem => extraCostItem.id === extraCost.id)] = {extraCost: extraCost.extraCost, name: e.target.value, id: extraCost.id}
                            setExtraCosts([...extraCosts]);
                        }
                    }
                    onChangeExtraPrice={(e) => {
                            extraCosts[extraCosts.findIndex(extraCostItem => extraCostItem.id === extraCost.id)] = {extraCost: e.target.value, name: extraCost.name, id: extraCost.id}
                            setExtraCosts([...extraCosts]);
                        }
                    }
                    onDeleteClick={() => {
                        extraCosts.splice(extraCosts.findIndex(extraCostItem => extraCostItem.id === extraCost.id), 1)
                        setExtraCosts([...extraCosts]);
                    }}
                />)}
                <Snackbar
                open={ error.message.length > 0 }
                autoHideDuration={ 5000 }
                severity='error'
                title={error.message}
                onClose={() => setError({...error, message: ''})}
                />
            </Box>
            :
            null
        }
        </>      
    )
};

export default ExtraCostInputController;