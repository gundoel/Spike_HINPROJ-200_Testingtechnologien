package com.swisscom.uamspiketesting.service;

import com.swisscom.uamspiketesting.exception.UserNotFoundException;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

   @Autowired
   private UserRepository userRepository;

   public List<User> getUsers() {
      ArrayList<User> userList = (ArrayList<User>) userRepository.findAll();
      return userList;
   }

   public void addUser(User user) {
      userRepository.save(user);
   }

   public List<User> getUsersByEmail(String email) {
      List<User> users = userRepository.findByEmail(email);
      if(users.size() < 1) {
         throw new UserNotFoundException("No user with " + email + " found");
      }
      return users;
   }

   public User getUserById(Integer id) {
      Optional<User> user = userRepository.findById(id);
      if(!user.isPresent()) {
         throw new UserNotFoundException("User with ID" + id + " not found");
      }
      return user.get();
   }
}
