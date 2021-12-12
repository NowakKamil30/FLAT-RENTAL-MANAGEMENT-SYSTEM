import { List } from '@mui/material';
import React from 'react';
import CustomListDownloadItem from './CustomListDownloadItem';
import { ListType } from './ListType';

export interface CustomListDownloadProps {
    listTypes: ListType[]
}

const CustomListDownload: React.FC<CustomListDownloadProps> = ({
    listTypes
}): JSX.Element => {

    return (
        <List>
            {listTypes.map((listType) => <CustomListDownloadItem key={listType.path} listType={listType}/>)}
        </List>
    )
}

export default CustomListDownload;