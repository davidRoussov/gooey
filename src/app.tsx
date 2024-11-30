import { createRoot } from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { SearchPage } from './pages';

const AppRouter: React.FC = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<SearchPage/>}/>
      <Route path="*" element={<SearchPage/>}/>
    </Routes>
  </BrowserRouter>
);

const root = createRoot(document.getElementById('root'));
root.render(<AppRouter/>);
