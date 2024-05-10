import { MemoryRouter as Router, Routes, Route } from 'react-router-dom';
import React, { useEffect } from 'react';
import axios from 'axios';
import config from '../config.js';
import './App.css';
import ContestantsList from '../components/ContestantsList';
import ContestantProfile from '../components/ContestantProfile';

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
      	{/*TODO: main page component to root '/' path */}
        <Route exact path="/contestants" element={<ContestantsList />} />
        <Route exact path="/contestants/:id" element={<ContestantProfile />} />
      </Routes>
    </Router>
  );
}
