package com.swisscom.uamspiketesting.frontend;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class UserFormPage {
   @FindBy(how = How.ID, using = "first-name")
   WebElement firstNameInput;
   @FindBy(how = How.ID, using = "last-name")
   WebElement lastNameInput;
   @FindBy(how = How.ID, using = "email")
   WebElement emailInput;
   @FindBy(how = How.ID, using = "password")
   WebElement passwordInput;
   @FindBy(how = How.ID, using = "submit-user")
   WebElement submitButton;
}
