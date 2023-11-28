import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function UploadLog() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [logTypeId, setLogTypeId] = useState('');
  const [uploadMessage, setUploadMessage] = useState('');
  const navigate = useNavigate();

  const handleFileSelect = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleUpload = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append('file', selectedFile);
    formData.append('logTypeId', logTypeId);

    try {
      const response = await axios.post('http://localhost:8080/api/logs/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      setUploadMessage(response.data);
      // Optionally navigate to another route after successful upload
      // navigate('/some-route');
    } catch (error) {
      console.error('Upload failed:', error);
      setUploadMessage('Failed to upload and parse file.');
    }
  };

  return (
    <div className="upload-form">
      <h2>Upload Log File</h2>
      {uploadMessage && <p className="message">{uploadMessage}</p>}
      <form onSubmit={handleUpload}>
        <div>
          <label>Log Type:</label>
          <select value={logTypeId} onChange={(e) => setLogTypeId(e.target.value)} required>
            <option value="">Select Log Type</option>
            <option value="1">access_log</option>
            <option value="3">HDFS_FS_Namesystem</option>
            <option value="2">HDFS_DataXceiver</option>
          </select>
        </div>
        <div>
          <label>File:</label>
          <input type="file" onChange={handleFileSelect} required />
        </div>
        <button type="submit">Upload</button>
      </form>
    </div>
  );
}

export default UploadLog;
