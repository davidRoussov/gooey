import React, { useState } from 'react';
import { css } from '@emotion/css';

const defaultTab = {
  name: 'New Tab',
  active: true,
};

const tabsStyle = css`
  width: 100vw;
  height: 40px;
  display: flex;
  background: black;
  padding: 0 10px 0 10px;
`;

export const TabBar = () => {
  const [ tabs, setTabs ] = useState([ defaultTab ]);
  const [ activeTabIndex, setActiveTabIndex ] = useState(0);

  const tabStyle = (isActive) => css`
    padding: 10px 20px;
    border: ${isActive ? '1px solid white' : '1px solid black'};
    border-radius: 5px 5px 0 0;
    color: white;
    font-size: 12px;
    display: flex;
    align-items: center;
  `;

  return (
    <div className={tabsStyle}>
      {tabs.map((tab, i) => (
        <div
          key={i}
          className={tabStyle(activeTabIndex === i)}
        >
          {tab.name}
        </div>
      ))}
    </div>
  );
};

export default TabBar;
