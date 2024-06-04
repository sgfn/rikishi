import { Link, useNavigate } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './ContestantsList.css';
import filterIcon from '../../assets/icons/filter.png';
import sortIcon from '../../assets/icons/sort.png';
import exitIcon from '../../assets/icons/exit.png';

function ContestantsList() {
  const {
    data: contestants,
    isPending,
    error,
  } = useFetch('http://localhost:8000/contestants'); // tymczasowy adres na potrzeby testu
  const history = useNavigate();

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
    history('/');
  };

  // @ts-ignore
  return (
    <>
      <h2>Wrestlers</h2>
      {error && <div className="error-message">{error}</div>}
      {isPending && <div className="loading-message">Loading...</div>}
      {contestants && (
        <div className="contestant-list">
          {contestants.map((contestant) => (
            <div className="contestant-preview" key={contestant.id}>
              <Link to={`/contestants/${contestant.id}`}>
                <p>
                  {contestant.id}. {contestant.name}
                </p>
                <p className="weight-info">
                  {contestant.weight}kg ({contestant.weightCategory})
                </p>
              </Link>
            </div>
          ))}
        </div>
      )}
      {/* Te buttony na razie nic nie robią bo task nie dotyczył filtrowania/sortowania,
      to tylko koncepcja jak to mogłoby wyglądać na stronie */}
      <button type="button" className="filter-button">
        <img src={filterIcon} alt="filter-icon" />
      </button>
      <button type="button" className="sort-button">
        <img src={sortIcon} alt="sort-icon" />
      </button>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" />
      </button>
    </>
  );
}

export default ContestantsList;
