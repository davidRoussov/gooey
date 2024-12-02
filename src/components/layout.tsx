import React from 'react';
import { css } from '@emotion/css';

import { TabBar, SearchBar } from '.';

const style = css`
  display: flex;
  flex-direction: column;
`;

export const Layout = ({ children }) => {
  return (
    <div className={style}>
      <TabBar/>
      <SearchBar/>
      {children}
    </div>
  );
};

export default Layout;
