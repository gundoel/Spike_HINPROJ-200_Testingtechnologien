package com.swisscom.uamspiketesting.controller;

import com.swisscom.uamspiketesting.service.UserService;
import com.swisscom.uamspiketesting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

   @Autowired
   private UserService userService;

   @GetMapping(value = "/users")
   public String getUsers(Model model) {
      List<User> users = userService.getUsers();
      model.addAttribute("users", users);
      model.addAttribute("user", new User());
      return "users";
   }

   // TODO return form with errors
   @PostMapping(value = "/users/save")
   public String saveUser(@Valid User user, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return "redirect:/users";
      }
      userService.addUser(user);
      return "redirect:/users";
   }
}
