import { createRoot } from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { SearchPage } from './pages';
import { Layout } from './components';
import './index.css';

const AppRouter: React.FC = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<SearchPage/>}/>
      <Route path="*" element={<SearchPage/>}/>
    </Routes>
  </BrowserRouter>
);

const App: React.FC = () => (
  <Layout>
    <AppRouter/>
  </Layout>
);

const root = createRoot(document.getElementById('root'));
root.render(<App/>);
