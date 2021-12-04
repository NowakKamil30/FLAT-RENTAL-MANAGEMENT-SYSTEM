import React from 'react';
import './App.css';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';
import thunk from 'redux-thunk';
import { ThemeProvider } from '@mui/system';
import { createMuiTheme } from '@mui/material';
import { blue, teal } from '@mui/material/colors';

const theme = createMuiTheme({
  palette: {
      background : {
        paper: teal[300]
      },
      primary: {
          main: teal[500],
          contrastText: '#000000'
      },
      secondary: {
          main: blue[500],
          contrastText: '#000000'
      }
  }
});

function App() {
  const store = createStore(null, applyMiddleware(thunk));

  return (
    <Provider store={store}>
      <ThemeProvider theme={theme}>
        <div className="App">
          <header className="App-header">
          </header>
        </div>
      </ThemeProvider>
    </Provider>
  );
}

export default App;
