import React, { useState } from 'react';
import axios from 'axios';

function LogCountByType() {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [result, setResult] = useState(null);

  const handleSubmit = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/logs/countByType?startDate=${startDate}&endDate=${endDate}`);
      setResult(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      setResult(null);
    }
  };

  return (
    <div>
      <h2>Get Log Count By Type</h2>
      <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
      <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />
      <button onClick={handleSubmit}>Submit</button>
      {result && <pre>{JSON.stringify(result, null, 2)}</pre>}
    </div>
  );
}

export default LogCountByType;
