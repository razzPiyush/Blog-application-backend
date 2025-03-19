package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;

/*
Project structure-

1) controller layer - handles https requests and responses to that http requests
2) service layer - controls the buisness logic (request from controller layer goes to service layer)
3) repository layer - handles database interactions
4) entity layer - maps java objects to tables
5) security layer - manges authentication and authorization
6) exceptional layer - handles application-specific and gloabl errors
7) utlity layer - provides ultility funcionalities, eg, JWT generation
8) Payloads - A term often used for request/response objects.
 */


@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
// CommandLineRunner interface allows the execution of custom code, after the spring boot application starts
	
	// all this inside this will run just after start of application.                              
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	// Dependency injection (injecting object without making using keyword new)
	
	@Autowired
	private RoleRepo roleRepo;
	// handles the database operations using role entity, using spring data JPA
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	// this class is used to convert object of one class into another class.
	@Bean
	public ModelMapper modelMapper() 
	{
		return new ModelMapper();
	}
	// ModelMapper is a library used for object mapping
	// ex- can covert of object of one type(DTOs) to another(entity)

	@Override
	public void run(String... args) throws Exception {
		// whatever password user enters, that got encoded
		System.out.println(this.passwordEncoder.encode("abc"));
		
		try {
			// role initialization - (admin role, normal role)
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			// save the role to the database
			result.forEach(r -> {
				System.out.println(r.getName());
			});
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}

/*
2. Workflow

2.1 User Authentication and Authorization
Login (User Authentication)

[Main -> AuthController -> CustomUserDetailsService -> JWTTokenHelper -> JWTAuthResponse]

- A user sends a POST request to the /auth/login endpoint in the AuthController with username and password.
- The AuthController uses CustomUserDetailsService to load the user from the database.
- The PasswordEncoder (e.g., BCrypt) validates the password.
- If authentication is successful, the JwtTokenHelper generates a JWT token.
- The token is returned in the JwtAuthResponse.
- Request Validation (Authorization)

[Any CRUD operations -> JwtAuthenticationFilter -> JWTTokenHelper -> valid request -> controller]

- Every subsequent user request (e.g., to create a post) includes the JWT token in the Authorization header.
- The JwtAuthenticationFilter intercepts the request and validates the JWT using JwtTokenHelper.
- If the token is valid, the request proceeds to the controller. Otherwise, the JwtAuthenticationEntryPoint returns an error response.

2.2 CRUD Operations Workflow
Example: Creating a Blog Post

[PostController -> PostService -> PostRepo -> Post entity (table) -> returns a success response (PostResponse DTO)]

- Client Sends Request
- The user sends a POST request to /posts with post data in the body and a JWT token in the header.
- Controller Processes Request
- The PostController receives the request and extracts the post data.
- It calls the PostService to handle the business logic.
- Business Logic Execution
- The PostService calls PostRepo to save the post in the database.
- The Post entity is mapped to the database table.
- Response to Client
- After the post is successfully saved, the PostController returns a success response (PostResponse DTO) to the client.

2.3 Exception Handling
Global Exception Handling

- If a resource (e.g., post) is not found, a ResourceNotFoundException is thrown.
- The GlobalExceptionHandler catches the exception and returns an appropriate error response.
- Custom API Exceptions
- ApiException can be used to define custom error scenarios, such as invalid input or forbidden actions.
 */



/*
3. Detailed Workflow: Authentication

3.1 Generating JWT Tokens

The JwtTokenHelper generates a token in the following steps:
- Define claims (issuer, subject, expiration, etc.).
- Sign the token using the HS512 algorithm and a secret key.
- Compact the token into a URL-safe string.

3.2 Validating JWT Tokens

The JwtAuthenticationFilter validates tokens in the following steps:
- Extract the token from the Authorization header.
- Parse the token and check if:
  1) The token has expired.
  2) The username in the token matches the authenticated user.
- If valid, the request is forwarded to the respective controller.
 */



/*
4. Key Components

4.1 Controller Layer
Handles HTTP requests and delegates tasks to the service layer.

Endpoints:
- AuthController: Login and token generation.
- PostController: CRUD operations for posts.
- CategoryController: Manage categories.
- CommentController: Add/view comments.
- UserController: User-related actions (e.g., profile updates).

4.2 Service Layer
Contains business logic and acts as a bridge between the controllers and repositories.

Services:
- PostService: Handles post-related logic.
- UserService: Handles user-related logic.
- CategoryService: Manages categories.
- CommentService: Handles comments.
- FileService: Deals with file uploads.

4.3 Repository Layer
Directly interacts with the database using Spring Data JPA.

- Each repository interface (e.g., PostRepo, UserRepo) extends JpaRepository, providing built-in methods for
  CRUD operations.

4.4 Security Layer
Manages application security.

Authentication:
- CustomUserDetailsService: Loads user data from the database for Spring Security.

Authorization:
- JwtAuthenticationFilter: Validates JWT tokens.
- SecurityConfig: Configures access rules for endpoints.

Token Management:
- JwtTokenHelper: Generates and validates JWT tokens.

4.5 Entity Layer
Defines database tables using JPA annotations.

Entities:
- Post: Represents blog posts.
- Comment: Represents comments on posts.
- Category: Represents post categories.
- User: Represents users of the application.
- Role: Represents user roles (e.g., Admin, Normal User).

4.6 Exception Layer
Handles application-specific and global exceptions.

- GlobalExceptionHandler: Catches exceptions and returns standardized error responses.
- ResourceNotFoundException: Thrown when a requested resource is not found.

 */


/*
DTOs - transfers data between layers of application (from controller to service), for APIs interaction
Entity - Maps to Database Tables using JPA/ Hibernate annotations
Security - Entities might contain sensitive fields (e.g., passwords). DTOs allow you to avoid exposing them.
 */