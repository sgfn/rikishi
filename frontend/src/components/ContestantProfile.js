import { useNavigate, useParams } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import useCategoryCheck from '../hooks/useCategoryCheck';
import './ContestantProfile.css';
import exitIcon from '../../assets/icons/exit.png';
import config from '../config.js';

function ContestantProfile() {
  const history = useNavigate();
  const { id } = useParams();
  const {
    data: contestant,
    isPending,
    error: errorFetch,
  } = useFetch(`${config.backendUrl}/contestants/${id}`);
  const {
    wrongCategoryFlag,
    error: errorCategoryCheck
  } = useCategoryCheck(`${config.backendUrl}/contestants/${id}/validateCategory`)

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
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
        {errorFetch && <div className="error">{errorFetch}</div>}
        {errorCategoryCheck && <div className="error">{errorCategoryCheck}</div>}
        {wrongCategoryFlag && (
          <div className="wrong-category">
            The weight of the contestant does not match his weight category.
            <br />
            Consider changing the category or update the weight.
          </div>
        )}
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
                <div
                  className="p-right"
                  style={{ color: wrongCategoryFlag ? 'red' : '#333' }}
                >
                  {contestant.weight} kg
                </div>
              </p>
              <p>
                <strong>Category:</strong>
                <div
                  className="p-right"
                  style={{ color: wrongCategoryFlag ? 'red' : '#333' }}
                >
                  {contestant.weightCategory}
                </div>
              </p>
            </div>
          </div>
        )}
      </div>
      {contestant && (
        <div className="buttons-container">
          <button
            type="button"
            className="weight-in-button"
            onClick={() => history(`/weight-in/${id}`)}
          >
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
