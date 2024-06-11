import { useLocation, useNavigate } from 'react-router-dom';
import './MainView.css';
import {useEffect, useState} from 'react';
import startPhoto from '../../assets/sumuStart.jpg';
import config from '../config.js';

function MainView() {
  const navigator = useNavigate();
  const [uploadStatus, setUploadStatus] = useState(null);
  const [inputValue, setInputValue] = useState('');
  const [bracketSubmitted, setBracketSubmitted] = useState(false);
  const [weightCategory, setWeightCategory] = useState(null);

  const bracketInfo = useLocation();
  useEffect( () => {
    if (bracketInfo.state != null) {
      setBracketSubmitted(bracketInfo.state.bracketSubmitted);
      setWeightCategory(bracketInfo.state.weightCategory);
    }
  }, [bracketInfo]);

  const handleConstants = () => {
    navigator('/contestants');
  };
  const handleDuels = () => {
    navigator('/categories');
  };
  const handleRaport = () => {
    navigator('/ladder');
  };

  const handleGoToBracket = () => {
    navigator(`/bracket/${weightCategory}`);
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0].path; // Access the selected file
    setInputValue(file);
    console.log(file);
  };

  const handleUpload = async () => {
    if (inputValue) {
      const requestBody = {
        path: inputValue,
      };

      try {
        const response = await fetch(
          `${config.backendUrl}/contestants/import-csv`,
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
          },
        );

        if (response.ok) {
          setUploadStatus('File uploaded successfully');
        } else {
          setUploadStatus('Failed to upload file');
        }
      } catch (error) {
        setUploadStatus('Error uploading file');
        console.error('Error uploading file:', error);
      }
    }
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
        {bracketSubmitted && (
          <button onClick={handleGoToBracket} type="button" className="goToList">
            Go to Tournament Bracket
          </button>
        )}
      </div>
      <div className="upload-container">
        <input type="file" className="file-input" onChange={handleFileChange} />
        <button onClick={handleUpload} type="button">
          Upload CSV
          {uploadStatus && <p>{uploadStatus}</p>}
        </button>
      </div>
    </div>
  );
}

export default MainView;
