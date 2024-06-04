import { useNavigate, Link, useParams } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import exitIcon from '../../assets/icons/exit.png';
import './ContestantsCategoryList.css';
import { useState } from 'react';

function ContestansCategoryList() {
  const { category } = useParams();
  const {
    data: contestants,
    isPending,
    error,
  } = useFetch('http://localhost:8000/contestants'); // tymczasowy adres na potrzeby testu
  const history = useNavigate();
  const [everyWithEvery, setEveryWithEvery] = useState(false);

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
    history('/ladder');
  };

  const handleEveryWithEveryChange = (event) => {
    setEveryWithEvery(event.target.checked);
  };
  const handleSubmitLadder = () => {
    // Implement your submit logic here
    // COMUNICATE WITH BACKEND
    // trzeba wziac pod uwage w ktorej stronie drabonki jest zawodnik i czy jest zaznaczona opcja
    // kazdy z kazdym i na tej podstawie wygernowac drabinke
  };

  const [selected, setSelected] = useState([]);
  const [pairs, setPairs] = useState([]);

  const generatePairs = (selectedContestants) => {
    const pairs = [];
    for (let i = 0; i < selectedContestants.length; i += 2) {
      if (selectedContestants[i + 1]) {
        pairs.push([selectedContestants[i], selectedContestants[i + 1]]);
      }
    }
    return pairs;
  };

  const handleSave = () => {
    const contestantPairs = generatePairs(selected);
    setPairs((prevPairs) => [...prevPairs, ...contestantPairs]);
    setSelected([]);
  };

  const handleClick = (id) => {
    setSelected((prevSelected) => {
      if (prevSelected.includes(id)) {
        return prevSelected.filter((item) => item !== id);
      }
      if (prevSelected.length === 2) {
        return [prevSelected[1], id];
      }
      return [...prevSelected, id];
    });
  };

  const handleRemove = (index) => {
    setPairs((prevPairs) => prevPairs.filter((_, i) => i !== index));
  };

  return (
    <div className="container">
      <div className="firstTeam">
        <h1>
          Choose contestants and "Save" to put them in different subladder
        </h1>
        <div>
          <ul>
            {pairs.map((pair, index) => (
              <li key={index}>{`${pair[0]} and ${pair[1]}`}
                <button
                  className="remove-button"
                  onClick={() => handleRemove(index)}
                >
                  Remove
                </button>
              </li>
            ))}
          </ul>
        </div>
      </div>
      <div className="contestants">
        <h2>Contestants</h2>
        {error && <div className="error-message">{error}</div>}
        {isPending && <div className="loading-message">Loading...</div>}
        {contestants && (
          <div className="contestant-list">
            {contestants
              .filter((contestant) => contestant.weightCategory === category)
              .map((contestant) => (
                <div
                  key={contestant.id}
                  className={`duel-preview ${
                    selected.includes(contestant.id) ? 'selected' : ''
                  }`}
                  onClick={() => handleClick(contestant.id)}
                  tabIndex={0}
                  role="button"
                  onKeyPress={(e) => {
                    if (e.key === 'Enter' || e.key === ' ') {
                      handleClick(contestant.id);
                    }
                  }}
                >
                  <p>{contestant.name}</p>
                </div>
              ))}
          </div>
        )}
        {/* <button type="button" className="filter-button">
        <img src={filterIcon} alt="filter-icon" />
      </button>
      <button type="button" className="sort-button">
        <img src={sortIcon} alt="sort-icon" />
      </button> */}
      </div>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" />
      </button>
      <div className="settings">
        <label>
          <input
            type="checkbox"
            checked={everyWithEvery}
            onChange={handleEveryWithEveryChange}
          />
          Round-Robin
        </label>
        <button type="button" onClick={handleSave}>
          Save
        </button>
        <button
          type="button"
          className="submit-button"
          onClick={handleSubmitLadder}
        >
          Submit Ladder
        </button>
      </div>
    </div>
  );
}

export default ContestansCategoryList;
