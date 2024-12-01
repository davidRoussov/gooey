import React, { useState } from 'react';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

const pageStyle = css`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f5f5f5;
`;

const SearchBar = styled.input`
  display: block;
  width: 80%;
  max-width: 600px;
  padding: 1rem;
  font-size: 1.5rem;
  border: 2px solid #ccc;
  border-radius: 4px;
  outline: none;
  transition: border-color 0.2s ease;

  &:focus {
    border-color: #007bff;
  }
`;

export const SearchPage = () => {
  const [ text, setText ] = useState('https://news.ycombinator.com');

  const handleSearch = async () => {
    if (window.electronAPI) {
      const result = await window.electronAPI.getDataByDomain(text);
      console.log('result', result);
    }
  };

  return (
    <div css={pageStyle}>
      <SearchBar
        type="text"
        placeholder="Search..."
        value={text}
        onChange={e => setText(e.target.value)}
      />
      <button
        type="button"
        onClick={handleSearch}
      >
        Search
      </button>
    </div>
  );
};

export default SearchPage;
