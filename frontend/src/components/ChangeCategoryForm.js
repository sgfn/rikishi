import { useState } from 'react';
import { useLocation, useNavigate, useParams} from 'react-router-dom';
import usePatchData from '../hooks/usePatchData';
import './ChangeCategoryForm.css';
import exitIcon from '../../assets/icons/exit.png';

function ChangeCategoryForm() {
  const [newWeightCategory, setNewWeightCategory] = useState("Light-weight - M");
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
    patchData({ weightCategory: newWeightCategory });
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
      <div className="change-category">
        {error && <div className="error">{error}</div>}
        <form onSubmit={handleSubmit} className="change-category-form">
          {/* eslint-disable-next-line jsx-a11y/label-has-associated-control */}
          <label>Enter the new weight:</label>
          <br />
          <select
            name="category"
            id="category"
            required
            onChange={(e) => setNewWeightCategory(e.target.value)}
          >
            <option value="Light-weight - M">Light-weight</option>
            <option value="Middle-weight - M">Middle-weight</option>
            <option value="Heavy-weight - M">Heavy-weight</option>
            <option value="Open-weight - M">Open-weight</option>
          </select>
          <br />
          {!isPending && <button type="submit">Update Category</button>}
          {isPending && <button disabled type="submit">Updating Category...</button>}
        </form>
      </div>
    </>
  );
}

export default ChangeCategoryForm;
