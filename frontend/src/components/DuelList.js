import { useNavigate, Link, useParams } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './DuelList.css';
import exitIcon from '../../assets/icons/exit.png';
import config from '../config.js';

function DuelList() {
  const { category } = useParams();
  const {
    data: data,
    isPending,
    error,
  } = useFetch(`${config.backendUrl}/duels/curr`);
  const history = useNavigate();

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
    history('/categories');
  };

  return (
    <>
      <h2>Duels</h2>
      {error && <div className="error-message">{error}</div>}
      {isPending && <div className="loading-message">Loading...</div>}
      {data && (
        <div className="duel-list">
          {data.duels
            .filter((duel) => duel.weightCategory === category)
            .map((duel) => (
              <div className="duel-preview" key={duel.id}>
                <Link
                  to={`/duels/${duel.weightCategory}/${duel.id}/${duel.id1Contestant}/${duel.id2Contestant}`}
                >
                  <p>Duel no. {duel.id}</p>
                </Link>
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
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" />
      </button>
    </>
  );
}

export default DuelList;
