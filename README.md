# conspector-parent

Initial example for the spring gateway + spring microservice (Deficiencies microservice used as example).
In order to check this example please follow the steps:
- Make sure that on the port 6379 you have REDIS server running (works for the redis-derver version 2.8.21, for latest verion of the redis (3.0.3) there were some issues). Redis-server can be running on VM.

- Make sure that you have MySQL db running locally on default port (user: root, password: '');
- Create db with name gateway. This database will be initialized by the application on the run time. Two users will be added:
'admin@gmail.com' and 'user@gmail.com' both with password 'admin'. admin@gmail.com will be assigned to roles ROLE_ADMIN and ROLE_USER, user@gmail.com will be assigned to the role ROLE_USER (check db_initializer.sql in resources for more details). Passwords will 
be stored in DB in encoded mode.

- In command line navigate to conspector-gateway folder and execute 
	- npm install
	- bower install
	- gulp dev	

- Open another command line, navigate to conspector-gateway folder and execute (previous terminal should be open)
	- mvn clean install
	- mvn spring-boot:run

- Open another command line, navigate to conspector-deficiencies folder and execute 
	- npm install
	- bower install
	- gulp dev		

- Open another command line, navigate to conspector-deficiencies folder and execute (previous terminal should be open): 
	- mvn clean install
	- mvn spring-boot:run	

Now you can try to access localhost:9000 (example of microservice). First thing that happens there - check if you are already logged in. If not, redirection will happen to localhost:8080 (spring gateway project).

For testing purposes use user/password created by db_initializer.sql script.

Note: UI part during development can be run on separate web server. There are some requirements/limitations with this approach: 
- Deficiencies microservice UI should use port 8084 (can be changed if needed)
- Logout functionality won't properly work for Deficiencies microservice.

Features:

- Example of paginatable/sortable end point (sample secured rest end point in gateway project that will return a list of existing users if user is logged in
and is assigned to the role ROLE_ADMIN). Here is the path to the end point: http://localhost:8080/gateway/getUsers (type of the request is GET). Response can be pagenated and sorted (i.e. http://localhost:8080/gateway/getUsers?pag=0&size=1&sort=username,desc&sort=enabled,asc)

- Logged in user can change his password with PUT request http://localhost:8080/gateway/changePassword that 
accept two attributes in the payload: currentPassword and newPassword.

- Possibility to remember password (will be kept for two months);

- Request input data validation (validation result will be returned with details per attribute - can be used to
display field validation in UI for each view of the form)

- Backend Localization enabled (based on cookie conspectorLanguage - cookie will stay valid for one month). I.e. request input validation results will be returned with respect of the current language. As example - there is validation for proper email format and password length for create user end point.

- Rest end point unit test examples (groovy based)

- profiling example (different db source settings based on
application.properties profile value (development/production));

- spring based audit mechanism (automatic population of the fields CreatedBy, ModifiedBy, CreatedAt, LastModifiedAt);

- transactional way of execution of the operations relevant for db modifications;

- concurrent entity modification locking (prevent data from been overwritten if changed between read/write operation);

- hibernate envers enables (read more here: http://docs.jboss.org/envers/docs/) - gives possibility to 
track entity modifications at individual field level (for test purposes activated for userEntity);
	
