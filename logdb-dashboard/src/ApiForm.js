import React from 'react';

const ApiForm = ({ api, params, onParamChange, onSubmit }) => {
  if (!api) return null;

  return (
    <form onSubmit={onSubmit}>
      <div className="api-description">{api.description}</div>
      {api.params.map((param, index) => (
        <input
          key={index}
          type={param.type}
          name={param.name}
          placeholder={param.placeholder}
          value={params[param.name]}
          onChange={onParamChange}
          required
        />
      ))}
      <button type="submit">Fetch Data</button>
    </form>
  );
};

export default ApiForm;
