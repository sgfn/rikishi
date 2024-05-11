/* eslint-disable prettier/prettier */
import { Link, useNavigate } from 'react-router-dom';
import './MainView.css';
import startPhoto from '../../assets/sumuStart.jpg';

function MainView() {
  const navigator = useNavigate();

  const handleConstants = () => {
    navigator('/contestants');
  };
  return (
    <div className="main-view">
      <h2>Welcome to the sumo competition!</h2>
      <img className="start-photo" src={startPhoto} alt="startPhoto" />
      <div className="button-container">
        <button
          onClick={handleConstants}
          type="button"
          className="goToContestantsList"
        >
          Go to Contestants list
        </button>
      </div>
    </div>
  );
}

export default MainView;
