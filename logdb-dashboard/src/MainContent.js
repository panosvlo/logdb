import React from 'react';

const logTypeDescriptions = {
  '1': 'access_log',
  '3': 'HDFS_FS_Namesystem',
  '2': 'HDFS_DataXceiver'
};

const MainContent = ({ data }) => {
  // Handle case where data is not an array
  if (!data) return null;

  // Check if data is an array, otherwise make it an array
  const dataArray = Array.isArray(data) ? data : [data];
  const keys = dataArray.length > 0 ? Object.keys(dataArray[0]) : [];

  return (
    <div className="main-content">
      <table>
        <thead>
          <tr>
            {keys.map(key => <th key={key}>{key === 'logTypeId' ? 'Log Type' : key}</th>)}
          </tr>
        </thead>
        <tbody>
          {dataArray.map((item, index) => (
            <tr key={index}>
              {keys.map(key => (
                <td key={key}>
                  {key === 'logTypeId' ? logTypeDescriptions[item[key].toString()] : item[key]}
                 </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default MainContent;
