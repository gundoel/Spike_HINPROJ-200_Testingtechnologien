package com.swisscom.uamspiketesting.frontend;

import com.swisscom.uamspiketesting.UamSpikeTestingApplication;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
@SpringBootTest(classes = UamSpikeTestingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserFormTest {
   @Autowired
   private ServerProperties serverProperties;
   @Value("${local.server.port}")
   private int portOption1;
   @LocalServerPort
   private int portOption2;
   String baseUrl = "http://localhost:";
   private String TEST_EMAIL = "hans.muster@frontendtest.com";
   private String TEST_FIRST_NAME = "Hans";
   private String TEST_LAST_NAME = "Muster";
   private String TEST_PASSWORD = "1234";
   private String URI = "/users";

   @BeforeEach
   void setUp() {

   }

   @Test
   void shouldSendUserForm(ChromeDriver chromeDriver) throws InterruptedException {
      chromeDriver.get(baseUrl + portOption1 + URI);
      UserFormPage page = PageFactory.initElements(chromeDriver, UserFormPage.class);
      page.firstNameInput.sendKeys(TEST_FIRST_NAME);
      page.lastNameInput.sendKeys(TEST_LAST_NAME);
      page.emailInput.sendKeys(TEST_EMAIL);
      page.passwordInput.sendKeys(TEST_PASSWORD);
      page.submitButton.click();
      // check if user is registered
      chromeDriver.get(baseUrl + portOption1 + URI);
      List<WebElement> users = chromeDriver.findElementsByTagName("li");
      List<WebElement> usersFiltered = users.stream().filter(listItem -> listItem.getText().contains(TEST_EMAIL)).collect(Collectors.toList());
      System.out.println(usersFiltered.get(0).getText());
      assertThat(usersFiltered.size()).isGreaterThan(0);
   }

   @Test
   void shouldShowFirstNameCantBeEmptyError(ChromeDriver chromeDriver) {
      chromeDriver.get(baseUrl + portOption1 + URI);
      UserFormPage page = PageFactory.initElements(chromeDriver, UserFormPage.class);
      //page.firstNameInput.sendKeys(TEST_FIRST_NAME);
      page.lastNameInput.sendKeys(TEST_LAST_NAME);
      page.emailInput.sendKeys(TEST_EMAIL);
      page.passwordInput.sendKeys(TEST_PASSWORD);
      page.submitButton.click();
      // check input for .is-invalid class
      WebElement firstNameInput = chromeDriver.findElementById("first-name");
      assertTrue(hasClass(firstNameInput,"is-invalid"));
      // check error message
      WebElement firstNameError = chromeDriver.findElementByCssSelector("#first-name + div");
      // in production -> get localized error message from resource file
      assertThat(firstNameError.getText().equals("darf nicht leer sein")).isTrue();
      // maybe better for string comparison, since error message contains compared strings
      assertEquals(firstNameError.getText(),"darf nicht leer sein");
   }

   private boolean hasClass(WebElement element, String className) {
      String classes = element.getAttribute("class");
      long matchingClassCount = Arrays.stream(classes.split(" ")).filter(clName -> clName.equals(className)).count();
      return (matchingClassCount >= 1);
   }
}
