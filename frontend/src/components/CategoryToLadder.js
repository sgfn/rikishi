import { useNavigate, Link } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './CategoryList.css';
import exitIcon from '../../assets/icons/exit.png';
import config from '../config.js';

function CategoryList() {
  const {
    data: data,
    isPending,
    error,
  } = useFetch(`${config.backendUrl}/contestants`);
  const history = useNavigate();

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
    history('/');
  };

  // @ts-ignore
  return (
    <>
      <h2>Categories</h2>
      {error && <div className="error-message">{error}</div>}
      {isPending && <div className="loading-message">Loading...</div>}
      {data && (
        <div className="category-list">
          {[
            ...new Set(
              data.contestants.map((contestant) => contestant.weightCategory),
            ),
          ].map((weightCategory) => (
            <div className="category-preview">
              <Link to={`/contestantsWeight/${weightCategory}`}>
                <p>{weightCategory}</p>
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

export default CategoryList;
