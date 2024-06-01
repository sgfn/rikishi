import { useNavigate } from 'react-router-dom';
import './MainView.css';
import startPhoto from '../../assets/sumuStart.jpg';

function MainView() {
  const navigator = useNavigate();

  const handleConstants = () => {
    navigator('/contestants');
  };
  const handleDuels = () => {
    navigator('/categories');
  };
  const handleRaport = () => {
    navigator('/ladder');
  };
  return (
    <div className="main-view">
      <h2>Welcome to the sumo competition!</h2>
      <img className="start-photo" src={startPhoto} alt="startPhoto" />
      <div className="button-container">
        <button onClick={handleConstants} type="button" className="goToList">
          Go to Contestants list
        </button>
        <button onClick={handleDuels} type="button" className="goToList">
          Go to Duels
        </button>
        <button onClick={handleRaport} type="button" className="goToList">
          Go to Ladder tournament
        </button>
      </div>
    </div>
  );
}

export default MainView;
