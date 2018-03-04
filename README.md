# FriendManagemet-Spring-Boot

### Steps to run the Application :
1. Build the project using from **FriendManagemet-Spring-Boot/application**: `mvn clean install`
2. Run `docker-compose rm` // to remove services
3. Run `docker-compose up --build` // build and create services
4. Launch application with `http://<docker-ip>:8080/`

### Endpoints to be tested:
1. Add Friends `[ /add ] `
2. Common Frineds `[ /common ] `
3. Get Friends `[ /friends ] `
4. Subscribe `[ /subscribe ] `
5. Block `[ /block ] `
6. Recipients `[ /recipients ]`

Use Swagger UI for testing rest calls: `[ /swagger-ui.html ]`
