import { useState } from 'react';
import { useLocation, useNavigate, useParams} from 'react-router-dom';
import usePatchData from '../hooks/usePatchData';
import './ChangeCategoryForm.css';
import exitIcon from '../../assets/icons/exit.png';
import config from '../config.js'

function ChangeCategoryForm() {
  const [newWeightCategory, setNewWeightCategory] = useState("Light-weight - M");
  const { id } = useParams();
  const {
    isLoading: isPending,
    error,
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    response,
    patchData,
  } = usePatchData(`${config.backendUrl}/contestants/${id}`);
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
            <option value="Men Heavy-weight">Light-weight</option>
            <option value="Men Middle-weight">Middle-weight</option>
            <option value="Jr. Men Heavy-weight">Middle-weight</option>
            <option value="Jr. Men Middle-weight">Middle-weight</option>
            <option value="Women Heavy-weight">Light-weight</option>
            <option value="Women Middle-weight">Middle-weight</option>
            <option value="Jr. Women Heavy-weight">Middle-weight</option>
            <option value="Jr. Women Middle-weight">Middle-weight</option>
          </select>
          <br/>
          {!isPending && <button type="submit">Update Category</button>}
          {isPending && <button disabled type="submit">Updating Category...</button>}
        </form>
      </div>
    </>
  );
}

export default ChangeCategoryForm;
