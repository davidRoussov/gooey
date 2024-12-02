import React from 'react';
import { css } from '@emotion/css';

const style = css`
  width: 100vw;
  height: 30px;
  display: flex;
  background: black;
`;

export const SearchBar = () => {
  return (
    <div className={style}>
      search bar works
    </div>
  );
};

export default SearchBar;
