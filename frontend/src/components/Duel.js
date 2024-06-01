import { useNavigate, useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import useFetch from '../hooks/useFetch';
import './Duel.css';
import exitIcon from '../../assets/icons/exit.png';
import usePatchData from '../hooks/usePatch';

function Duel() {
  const { weightCategory, duelId, id1Contestant, id2Contestant } = useParams();

  const {
    data: contestant2,
    isPending: isPending2,
    error: error2,
  } = useFetch(`http://localhost:8000/contestants/${id2Contestant}`);

  const {
    data: contestant1,
    isPending: isPending1,
    error: error1,
  } = useFetch(`http://localhost:8000/contestants/${id1Contestant}`);

  const { data: duel, loading } = useFetch(
    `http://localhost:8000/duels/${duelId}`,
    [],
    { suspense: true },
  );

  const [isWinnerSelected, setIsWinnerSelected] = useState(false);
  const [clicked, setClicked] = useState(false);
  const [clicked2, setClicked2] = useState(false);

  useEffect(() => {
    if (!loading && duel) {
      const winnerExists = duel.winner !== '' && duel.winner !== undefined;
      setIsWinnerSelected(winnerExists);
    }
    if (!loading && duel && duel.winner == contestant1.id) {
      setClicked(true);
    } else if (!loading && duel && duel.winner == contestant2.id) {
      setClicked2(true);
    }
  }, [loading, duel]);

  const history = useNavigate();

  const handleExit = () => {
    console.log('Exit button pressed');
    history(`/categories/${weightCategory}`);
  };

  const { isLoading, error, response, usePatch } = usePatchData(
    `http://localhost:8000/duels/${duelId}`,
  );

  const handleWinner = (winnerId) => {
    setIsWinnerSelected(!isWinnerSelected);
    const numberValue = parseInt(winnerId, 10);
    usePatch({ winner: numberValue });
    console.log(winnerId);
    console.log(contestant1.id);
    if (winnerId == contestant1.id) {
      setClicked(true);
      setClicked2(false);
    } else {
      setClicked2(true);
      setClicked(false);
    }
  };
  const handleChangeWinner = () => {
    setIsWinnerSelected(!isWinnerSelected);
  };

  return (
    <>
      <h2>Duel in {weightCategory}</h2>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" className="exit-icon" />
      </button>
      {contestant1 && contestant2 && duel && (
        <div className="contestant-profile">
          {isPending1 && isPending2 && <div>Loading...</div>}
          {error1 && error2 && <div className="error">{error1}</div>}
          {contestant1 && (
            <p className={clicked ? 'text-duel-winner' : 'text-duel'}>
              {contestant1.name}
            </p>
          )}
          <p className="text-duel">VS</p>
          {contestant2 && (
            <p className={clicked2 ? 'text-duel-winner' : 'text-duel'}>
              {contestant2.name}
            </p>
          )}
          <div>
            <h2>Select The Winner: </h2>
            <div className="winner">
              <button
                type="button"
                disabled={isWinnerSelected}
                onClick={() => handleWinner(id1Contestant)}
              >
                <p>{contestant1.name}</p>
              </button>
              <button
                type="button"
                disabled={isWinnerSelected}
                onClick={() => handleWinner(id2Contestant)}
              >
                <p>{contestant2.name}</p>
              </button>
              <button
                type="button"
                disabled={!isWinnerSelected}
                onClick={() => handleChangeWinner()}
              >
                <p>Change the winner:</p>
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default Duel;
