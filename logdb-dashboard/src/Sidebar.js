import React from 'react';

const Sidebar = ({ apis, selectedApi, onSelectApi }) => {
  return (
    <div className="sidebar">
      {apis.map((api, index) => (
        <button
          key={index}
          onClick={() => onSelectApi(api)}
          className={selectedApi && api.name === selectedApi.name ? 'active' : ''}
        >
          {api.name}
        </button>
      ))}
    </div>
  );
};

export default Sidebar;