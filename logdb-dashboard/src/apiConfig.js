const apis = [
  {
    name: 'SQL Query 1',
    endpoint: '/api/logs/countByType',
    params: [
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 2',
    endpoint: '/api/logs/total-per-day',
    params: [
      { name: 'method', type: 'text', placeholder: 'HTTP Method', value: 'GET' }, // Assuming 'GET' is the default value
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 3',
    endpoint: '/api/logs/mostCommonLog',
    params: [
      { name: 'specificDate', type: 'date', placeholder: 'Specific Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 4',
    endpoint: '/api/logs/topBlockActions',
    params: [
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 5',
    endpoint: '/api/logs/secondMostCommonResource',
    params: [
      // This API doesn't require any parameters
    ]
  },
  {
    name: 'SQL Query 6',
    endpoint: '/api/logs/accessLogsSize',
    params: [
      { name: 'size', type: 'number', placeholder: 'Size', value: '300' } // Default value set to 300
    ]
  },
  {
      name: 'SQL Query 7',
      endpoint: '/api/logs/accessLogs/firefox',
      params: [] // No parameters required for this API
    },
    {
      name: 'SQL Query 8',
      endpoint: '/api/logs/blocks',
      params: [] // No parameters required for this API
    },
    {
      name: 'SQL Query 9',
      endpoint: '/api/logs/blockAllocationsAndReplicationsSameHour',
      params: [] // No parameters required for this API
    },
    {
        name: 'SQL Query 10',
          endpoint: '/api/logs/methodUsage',
          params: [
            { name: 'httpMethod', type: 'text', placeholder: 'HTTP Method', value: 'GET' },
            { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
            { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
          ]
      },
      {
        name: 'SQL Query 11',
        endpoint: '/api/logs/ipsWithTwoMethods',
        params: [
          { name: 'method1', type: 'text', placeholder: 'Method 1', value: 'GET' },
          { name: 'method2', type: 'text', placeholder: 'Method 2', value: 'POST' },
          { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
          { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
        ]
      },
      {
        name: 'SQL Query 12',
        endpoint: '/api/logs/distinctMethods',
        params: [
          { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
          { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' },
          { name: 'minMethods', type: 'number', placeholder: 'Minimum Methods', value: 2 }
        ]
      },
      {
        name: 'SQL Query 13',
        endpoint: '/api/logs/referrers/multiple-resources',
        params: [] // No parameters required for this API
      },
];

export default apis;
