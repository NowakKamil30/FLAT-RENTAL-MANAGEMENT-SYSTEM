import { ListItem, ListItemButton, ListItemIcon, ListItemText } from '@mui/material';
import React from 'react';
import { useNavigate } from 'react-router';
import { ListType } from './ListType';
import DeleteIcon from '@mui/icons-material/Delete';

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
        {!!listType.onDeleteAction
        ?
        <ListItemButton
          onClick={listType.onDeleteAction}
        >
        <ListItemIcon>
            <DeleteIcon color='secondary'/>
        </ListItemIcon>
       </ListItemButton>
        :
        null}
      </ListItem>
    )
};

export default CustomListItem;