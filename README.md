# conspector-parent

Initial example for the spring gateway + spring microservice (Deficiencies microservice used as example).
In order to check this example please follow the steps:
- Make sure that on the port 6379 you have REDIS server running (works for the redis-derver version 2.8.21, for latest verion of the redis (3.0.3) there were some issues). Redis-server can be running on VM.

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
For testing purposes next user/password pairs can be used for logIn: user password, admin password.

Note: UI part during development can be run on separate web server. There are some requirements/limitations with this approach: 
- Deficiencies microservice UI should use port 8084 (can be changed if needed)
- Logout functionality won't properly work for Deficiencies microservice 

	
