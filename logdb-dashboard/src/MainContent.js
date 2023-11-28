import React from 'react';

const MainContent = ({ data }) => {
  if (!data) return null;

  const keys = data.length > 0 ? Object.keys(data[0]) : [];

  return (
    <div className="main-content">
      <table>
        <thead>
          <tr>
            {keys.map(key => <th key={key}>{key}</th>)}
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              {keys.map(key => <td key={key}>{item[key]}</td>)}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default MainContent;
