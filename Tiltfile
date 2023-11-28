docker_compose('docker-compose.yaml')
# Add labels to Docker services
dc_resource('logdbpostgres', labels=["database"])

# Frontend Configuration
local_resource(
  'frontend_dependencies',
  cmd='cd logdb-dashboard && npm install',
  labels=['dependencies'],
  deps=['./frontend/'],
)
local_resource(
  'frontend',
  serve_cmd='cd logdb-dashboard && npm start',
  labels=['frontend'],
  resource_deps=['backend', 'frontend_dependencies']
)



# Backend Configuration

local_resource(
  'backend_dependencies',
  cmd='mvn install -DskipTests',
  labels=['dependencies'],
)

local_resource(
  'backend',
  serve_cmd='mvn spring-boot:run',
  labels=['backend'],
  resource_deps=['logdbpostgres', 'backend_dependencies'],
)