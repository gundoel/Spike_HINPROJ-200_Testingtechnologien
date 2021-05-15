package com.swisscom.uamspiketesting.api;

import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/users")
@RestController
public class UserRestController {
   @Autowired
   private UserService userService;

   @GetMapping("/hello")
   public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
      return String.format("Hello, %s", name);
   }


   @GetMapping()
   public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String email) {
      if(!StringUtils.isEmpty(email)) {
         return new ResponseEntity<List<User>>(userService.getUsersByEmail(email), HttpStatus.OK);
      } else {
         return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
      }

   }
}
