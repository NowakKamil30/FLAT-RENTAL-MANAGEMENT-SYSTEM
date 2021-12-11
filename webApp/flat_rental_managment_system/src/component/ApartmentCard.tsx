import { Button, Card, CardActions, CardContent, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React from 'react';
import { Apartment } from '../type/Apartment';


interface ApartmentCardProps {
    apartment: Apartment
    title: string;
    buttonTitle: string;
    onButtonClick: () => void;
}

const ApartmentCard: React.FC<ApartmentCardProps> = ({
    apartment,
    title,
    buttonTitle,
    onButtonClick
}): JSX.Element => {
  const classes = useStyles();

  return (
    <Card className={classes.root}>
      <CardContent>
        <Typography variant='h4' color="textSecondary" gutterBottom>
            {`${title}(${apartment?.name})`}
        </Typography>
        <Typography variant="h3" component="h2">
        </Typography>
        <Typography variant="body2" component="p">
            <Typography className={classes.pos} color="textSecondary">
                {`COUNTRY: ${apartment.country}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`CITY: ${apartment.city}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`POSTCODE: ${apartment.postcode}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`STREET: ${apartment.street}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`HOUSE NUMBER: ${apartment.houseNumber}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`DESCRIPTION: ${apartment.description}`}
            </Typography>
        </Typography>
        <CardActions>
          <Button 
          size="small"
          color='secondary'
          onClick={onButtonClick}
          >{buttonTitle}</Button>
      </CardActions>
      </CardContent>
    </Card>
  );
}

export default ApartmentCard;

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