import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './WeightInForm.css';
import exitIcon from '../../assets/icons/exit.png';

function WeightInForm() {
  const [newWeight, setNewWeight] = useState(null);
  const [isPending, setIsPending] = useState(false);
  const [error, setError] = useState(null);
  const { id } = useParams();
  const history = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsPending(true);
    // eslint-disable-next-line promise/catch-or-return
    fetch(`http://localhost:8000/contestants/${id}`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ weight: newWeight }),
      // eslint-disable-next-line promise/always-return
    })
      // eslint-disable-next-line promise/always-return
      .then((res) => {
        // eslint-disable-next-line promise/always-return
        if (!res.ok) {
          throw Error('Could not update weight.');
        }
        setIsPending(false);
        setError(null);
        history(`/contestants/${id}`);
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(error);
        setError(err.message);
        setIsPending(false);
      });
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
