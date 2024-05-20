import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import usePatchData from '../hooks/usePatchData';
import './WeightInForm.css';
import exitIcon from '../../assets/icons/exit.png';


function WeightInForm() {
  const [newWeight, setNewWeight] = useState(null);
  const { id } = useParams();
  const {
    isLoading: isPending,
    error,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    response,
    patchData,
  } = usePatchData(`http://localhost:8000/contestants/${id}`);
  const history = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    patchData({ weight: newWeight });
    history(`/contestants/${id}`);
  };

  const handleExit = () => {
    // eslint-disable-next-line no-console
    console.log('Exit button pressed');
    history(`/contestants/${id}`);
  };

  return (
    <>
      <button type="button" className="exit-button" onClick={handleExit}>
        <img src={exitIcon} alt="exit-icon" className="exit-icon" />
      </button>
      <div className="weight-in">
        {error && <div className="error">{error}</div>}
        <form onSubmit={handleSubmit} className="weight-in-form">
          {/* eslint-disable-next-line jsx-a11y/label-has-associated-control */}
          <label>Enter the new weight:</label>
          <br />
          <input
            type="number"
            required
            value={newWeight}
            onChange={(e) => setNewWeight(e.target.value)}
          />
          kg
          <br />
          {!isPending && <button type="submit">Update Weight</button>}
          {isPending && <button disabled type="submit">Updating Weight...</button>}
        </form>
      </div>
    </>
  );
}

export default WeightInForm;
