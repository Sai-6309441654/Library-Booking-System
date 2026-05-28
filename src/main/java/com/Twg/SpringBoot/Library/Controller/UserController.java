package com.Twg.SpringBoot.Library.Controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Twg.SpringBoot.Library.Entities.User;
import com.Twg.SpringBoot.Library.JotModel.JotService;
import com.Twg.SpringBoot.Library.JotModel.JotUser;
import com.Twg.SpringBoot.Library.Service.UserService;

import io.jsonwebtoken.Claims;


@RestController
@RequestMapping("/auth")
public class UserController 
{
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JotService jotService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	//Add User
	@PostMapping("/")
	public ResponseEntity<User> register(@RequestBody User user)
	{
		User saveuser=new User();
		saveuser.setUsername(user.getUsername());
		saveuser.setEmail(user.getEmail());
		saveuser.setPassword(passwordEncoder.encode(user.getPassword()));
		saveuser.setActive(user.isActive());
		saveuser.setRole(user.getRole());
		User savedUser=userService.saveUser(saveuser);
		if(savedUser==null)
		{
			throw new NullPointerException();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(saveuser);
	}
	@PostMapping("/login")
	public String jwtGen(@RequestBody JotUser jotUser)
	{
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jotUser.getUsername(),jotUser.getPassword()));
		if(authentication.isAuthenticated())
		{
			String role=authentication
			.getAuthorities()
			.iterator()
			.next()
			.getAuthority()
			.replace("ROLE_","");//prefix "ROLE_" has to be added when configuring roles in spring security,but don't add it when passing it to JWT
			String username=jotUser.getUsername();
			String token=jotService.generateJwt(username, role);
			return token;
		}
		return null;
	}
	//view user by id
	@GetMapping("/{id}")
    public ResponseEntity<User> GetUser(@PathVariable Integer id)
    {
		User user=userService.findById(id);
    	return ResponseEntity.status(HttpStatus.OK).body(user);
    }
	//view user by username
	@GetMapping("/username/{username}")
    public ResponseEntity<User> GetUser(@PathVariable String username)
    {
		User user=userService.findByUsername(username);
    	return ResponseEntity.status(HttpStatus.OK).body(user);
    }
	//view all users
	@GetMapping("/")
	public ResponseEntity<List<User>> GetUsers()
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
	//Update user by id
	@PutMapping("/{id}")
	public ResponseEntity<User> UpdateuserByuserId(@PathVariable Integer id,@RequestBody User user)
	{
		User foundUser=userService.findById(id);
		foundUser.setUsername(user.getUsername());
		foundUser.setEmail(user.getEmail());
		foundUser.setPassword(user.getPassword());
		User UpdateFoundUser =userService.updateUser(foundUser);
		return ResponseEntity.status(HttpStatus.OK).body(UpdateFoundUser);
	}
	//Delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id)
	{
		User user=userService.findById(id);
		userService.deleteUserById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(user.getUsername()+"is deleted");
	}
	@PostMapping("/validate")
    public ResponseEntity<String> jwtval(@RequestParam String Token)
    {
    	Claims claims=jotService.verifySignatureAndExtractAllClaims(Token);
    	return  ResponseEntity.status(HttpStatus.ACCEPTED).body(claims.getSubject());
    }
}
