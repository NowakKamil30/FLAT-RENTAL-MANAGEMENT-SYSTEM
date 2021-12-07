import { List } from '@mui/material';
import React from 'react';
import CustomListItem from './CustomListItem';
import { ListType } from './ListType';

export interface CustomListProps {
    listTypes: ListType[]
}

const CustomList: React.FC<CustomListProps> = ({
    listTypes
}): JSX.Element => {

    return (
        <List>
            {listTypes.map((listType) => <CustomListItem key={listType.path} listType={listType}/>)}
        </List>
    )
}

export default CustomList;