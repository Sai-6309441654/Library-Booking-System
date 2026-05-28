package com.Twg.SpringBoot.Library.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Twg.SpringBoot.Library.Entities.User;
import com.Twg.SpringBoot.Library.Entities.User.Role;
import com.Twg.SpringBoot.Library.ExceptionHandler.ResourceNotFoundException;
import com.Twg.SpringBoot.Library.Repository.UserRepository;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest 
{
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserServiceImpl userService;
	//Expected User
	private User userSample;
    @BeforeEach
	public void ArrangeUser()
	{
    	userSample=new User();
    	userSample.setId(1);
    	userSample.setUsername("salil");
    	userSample.setPassword("Salil@123");
    	userSample.setEmail("Salil123@gmail.com");
    	userSample.setRole(Role.ROLE_USER);
    	userSample.setActive(true);
	}

	@Test
	@DisplayName("saveUser() - Should successfully save and return the user")
	void saveUser_Success() 
	{
		//Arrange
		when(userRepository.save(any(User.class))).thenReturn(userSample);
		//Actual User
		User userTest=userService.saveUser(new User());
		//Assert
		assertNotNull(userTest);
		assertEquals(userSample.getId(),userTest.getId());
		assertThat(userTest.getUsername()).isEqualTo(userSample.getUsername());
		verify(userRepository,times(1)).save(any(User.class));
	}

	@Test
	@DisplayName("updateUser() - Should successfully save updated user changes")
	void updateUser_Success() 
	{
		// Arrange
        userSample.setUsername("john_updated");
        when(userRepository.save(any(User.class))).thenReturn(userSample);

        // Actual User
        User updatedUser = userService.updateUser(userSample);

        // Assert
        assertThat(updatedUser.getUsername()).isEqualTo(userSample.getUsername());
        verify(userRepository, times(1)).save(userSample);
	}

	@Test
    @DisplayName("deleteUser() - Should invoke repository delete method")
    void deleteUser_Success() 
	{
		// Arrange
        doNothing().when(userRepository).delete(userSample);
        //Act
        userService.deleteUser(userSample);
        //Assert
        verify(userRepository, times(1)).delete(userSample);
    }
	
	@Test
    @DisplayName("findById() - Should return user when user exists in database")
    void findById_Success() 
	{
		// Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(userSample));
        //Act
        User foundUserById = userService.findById(1);
      //Assert
        assertNotNull(foundUserById);
        assertThat(foundUserById.getUsername()).isEqualTo(userSample.getUsername());
    }
	@Test
    @DisplayName("findById() - Should throw ResourceNotFoundException when user does not exist")
    void findById_ThrowsResourceNotFoundException() 
	{
        // Arrange
        when(userRepository.findById(0)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(0);
        });
    }
	@Test
    @DisplayName("findByUsername() - Should find active user matching criteria")
    void findByUsername_Success() {
        // Arrange - Your method maps to findByUsernameAndIsActive(username, true)
        when(userRepository.findByUsernameAndIsActive(userSample.getUsername(), true)).thenReturn(Optional.of(userSample));

        // Act
        User foundUserByUsername= userService.findByUsername(userSample.getUsername());

        // Assert
        assertNotNull(foundUserByUsername);
        assertThat(foundUserByUsername.getId()).isEqualTo(1);
    }@Test
    @DisplayName("findByUsername() - Should throw UsernameNotFoundException when user is inactive or missing")
    void findByUsername_ThrowsUsernameNotFoundException() 
    {
        // Arrange
        when(userRepository.findByUsernameAndIsActive(" ", true)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> 
        {
            userService.findByUsername(" ");
        });
    }
    @Test
    @DisplayName("findAll() - Should return a complete list of users")
    void findAll_Success() 
    {
        when(userRepository.findAll()).thenReturn(Arrays.asList(userSample));

        List<User> result = userService.findAll();

        assertThat(result).hasSize(1);
        verify(userRepository, times(1)).findAll();
    }


}
