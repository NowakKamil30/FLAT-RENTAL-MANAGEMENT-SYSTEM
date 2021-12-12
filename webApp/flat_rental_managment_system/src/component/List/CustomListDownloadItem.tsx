import { ListItem, ListItemButton, ListItemIcon, ListItemText } from '@mui/material';
import React from 'react';
import { useNavigate } from 'react-router';
import { ListType } from './ListType';

export interface CustomListDownloadItemProps {
  listType: ListType
}

const CustomListDownloadItem: React.FC<CustomListDownloadItemProps> = ({
  listType
}): JSX.Element => {
    return (
        <ListItem disablePadding>
        <a 
        href={listType.path} 
        target='_blank'  
        download={listType.title}
        style={{textDecoration: 'none'}}>
        <ListItemButton>
          <ListItemIcon>
            {listType.icon}
          </ListItemIcon>
          <ListItemText primary={listType.title}/>
        </ListItemButton>
        </a>
      </ListItem>
    )
};

export default CustomListDownloadItem;