import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './ContestantsList.css';
import filterIcon from '../../assets/icons/filter.png';
import sortIcon from '../../assets/icons/sort.png';
import exitIcon from '../../assets/icons/exit.png';
import config from '../config.js';

function ContestantsList() {
  const [sort, setSort] = useState('');
  const [minAge, setMinAge] = useState('');
  const [maxAge, setMaxAge] = useState('');
  const [minWeight, setMinWeight] = useState('');
  const [maxWeight, setMaxWeight] = useState('');
  const [sex, setSex] = useState('');

  const createQueryParams = () => {
    const params = new URLSearchParams();
    if (sort) params.append('sort', sort);
    if (minAge) params.append('minAge', minAge);
    if (maxAge) params.append('maxAge', maxAge);
    if (minWeight) params.append('minWeight', minWeight);
    if (maxWeight) params.append('maxWeight', maxWeight);
    if (sex) params.append('sex', sex);
    return params.toString();
  };

  const { data, isPending, error } = useFetch(
    `${config.backendUrl}/contestants?${createQueryParams()}`,
  );
  const history = useNavigate();

  const handleExit = () => {
    console.log('Exit button pressed');
    history('/');
  };

  return (
    <>
      <h2>Wrestlers</h2>
      {error && <div className="error-message">{error}</div>}
      {isPending && <div className="loading-message">Loading...</div>}
      <div className="filters">
        <select value={sort} onChange={(e) => setSort(e.target.value)}>
          <option value="">Sort By</option>
          <option value="name">Name</option>
          <option value="age">Age</option>
          <option value="weight">Weight</option>
        </select>
        <input
          type="number"
          placeholder="Min Age"
          value={minAge}
          onChange={(e) => setMinAge(e.target.value)}
        />
        <input
          type="number"
          placeholder="Max Age"
          value={maxAge}
          onChange={(e) => setMaxAge(e.target.value)}
        />
        <input
          type="number"
          placeholder="Min Weight"
          value={minWeight}
          onChange={(e) => setMinWeight(e.target.value)}
        />
        <input
          type="number"
          placeholder="Max Weight"
          value={maxWeight}
          onChange={(e) => setMaxWeight(e.target.value)}
        />
        <select value={sex} onChange={(e) => setSex(e.target.value)}>
          <option value="">Sex</option>
          <option value="m">Male</option>
          <option value="f">Female</option>
        </select>
      </div>
      {data && (
        <div className="contestant-list">
          {data.contestants.map((contestant) => (
            <div className="contestant-preview" key={contestant.id}>
              <Link to={`/contestants/${contestant.id}`}>
                <p>
                  {contestant.id}. {contestant.name}
                </p>
                <p className="weight-info">
                  {contestant.weight}kg ({contestant.weightCategory})
                </p>
                <p className="weight-info">Age: {contestant.age}</p>
              </Link>
            </div>
          ))}
        </div>
      )}
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" />
      </button>
    </>
  );
}

export default ContestantsList;
