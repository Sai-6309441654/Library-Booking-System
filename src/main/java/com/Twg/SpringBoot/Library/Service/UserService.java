package com.Twg.SpringBoot.Library.Service;

import java.util.List;


import com.Twg.SpringBoot.Library.Entities.User;

public interface UserService 
{
	public User saveUser(User user);
	public User updateUser(User user);
    public void deleteUser(User user);
    public User findById(Integer id);
    public User findByUsername(String username);
	public List<User> findAll();
	public void deleteUserById(Integer id);
}
