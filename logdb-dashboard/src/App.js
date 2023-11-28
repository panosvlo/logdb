import React, { useState } from 'react';
import './App.css';
import axios from 'axios';
import { BrowserRouter as Router, Route, Routes, Navigate, Link, useLocation } from 'react-router-dom';
import Sidebar from './Sidebar';
import MainContent from './MainContent';
import ApiForm from './ApiForm';
import apis from './apiConfig'; // Import your API configuration
import Login from './Login';
import Register from './Register';
import UploadLog from './UploadLog';

function App() {
  const [selectedApi, setSelectedApi] = useState(null);
  const [params, setParams] = useState({});
  const [data, setData] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token')); // If you're storing the token in localStorage

  const handleParamChange = (e) => {
    const { name, value } = e.target;
    setParams(prevParams => ({ ...prevParams, [name]: value }));
  };

  const isAuthenticated = () => {
    // Check if the token is valid (exists, not expired, etc.)
    // This is a simplified example; in a real app, you'd also need to check if the token is expired
    return token;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Prepare parameters for API 10
    let apiParams = { ...params };
    if (selectedApi.name === 'SQL Query 11') {
      apiParams = {
        ...apiParams,
        startDate: `${apiParams.startDate} 00:00:00`,
        endDate: `${apiParams.endDate} 23:59:59`
      };
    }
    try {
      const response = await axios.get(`http://localhost:8080${selectedApi.endpoint}`, {
        params: apiParams
      });
      setData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      setData(null);
    }
  };

  const handleSelectApi = (api) => {
    setSelectedApi(api);
    setData(null);
    setParams(api.params.reduce((acc, param) => {
      acc[param.name] = param.value || '';
      return acc;
    }, {}));
  };

  const updateToken = (newToken) => {
    if (newToken) {
      localStorage.setItem('token', newToken); // Save the new token to localStorage
    } else {
      localStorage.removeItem('token'); // Clear the token from localStorage on logout
    }
    setToken(newToken); // Update the state
  };
  const logout = () => {
    localStorage.removeItem('token'); // Remove the token from localStorage
    setToken(null); // Clear the token state
  };


  const ContentWithSidebar = () => {
    const location = useLocation();

    return (
      <>
        {location.pathname !== '/upload' && <Sidebar apis={apis} selectedApi={selectedApi} onSelectApi={handleSelectApi} />}
        <div className="content-container">
          <Routes>
            <Route path="/" element={selectedApi && <ApiForm api={selectedApi} params={params} onParamChange={handleParamChange} onSubmit={handleSubmit} />} />
            <Route path="/upload" element={<UploadLog setData={setData} />} />
            {/* Add more protected routes as needed */}
            <Route path="*" element={<Navigate replace to="/" />} />
          </Routes>
          {location.pathname !== '/upload' && <MainContent data={data} />}
        </div>
      </>
    );
  };

  if (!isAuthenticated()) {
    return (
      <Router>
        <Routes>
          <Route path="/login" element={<Login setToken={setToken} />} />
          <Route path="/register" element={<Register />} />
          <Route path="*" element={<Navigate replace to="/login" />} />
        </Routes>
      </Router>
    );
  }

  const TopBar = () => (
    <nav className="top-bar">
      <div className="nav-links">
        <Link to="/" className="nav-link">Home</Link>
        <Link to="/upload" className="nav-link">Upload</Link>
      </div>
      <div>
        <button onClick={logout} className="logout-button">Logout</button>
      </div>
    </nav>
  );

  return (
      <Router>
        {isAuthenticated() ? (
          <>
            <TopBar />
            <div className="app-container">
              <ContentWithSidebar />
            </div>
          </>
        ) : (
          <Routes>
            <Route path="/login" element={<Login setToken={setToken} />} />
            <Route path="/register" element={<Register />} />
            <Route path="*" element={<Navigate replace to="/login" />} />
          </Routes>
        )}
      </Router>
    );
  }

  export default App;
