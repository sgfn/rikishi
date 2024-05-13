import { useNavigate, useParams } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import './Duel.css';
import exitIcon from '../../assets/icons/exit.png';

function Duel() {
  const { weightCategory, id1Contestant, id2Contestant } = useParams();

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

  const history = useNavigate();

  const handleExit = () => {
    console.log('Exit button pressed');
    history(`/categories/${weightCategory}`);
  };

  return (
    <>
      <h2>Duel in {weightCategory}</h2>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" className="exit-icon" />
      </button>
      <div className="contestant-profile">
        {isPending1 && isPending2 && <div>Loading...</div>}
        {error1 && error2 && <div className="error">{error1}</div>}
        {contestant1 && <p>{contestant1.name}</p>}
        <p>VS</p>
        {contestant2 && <p>{contestant2.name}</p>}
      </div>
    </>
  );
}

export default Duel;
