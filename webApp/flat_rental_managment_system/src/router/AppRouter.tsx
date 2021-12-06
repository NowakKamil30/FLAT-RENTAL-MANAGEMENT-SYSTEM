import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Layout from '../layout/Layout';

const AppRouter = (): JSX.Element => (
    <BrowserRouter>
        <Layout/>
    </BrowserRouter>
)

export default AppRouter;