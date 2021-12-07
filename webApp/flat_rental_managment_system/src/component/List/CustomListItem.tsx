import { ListItem, ListItemButton, ListItemIcon, ListItemText } from '@mui/material';
import React from 'react';
import { useNavigate } from 'react-router';
import { ListType } from './ListType';

export interface CustomListItemProps {
  listType: ListType
}

const CustomListItem: React.FC<CustomListItemProps> = ({
  listType
}): JSX.Element => {
    const navigate = useNavigate();
    return (
        <ListItem disablePadding>
        <ListItemButton
        onClick={() => navigate(listType.path)}>
          <ListItemIcon>
            {listType.icon}
          </ListItemIcon>
          <ListItemText primary={listType.title}/>
        </ListItemButton>
      </ListItem>
    )
};

export default CustomListItem;