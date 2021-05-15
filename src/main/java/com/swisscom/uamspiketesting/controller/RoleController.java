package com.swisscom.uamspiketesting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {
   @GetMapping(value = "/roles")
   public String getRoles() {
      return "roles";
   }
}
