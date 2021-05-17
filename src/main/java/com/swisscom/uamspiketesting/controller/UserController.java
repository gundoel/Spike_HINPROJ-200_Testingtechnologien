package com.swisscom.uamspiketesting.controller;

import com.swisscom.uamspiketesting.constant.ValidationStatus;
import com.swisscom.uamspiketesting.exception.UserAlreadyExistsException;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.service.UserService;
import com.swisscom.uamspiketesting.service.UserValidationService;
import com.swisscom.uamspiketesting.service.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

   @Autowired
   private UserService userService;

   @Autowired // https://stackoverflow.com/questions/36663048/spring-boot-autowiring-repository-always-null
   private UserValidationService userValidationService;

   @GetMapping(value = "/users")
   public String getUsers(Model model) {
      List<User> users = userService.getUsers();
      model.addAttribute("users", users);
      model.addAttribute("user", new User());
      return "users";
   }

   // TODO return form with errors
   @PostMapping(value = "/users/save")
   @ExceptionHandler(UserAlreadyExistsException.class)
   // @ModelAttribute adds user to the model again
   public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
      ValidationResult validationResult = userValidationService.validateUser(user);
      if(validationResult.getStatus() == ValidationStatus.ERROR) {
         for(ValidationResult.ValidationMessage msg : validationResult.getMessages()) {
            bindingResult.addError(new FieldError(validationResult.getObjectId(), msg.getFieldId(), msg.getMessage()));
         }
      }
      if (bindingResult.hasErrors()) {
         // does not really make sense, since same view
         return "users";
      }
      userService.addUser(user);
      return "redirect:/users";
   }
}
