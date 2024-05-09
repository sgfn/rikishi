import { useNavigate } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './ContestantProfile.css';
import exitIcon from '../../assets/icons/exit.png';

function ContestantProfile() {
  // const {id} = useParams();
  // Normalnie powinien brać parametry z linku, dla testu na razie to hardcoduje
  const id = 2;
  const {
    data: contestant,
    isPending,
    error,
  } = useFetch(`http://localhost:8000/contestants/${id}`); // adres tymczasowy do testów
  const history = useNavigate();

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
    // na razie przenosi do pustej strony
    history('/contestants');
  };

  return (
    <>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" className="exit-icon" />
      </button>
      <div className="contestant-profile">
        <h2>Wrestler Profile</h2>
        {isPending && <div>Loading...</div>}
        {error && <div className="error">{error}</div>}
        {contestant && (
          <div className="contestant-profile-container">
            <img
              src={contestant.image}
              alt={contestant.name}
              className="contestant-profile-image"
            />
            <div className="contestant-stats">
              <p>
                <strong>Name:</strong>
                <div className="p-right">{contestant.name}</div>
              </p>
              <p>
                <strong>Age:</strong>
                <div className="p-right">{contestant.age}</div>
              </p>
              <p>
                <strong>Country:</strong>
                <div className="p-right">{contestant.country}</div>
              </p>
              <p>
                <strong>Weight:</strong>
                <div className="p-right">{contestant.weight} kg</div>
              </p>
              <p>
                <strong>Category:</strong>
                <div className="p-right">{contestant.weightCategory}</div>
              </p>
            </div>
          </div>
        )}
      </div>
      {contestant && (
        <div className="buttons-container">
          <button type="button" className="weight-in-button">
            Weight-In
          </button>
          <button type="button" className="category-button">
            Change Category
          </button>
        </div>
      )}
    </>
  );
}

export default ContestantProfile;
