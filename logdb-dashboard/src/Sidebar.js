import React from 'react';

const Sidebar = ({ apis, onSelectApi }) => {
  return (
    <div className="sidebar">
      {apis.map((api, index) => (
        <button key={index} onClick={() => onSelectApi(api)}>
          {api.name}
        </button>
      ))}
    </div>
  );
};

export default Sidebar;
