## Prerequisites

Here are the tools you'll need to install before you get started:
- **Java 17:** Download it from the [official Oracle site](https://www.oracle.com/java/technologies/downloads/#jdk17-windows)
- **Python 3:** Download it from the [official Python site](https://www.python.org/downloads/release/python-3115/)
- **Node.js 18.15:** Download it from the [official Node.js site](https://nodejs.org/download/release/v18.15.0/)
- **Docker (along with docker-compose):** Follow the instructions on
  the [official Docker site](https://docs.docker.com/engine/install/)
- **Tilt:** Follow the instructions on the [official Tilt site](https://docs.tilt.dev/install.html) or use one of the below commands (depending on your OS):
  ```bash
  #For Linux: 
  curl -fsSL https://raw.githubusercontent.com/tilt-dev/tilt/master/scripts/install.sh | bash
  #For Windows (with Powershell): 
  iex ((new-object net.webclient).DownloadString('https://raw.githubusercontent.com/tilt-dev/tilt/master/scripts/install.ps1'))
  #For MacOS: 
  curl -fsSL https://raw.githubusercontent.com/tilt-dev/tilt/master/scripts/install.sh | bash

## Getting Started

### Initial configuration before the startup

In the file [application.properties](src/main/resources/application.properties) you can set the log 
files that are going to be used for the initial population of the database. The files should be in the same
level as the root of the repository. 
```bash
# File paths
log.parsing.accesslog.path=access_log_full.short
log.parsing.hdfs.namesystem.path=HDFS_FS_Namesystem.log.short
log.parsing.hdfs.dataxceiver.path=HDFS_DataXceiver.log.short
```
You can also skip the initialization of the database by setting the below value in the application.properties
file to false.
```bash
# Initialize database
log.parsing.init.database=false
```

In the application.properties file you can also configure to connect
to another database:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```
### Starting the environment
To start the environment, run the following command:

`tilt up`

You can view all the resources being deployed at the
URL: [localhost:10350/r/(all)/overview](http://localhost:10350/r/(all)/overview). Here, you can click each resource to
see individual logs and status for each command and step. If one resource (or command) crashes for any reason, you can
restart it by clicking the 'Update' button next to the resource.

Initially, it will download the backend
dependencies ([backend dependencies overview](http://localhost:10350/r/backend_dependencies/overview)). Depending on
your internet connection, this may take some time. After that, it will continue with the frontend
dependencies ([frontend dependencies overview](http://localhost:10350/r/frontend_dependencies/overview)).

**Important Note:** The frontend dependencies will be installed successfully as long as the logs state "added `<number>`
packages" and "Server exited with exit code 0".

Once all resources are green, you can access the application by going to the
URL: [localhost:3000](http://localhost:3000/). You can create a new user to login to the application by going to the
URL: [localhost:3000/register](http://localhost:3000/register). Once you have successfully created the user, you can
login by visiting the URL: [http://localhost:3000/login](http://localhost:3000/login).