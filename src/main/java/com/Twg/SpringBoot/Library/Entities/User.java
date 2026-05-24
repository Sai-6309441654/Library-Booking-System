package com.Twg.SpringBoot.Library.Entities;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid")
    private Integer id;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="email")
	private String email;
	@Column(name="isactive")
    private boolean isActive=true;
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Role role;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public enum Role
	{
		ROLE_ADMIN,
		ROLE_USER
	}
	public User(Integer id, String username, String password,String email, boolean isActive, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email=email;
		this.isActive = isActive;
		this.role = role;
	}
	public Integer getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public boolean isActive() {
		return isActive;
	}
	public Role getRole() {
		return role;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", isActive=" + isActive
				+ ", role=" + role + "]";
	}
	
}
