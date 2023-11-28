import React, { useState } from 'react';
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
    try {
      const response = await axios.get(`http://localhost:8080${selectedApi.endpoint}`, {
        params: params
      });
      setData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      setData(null);
    }
  };

  const handleSelectApi = (api) => {
    setSelectedApi(api);
    setParams(api.params.reduce((acc, param) => {
      acc[param.name] = param.value || '';
      return acc;
    }, {}));
  };

  return (
      <div className="app">
        <Sidebar apis={apis} onSelectApi={handleSelectApi} />
        {selectedApi && (
          <ApiForm
            api={selectedApi}
            params={params} // Pass the params state as a prop to ApiForm
            onParamChange={handleParamChange}
            onSubmit={handleSubmit}
          />
        )}
        <MainContent data={data} />
      </div>
    );
  }

export default App;