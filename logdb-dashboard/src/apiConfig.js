const apis = [
  {
    name: 'Search Logs by IP',
    endpoint: '/api/logs/searchByIp',
    params: [
      { name: 'ip', type: 'text', placeholder: 'IP Address', value: '' }
    ],
    description: 'Search for all logs associated with a specific IP (source or destination).'
  },
  {
    name: 'SQL Query 1',
    endpoint: '/api/logs/countByType',
    description: 'Find the total logs per type that were created within a specified time range and sort them in a descending order. Please note that individual files may log actions of more than one type.',
    params: [
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 2',
    endpoint: '/api/logs/total-per-day',
    description: 'Find the total logs per day for a specific action type and time range.',
    params: [
      { name: 'method', type: 'text', placeholder: 'HTTP Method', value: 'GET' },
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 3',
    endpoint: '/api/logs/mostCommonLog',
    description: 'Find the most common log per source IP for a specific day.',
    params: [
      { name: 'specificDate', type: 'date', placeholder: 'Specific Date', value: '' }
    ]
  },
  {
    name: 'SQL Query 4',
    endpoint: '/api/logs/topBlockActions',
    description: 'Find the top-5 Block IDs with regards to total number of actions per day for a specific date range (for types that Block ID is available).',
    params: [
      { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
      { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
    ]
  },
	{
	name: 'SQL Query 5',
	endpoint: '/api/logs/referrers/multiple-resources',
	description: 'Find the referers (if any) that have led to more than one resources.',
	params: [] // No parameters required for this API
	},
  {
    name: 'SQL Query 6',
    endpoint: '/api/logs/secondMostCommonResource',
    description: 'Find the 2nd–most–common resource requested.',
    params: [
      // This API doesn't require any parameters
    ]
  },
  {
    name: 'SQL Query 7',
    endpoint: '/api/logs/accessLogsSize',
    description: 'Find the access log (all fields) where the size is less than a specified number.',
    params: [
      { name: 'size', type: 'number', placeholder: 'Size', value: '300' } // Default value set to 300
    ]
  },
    {
      name: 'SQL Query 8',
      endpoint: '/api/logs/blocks',
      description: 'Find the blocks that have been replicated the same day that they have also been served.',
      params: [] // No parameters required for this API
    },
    {
      name: 'SQL Query 9',
      endpoint: '/api/logs/blockAllocationsAndReplicationsSameHour',
      description: ' Find the blocks that have been replicated the same day and hour that they have also been served.',
      params: [] // No parameters required for this API
    },
	{
      name: 'SQL Query 10',
      endpoint: '/api/logs/accessLogs/firefox',
      description: 'Find access logs that specified a particular version of Firefox as their browser.',
      params: [] // No parameters required for this API
    },
    {
      name: 'SQL Query 11',
      endpoint: '/api/logs/methodUsage',
      description: 'Find IPs that have issued a particular HTTP method on a particular time range.',
      params: [
          { name: 'httpMethod', type: 'text', placeholder: 'HTTP Method', value: 'GET' },
          { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
          { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
        ]
      },
      {
        name: 'SQL Query 12',
        endpoint: '/api/logs/ipsWithTwoMethods',
        description: 'Find IPs that have issued two particular HTTP methods on a particular time range.',
        params: [
          { name: 'method1', type: 'text', placeholder: 'Method 1', value: 'GET' },
          { name: 'method2', type: 'text', placeholder: 'Method 2', value: 'POST' },
          { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
          { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' }
        ]
      },
      {
        name: 'SQL Query 13',
        endpoint: '/api/logs/distinctMethods',
        description: 'Find IPs that have issued any four distinct HTTP methods on a particular time range',
        params: [
          { name: 'startDate', type: 'date', placeholder: 'Start Date', value: '' },
          { name: 'endDate', type: 'date', placeholder: 'End Date', value: '' },
          { name: 'minMethods', type: 'number', placeholder: 'Minimum Methods', value: 4 }
        ]
      },
];

export default apis;
