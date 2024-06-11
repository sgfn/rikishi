import { MemoryRouter as Router, Routes, Route } from 'react-router-dom';
import React, { useEffect } from 'react';
import axios from 'axios';
import config from '../config.js';
import './App.css';
import ContestantsList from '../components/ContestantsList';
import ContestantProfile from '../components/ContestantProfile';
import MainView from '../components/MainView.js';
import DuelList from '../components/DuelList.js';
import CategoryList from '../components/CategoryList.js';
import Duel from '../components/Duel';
import WeightInForm from '../components/WeightInForm';
import CategoryToLadder from '../components/CategoryToLadder.js';
import ContestantsCategoryList from '../components/ContestantsCategoryList.js';
import ChangeCategoryForm from '../components/ChangeCategoryForm.js';
import TournamentBracket from '../components/TournamentBracket';
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
    <div>
      <Router>
        <Routes>
          <Route exact path="/" element={<MainView />} />
          <Route exact path="/contestants" element={<ContestantsList />} />
          <Route exact path="/categories/:category" element={<DuelList />} />
          <Route exact path="/categories" element={<CategoryList />} />
          <Route
            exact
            path="/contestants/:id"
            element={<ContestantProfile />}
          />
          <Route exact path="/weight-in/:id" element={<WeightInForm />} />
          <Route
            exact
            path="/change-category/:id"
            element={<ChangeCategoryForm />}
          />
          <Route
            exact
            path="/duels/:weightCategory/:duelId/:id1Contestant/:id2Contestant"
            element={<Duel />}
          />
          <Route exact path="/ladder" element={<CategoryToLadder />} />
          <Route
            exact
            path="/contestantsWeight/:category"
            element={<ContestantsCategoryList />}
          />
          <Route
            exact
            path="/bracket/:weightCategory"
            element={<TournamentBracket />}
          />
        </Routes>
      </Router>
    </div>
  );
}
