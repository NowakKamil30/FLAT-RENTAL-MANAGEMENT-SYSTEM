import { Button, Card, CardActions, CardContent, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React from 'react';
import { User } from '../type/User';

interface UserCardProps {
    user: User;
    title: string;
    buttonTitle: string;
    onButtonClick: () => void;
    isAdmin: boolean;
    onClickAdminButton: () => void;
    adminButtonText: string;
}

const UserCard: React.FC<UserCardProps> = ({
    user,
    title,
    buttonTitle,
    isAdmin,
    adminButtonText,
    onClickAdminButton,
    onButtonClick
}): JSX.Element => {
  const classes = useStyles();

  return (
    <Card className={classes.root}>
      <CardContent>
        <Typography variant='h4' color="textSecondary" gutterBottom>
            {title}
        </Typography>
        <Typography variant="h5" component="h2">
        </Typography>
        <Typography variant="body2" component="p">
            <Typography className={classes.pos} color="textSecondary">
                {`FIRST NAME: ${user?.firstName}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`LAST NAME: ${user?.lastName}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`CREATE ACCOUNT: ${user?.createUserData}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`ACTIVE ACCOUNT: ${user?.activeAccountData}`}
            </Typography>
        </Typography>
        <CardActions>
          <Button 
          size="small"
          color='secondary'
          onClick={onButtonClick}
          >{buttonTitle}</Button>
          {isAdmin
          ?
          <Button 
          size="small"
          color='secondary'
          onClick={onClickAdminButton}
          >{adminButtonText}</Button>
          :
          null}
      </CardActions>
      </CardContent>
    </Card>
  );
}

export default UserCard;

const useStyles = makeStyles({
    root: {
      minWidth: 500,
    },
    bullet: {
      display: 'inline-block',
      margin: '0 2px',
      transform: 'scale(0.8)',
    },
    pos: {
      marginBottom: 12,
      fontSize: 30
    },
  });