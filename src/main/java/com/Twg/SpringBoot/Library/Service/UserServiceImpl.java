package com.Twg.SpringBoot.Library.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Twg.SpringBoot.Library.Entities.User;
import com.Twg.SpringBoot.Library.ExceptionHandler.ResourceNotFoundException;
import com.Twg.SpringBoot.Library.Repository.UserRepository;
@Service
public class UserServiceImpl implements UserService,UserDetailsService
{

	@Autowired
    private UserRepository userRepository;
    
    public UserRepository getUserRepository() 
    {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) 
	{
		this.userRepository = userRepository;
	}
	public UserServiceImpl() 
	{
		
	}
	//Creating the user or Inserting the user
		@Override
		public User saveUser(User user) 
		{
			return userRepository.save(user);
		}
	    //updating the user
		@Override
		public User updateUser(User user) 
		{
			return userRepository.save(user);
		}
	    //deleting the user
		@Override
		public void deleteUser(User user) 
		{
			userRepository.delete(user);
		}
	    //finding the user by using userid and using optional class 
		@Override
		public User findById(Integer id) 
		{
			return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user with id:"+id+" is not found"));
		}
		//finding the user by using username
		@Override
		public User findByUsername(String username) 
		{
			return userRepository.findByUsernameAndIsActive(username,true).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		}
	    //finding all users
		@Override
		public List<User> findAll() 
		{
			return userRepository.findAll();
		}
		//deleting the user By Particular Id
		public void deleteUserById(Integer id)
		{
			 userRepository.deleteById(id);
		}
        
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
		{
			User user=findByUsername(username);
			return org.springframework.security.core.userdetails.User
					.builder()
					.username(user.getUsername())
					.password(user.getPassword())
					.authorities(new SimpleGrantedAuthority(user.getRole().name()))
					.build();
		}
	

}
