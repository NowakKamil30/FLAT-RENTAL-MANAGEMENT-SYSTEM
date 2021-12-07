import { Theme } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Box } from '@mui/system';
import React from 'react';

const StatutePage = (): JSX.Element => {
    const {root, title} = useStyles();
    return (
        <Box 
        component='div'
        className={root}>
            <Box 
            component='h2'
            className={title}>
                statute
            </Box>
            <Box component='p'>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent sollicitudin finibus augue, ac vestibulum ipsum facilisis id. Mauris posuere tempus mauris, ac dignissim mauris fermentum sed. Aenean dignissim libero tempor quam lacinia, id consequat turpis sollicitudin. Mauris eros leo, imperdiet a vulputate et, fringilla sit amet elit. Vivamus laoreet lacinia augue, eleifend sodales nibh porttitor pretium. Praesent ornare justo ut blandit euismod. Sed eget neque tempor, consectetur justo et, luctus augue. Nullam id faucibus dui. Sed imperdiet justo in molestie ultrices. Nunc euismod vehicula dolor iaculis tincidunt. Pellentesque augue eros, hendrerit ut tempus et, sagittis at lacus. Quisque ullamcorper, tortor in blandit pharetra, eros neque posuere massa, lacinia placerat sapien orci non enim. Morbi in ultricies velit. Nunc consequat nulla ac tristique condimentum. Proin facilisis gravida viverra. Pellentesque vulputate mauris sit amet ultrices pharetra.
            Curabitur commodo odio purus, vitae commodo odio mattis in. Nunc consectetur posuere purus, et pellentesque mi ornare sed. Pellentesque tincidunt egestas velit ac ornare. Quisque semper mollis mauris, ut ultricies tellus mollis a. Proin blandit, tellus eu tristique malesuada, sem sapien sagittis tortor, sed gravida neque ex eu turpis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean in convallis nisi. Fusce non lobortis tortor, eget tempor velit. Phasellus pulvinar ligula lacus, sit amet suscipit risus viverra id.
            Nunc scelerisque venenatis ipsum. Etiam porta, libero nec consequat eleifend, lorem urna rutrum neque, sed lobortis magna urna at sem. Nunc condimentum enim ac neque pulvinar ultrices. Praesent mollis pellentesque arcu at molestie. Phasellus feugiat ut lacus non pellentesque. Cras commodo molestie velit eget auctor. Maecenas sed ligula non justo fermentum ornare eget et quam. Ut vel mattis ipsum, at pellentesque leo. Donec ut porta orci, at euismod arcu. Proin id magna ac mauris tempus faucibus malesuada non est.
            In maximus augue eu vehicula condimentum. Sed ante nunc, cursus id elementum et, pellentesque ac urna. Morbi porta massa sem, et sollicitudin tortor elementum vel. Nulla facilisi. Donec fermentum sed massa quis sagittis. Quisque euismod faucibus velit in scelerisque. Curabitur ut efficitur mi. Etiam eu malesuada dui. Suspendisse ut fringilla libero, et accumsan nisi. In id quam fermentum diam aliquet auctor. Cras eget est ac nunc malesuada aliquet. Nam scelerisque ornare dapibus. Etiam pellentesque ultricies eros, a venenatis neque venenatis auctor. Mauris auctor gravida arcu in blandit. Nulla ex lectus, condimentum at mollis quis, auctor id dui.
            </Box>
        </Box>
    )
}

const useStyles = makeStyles((theme: Theme) =>({
    root: {
      flexGrow: 1,
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center',
      paddingLeft: '10%',
      paddingRight: '10%'
    },
    title: {
        fontSize: 30
    }
  }
));

export default StatutePage;