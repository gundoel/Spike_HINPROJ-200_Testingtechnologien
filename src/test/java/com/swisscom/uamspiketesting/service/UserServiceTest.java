package com.swisscom.uamspiketesting.service;

import com.swisscom.uamspiketesting.exception.UserAlreadyExistsException;
import com.swisscom.uamspiketesting.model.Role;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

   // Use mockito to mock userRepository, since UserRepository has already been tested in UserRepositoryTest
   @Mock
   private UserRepository userRepository;
   // private AutoCloseable autoCloseable; -> not needed with @ExtendedWith(MockitoExtension.class)
   private UserService userService;

   @BeforeEach
   void setUp() {
      // initialize all mocks in this test class
      //autoCloseable = MockitoAnnotations.openMocks(this);  -> not needed with @ExtendedWith(MockitoExtension.class)
      userService = new UserService(userRepository);
   }

   @AfterEach
   void tearDown() throws Exception {
      // close mocked repository
      // autoCloseable.close();  -> not needed with @ExtendedWith(MockitoExtension.class)
   }

   @Test
   void canGetUsers() {
      userService.getUsers();
      // verify getUsers was invoked by userRepository.findAll
      verify(userRepository).findAll();
      // would fail
      //verify(userRepository).deleteAll();
   }

   @Test
   void canAddUser() throws Exception {
      HashSet<User> usersDummy = new HashSet<>();
      HashSet<Role> roles = new HashSet<>();
      roles.add(new Role(1, "admin", usersDummy));
      User user = new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles);
      userService.addUser(user);
      // verify getUsers was invoked with user we pass
      ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
      // verify that userRepository was invoked with save method
      // and capture argument at same time
      verify(userRepository).save(userArgumentCaptor.capture());
      User capturedUser = userArgumentCaptor.getValue();
      // assert that passed user is same as the one passed to userRepository
      assertThat(capturedUser).isEqualTo(user);
   }

   @Test
   @Disabled
      // test fails since UserValidationService was implemented instead of exception
      // test remains in code as sample
   void willThrowWhenUserWithEmailExists() {
      HashSet<User> usersDummy = new HashSet<>();
      HashSet<Role> roles = new HashSet<>();
      roles.add(new Role(1, "admin", usersDummy));
      User user = new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles);
      // mock return value of repository method (which would return false, since user does not exist)
      // instead of email we could pass anyString()
      given(userRepository.doesUserWithEmailExist(user.getEmail())).willReturn(true);
      assertThatThrownBy(() -> userService.addUser(user))
              .isInstanceOf(UserAlreadyExistsException.class)
              .hasMessageContainingAll("User with email hans.muster@test.com already exists.");
      // assert that save method is never called
      verify(userRepository, never()).save(any());
   }

   @Test
   void canGetUsersByEmail() {
      HashSet<User> usersDummy = new HashSet<>();
      HashSet<Role> roles = new HashSet<>();
      roles.add(new Role(1, "admin", usersDummy));
      User user = new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles);
      List<User> users = new ArrayList<>();
      users.add(user);
      // assert that every user in list has given email address
      Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(users);
      assertThat(userService.getUsersByEmail(user.getEmail())).isEqualTo(users);
   }

   @Test
   void getUserById() {
   }
}