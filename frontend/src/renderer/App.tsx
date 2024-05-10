import { MemoryRouter as Router, Routes, Route } from 'react-router-dom';
import React, { useEffect } from 'react';
import axios from 'axios';
import config from '../config.js';
import './App.css';
import ContestantsList from '../components/ContestantsList';

export default function App() {
  // TODO: this healthcheck should be called periodically
  useEffect(() => {
    axios
      .get(`${config.backendUrl}/health`)
      .then((response) => {
        console.log('Healthcheck successful');
        return response;
      })
      .catch((error) => {
        console.error('Unable to communicate with backend:', error);
      });
  }, []);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<ContestantsList />} />
      </Routes>
    </Router>
  );
}
