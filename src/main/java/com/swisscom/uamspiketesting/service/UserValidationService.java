package com.swisscom.uamspiketesting.service;

import com.swisscom.uamspiketesting.exception.UserAlreadyExistsException;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserValidationService {
   @Autowired
   UserRepository userRepository;

   public ValidationResult validateUser(User user) {
      ValidationResult result = new ValidationResult("user");
      if(userRepository.doesUserWithEmailExist(user.getEmail())) {
         result.addError("email", "User with email already exists.");
      }
      if(user.getEmail().endsWith("@swisscom.com")) {
         result.addError("email", "Email must end with @swisscom.com");
      }
      return result;
   }
}
