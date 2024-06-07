import { useNavigate } from 'react-router-dom';
import './MainView.css';
import startPhoto from '../../assets/sumuStart.jpg';
import { useState } from 'react';

function MainView() {
  const navigator = useNavigate();
  const [uploadStatus, setUploadStatus] = useState(null);

  const handleConstants = () => {
    navigator('/contestants');
  };
  const handleDuels = () => {
    navigator('/categories');
  };
  const handleRaport = () => {
    navigator('/ladder');
  };
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleUpload = () => {
    if (file) {
      const reader = new FileReader();
      reader.onload = async (event) => {
        const csvContent = event.target.result;

        const requestBody = {
          path: csvContent,
        };

        try {
          const response = await fetch('/contestants/import-csv', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
          });

          if (response.ok) {
            setUploadStatus('File uploaded successfully');
          } else {
            setUploadStatus('Failed to upload file');
          }
        } catch (error) {
          setUploadStatus('Error uploading file');
          console.error('Error uploading file:', error);
        }
      };
      reader.readAsText(file);
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
      </div>
      <div className="upload-container">
        <input
          className="file-input"
          type="file"
          accept=".csv"
          onChange={handleFileChange}
        />
        <button onClick={handleUpload} type="button">
          Upload CSV
          {uploadStatus && <p>{uploadStatus}</p>}
        </button>
      </div>
    </div>
  );
}

export default MainView;
