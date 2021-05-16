package com.swisscom.uamspiketesting.frontend;

import com.swisscom.uamspiketesting.UamSpikeTestingApplication;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

   @Test
   void shouldSendUserForm(ChromeDriver chromeDriver) throws InterruptedException {
      String testEmail = "hans.muster@frontendtest.com";
      chromeDriver.get(baseUrl + portOption1 + "/users");
      UserFormPage page = PageFactory.initElements(chromeDriver, UserFormPage.class);
      page.firstNameInput.sendKeys("Hans");
      page.lastNameInput.sendKeys("Muster");
      page.emailInput.sendKeys(testEmail);
      page.passwordInput.sendKeys("1234");
      page.submitButton.click();
      // check if user is registered
      chromeDriver.get(baseUrl + portOption1 + "/users");
      List<WebElement> users = chromeDriver.findElementsByTagName("li");
      List<WebElement> usersFiltered = users.stream().filter(listItem -> listItem.getText().contains(testEmail)).collect(Collectors.toList());
      System.out.println(usersFiltered.get(0).getText());
      assertThat(usersFiltered.size()).isGreaterThan(0);
   }
}
