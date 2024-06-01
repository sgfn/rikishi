import { useNavigate, Link, useParams } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import exitIcon from '../../assets/icons/exit.png';
import './ContestantsCategoryList.css';
import { useState } from 'react';
import config from '../config.js';

function ContestansCategoryList() {
  const { category } = useParams();
  const {
    data: data,
    isPending,
    error,
  } = useFetch(`${config.backendUrl}/contestants`);
  const history = useNavigate();
  const [firstTeam, setFirstTeam] = useState([]);
  const [secondTeam, setSecondTeam] = useState([]);
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
      //
      // OK, zróbcie POST pod /duels/generateLadder
      //    ze strukturą:
      //      {
      //        "weightCategory": "Men Heavy-Weight",
      //        "firstBracketContestant": 2,            // id lub null
      //        "secondBracketContestant": null         // id lub null
      //      }
      //   Desygnować można (nie trzeba) po jednym zawodniku do każdej ze stron.
      //   Nie robimy zaznaczania opcji każdy z każdym,
      //    mamy predefiniowane zasady dla danej liczby zawodników
  };

  const handleAddToFirstTeam = (contestant) => {
    setFirstTeam((prevFirstTeam) => {
      if (prevFirstTeam.some((member) => member.id === contestant.id)) {
        return prevFirstTeam;
      }
      setSecondTeam((prevSecondTeam) =>
        prevSecondTeam.filter((member) => member.id !== contestant.id),
      );
      return [...prevFirstTeam, contestant];
    });
  };

  const handleAddToSecondTeam = (contestant) => {
    setSecondTeam((prevSecondTeam) => {
      if (prevSecondTeam.some((member) => member.id === contestant.id)) {
        return prevSecondTeam;
      }
      setFirstTeam((prevFirstTeam) =>
        prevFirstTeam.filter((member) => member.id !== contestant.id),
      );
      return [...prevSecondTeam, contestant];
    });
  };

  return (
    <div className="container">
      <div className="firstTeam">
        <h1>First team</h1>
        {firstTeam.map((contestant) => (
          <div key={contestant.id}>
            <p>{contestant.name}</p>
          </div>
        ))}
      </div>
      <div className="contestants">
        <h2>Contestants</h2>
        {error && <div className="error-message">{error}</div>}
        {isPending && <div className="loading-message">Loading...</div>}
        {data && (
          <div className="contestant-list">
            {data.contestants
              .filter((contestant) => contestant.weightCategory === category)
              .map((contestant) => (
                <div className="duel-preview" key={contestant.id}>
                  <p>{contestant.name}</p>
                  <div className="addToTeam">
                    <button
                      type="button"
                      className="addButton"
                      onClick={() => handleAddToFirstTeam(contestant)}
                    >
                      Add to 1 team
                    </button>
                    <button
                      type="button"
                      className="addButton"
                      onClick={() => handleAddToSecondTeam(contestant)}
                    >
                      Add to 2 team
                    </button>
                  </div>
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
      <div className="SecondTeam">
        <h1>Second team</h1>
        {secondTeam.map((contestant) => (
          <div key={contestant.id}>
            <p>{contestant.name}</p>
          </div>
        ))}
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
          Every with Every
        </label>
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
