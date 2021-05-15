package com.swisscom.uamspiketesting.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;

import static org.junit.jupiter.api.Assertions.*;

// check html template mappings of controller endpoints
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

   /*@Disabled
   @Test
   void getUsers() {
      UserController controller = new UserController();
      assertEquals("users", controller.getUsers(new ExtendedModelMap()));
   }*/
}