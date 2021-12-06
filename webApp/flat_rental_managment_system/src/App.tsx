import React from 'react';
import './App.css';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';
import thunk from 'redux-thunk';
import { ThemeProvider } from '@mui/system';
import { createTheme } from '@mui/material';
import { red, teal } from '@mui/material/colors';
import reducers from './store/reducer';
import AppRouter from './router/AppRouter';

const theme = createTheme({
  palette: {
      background : {
        paper: teal[300]
      },
      primary: {
          main: teal[500],
          contrastText: '#000000'
      },
      secondary: {
          main: red[500],
          contrastText: '#000000'
      }
  }
});

function App() {
  const store = createStore(reducers, applyMiddleware(thunk));

  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
          <AppRouter/>
      </ThemeProvider>
    </Provider>
  );
}

export default App;
