import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'; // Assume this is your React component

// This is a function that mounts your React app to the div with id 'react-root'.
// It needs to be exposed globally so that it can be called via executeJavaScript.
window.mountApp = () => {
  ReactDOM.render(<App />, document.getElementById('react-root'));
}
