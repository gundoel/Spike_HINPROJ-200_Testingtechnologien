package com.swisscom.uamspiketesting.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

// simple unit test
class UserRestControllerTest {

   @Test
   // Dummy test
   void hello() {
      UserRestController controller = new UserRestController();
      String response = controller.hello("World");
      assertEquals("Hello, World", response);
   }
}