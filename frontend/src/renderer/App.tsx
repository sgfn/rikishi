import { MemoryRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import ContestantsList from '../components/ContestantsList';

export default function App() {
  return (
    <Router>
      <Routes>
        {/* Nie wiem jak w electronie przemieszczać się pomiędzy stronami, więc na potrzeby testu
        daje komponent do roota */}
        <Route path="/" element={<ContestantsList />} />
      </Routes>
    </Router>
  );
}
