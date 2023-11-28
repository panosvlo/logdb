const apis = [
  {
    name: 'API 1',
    endpoint: '/api/logs/countByType',
    params: [
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'API 2',
    endpoint: '/api/logs/total-per-day',
    params: [
      { name: 'method', type: 'text', placeholder: 'HTTP Method', value: 'GET' }, // Assuming 'GET' is the default value
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'API 3',
    endpoint: '/api/logs/mostCommonLog',
    params: [
      { name: 'specificDate', type: 'date', placeholder: 'Specific Date', value: '' }
    ]
  },
  {
    name: 'API 4',
    endpoint: '/api/logs/topBlockActions',
    params: [
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'API 5',
    endpoint: '/api/logs/secondMostCommonResource',
    params: [
      // This API doesn't require any parameters
    ]
  },
  {
    name: 'API 6',
    endpoint: '/api/logs/accessLogsSize',
    params: [
      { name: 'size', type: 'number', placeholder: 'Size', value: '300' } // Default value set to 300
    ]
  },
];

export default apis;
