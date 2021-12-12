import { Box } from '@mui/system';
import React from 'react';
import { ListType } from './ListType';
import CustomList from './CustomList';
import { makeStyles } from '@mui/styles';
import { Button, Pagination, Theme } from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import CustomListDownload from './CustomListDownload';


export interface CustomListDocumentComponentProps {
    listTypes: ListType[];
    title: string;
    page: number;
    maxPage: number;
    onCreactItemClick?: () => void;
    onPageChange: (event: any, value: any) => void;
}

const CustomListDocumentComponent: React.FC<CustomListDocumentComponentProps> = ({
    listTypes,
    title,
    page,
    maxPage,
    onCreactItemClick,
    onPageChange,
}): JSX.Element => {
    const styles = useStyles();

    return (
        <Box
        component='div'
        >
            <Box
            component='div'
            className={styles.header}>
                <Box
                component='h2'
                className={styles.title}> 
                    {title.toUpperCase()}
                </Box>
                {!!onCreactItemClick 
                ?
                <Button onClick={onCreactItemClick}>
                    <AddCircleOutlineIcon 
                    color='secondary'
                    fontSize='large'/>
                </Button>
                :
                null
                }
            </Box>
            <CustomListDownload
            listTypes={listTypes}
            />
            {maxPage < 2 ? 
            null 
            : 
            <Pagination 
            page={page} 
            count={maxPage} 
            variant="outlined" 
            onChange={onPageChange}/> }
        </Box>
    );
}

const useStyles = makeStyles((theme: Theme) =>({
      title: {
        fontSize: 30
      },
      header: {
          display: 'flex',
          alignContent: 'center',
          justifyContent: 'center'
      }

    })
  );



export default CustomListDocumentComponent;