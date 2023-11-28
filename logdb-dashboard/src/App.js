import React, { useState } from 'react';
import './App.css';
import axios from 'axios';
import Sidebar from './Sidebar';
import MainContent from './MainContent';
import ApiForm from './ApiForm';
import apis from './apiConfig'; // Import your API configuration

function App() {
  const [selectedApi, setSelectedApi] = useState(null);
  const [params, setParams] = useState({});
  const [data, setData] = useState(null);

  const handleParamChange = (e) => {
    const { name, value } = e.target;
    setParams(prevParams => ({ ...prevParams, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Prepare parameters for API 10
    let apiParams = { ...params };
    if (selectedApi.name === 'API 10') {
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

  return (
    <div className="app-container">
      <Sidebar apis={apis} selectedApi={selectedApi} onSelectApi={handleSelectApi} />
      <div className="content-container">
        {selectedApi && (
          <ApiForm
            api={selectedApi}
            params={params}
            onParamChange={handleParamChange}
            onSubmit={handleSubmit}
          />
        )}
        <MainContent data={data} />
      </div>
    </div>
  );
}

export default App;
