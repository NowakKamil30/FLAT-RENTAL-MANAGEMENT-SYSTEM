import { Button, Card, CardActions, CardContent, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React from 'react';
import { TenantModel } from '../type/TenantModel';

interface TenantCardProps {
    tenant: TenantModel;
    title: string;
    buttonTitle: string;
    onButtonClick: () => void;
    extraCostSum: number;
}

const TenantCard: React.FC<TenantCardProps> = ({
    tenant,
    title,
    buttonTitle,
    extraCostSum,
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
                {`FIRST NAME: ${tenant?.firstName}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`LAST NAME: ${tenant?.lastName}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`PHONE NUMBER: ${tenant?.phoneNumber}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`MAIL: ${tenant?.mail}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`FEE: BASIC(${tenant?.fee} ${tenant?.currency?.name}) + EXTRA COST(${extraCostSum} ${tenant?.currency?.name})`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`IS PAID: ${tenant?.isPaid ? 'YES' : 'NO'}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`IS ACTIVE: ${tenant?.isActive ? 'YES' : 'NO'}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`DESCRIPTION: ${tenant?.description}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`END DATE: ${tenant?.endDate ? tenant?.endDate : "NOT KNOW"}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`START DATE: ${tenant?.startDate}`}
            </Typography>
            <Typography className={classes.pos} color="textSecondary">
                {`PAID DATE: ${tenant?.paidDate}`}
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

export default TenantCard;

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