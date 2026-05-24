package com.Twg.SpringBoot.Library.JotModel;

public class JotUser 
{
    private String username;
    private String password;
    public JotUser() 
	{
		super();
	}
	public JotUser(String username, String password) 
	{
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() 
	{
		return "JotUser [username=" + username + ", password=" + password + "]";
	}
	
	
	
    
    

}
