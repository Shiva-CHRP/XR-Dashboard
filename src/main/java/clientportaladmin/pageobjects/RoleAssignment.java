package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class RoleAssignment extends AbstractComponent {

	public RoleAssignment(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Role Assignment']]")
	private WebElement roleAssignmentButton;

	@StepName("Click Role Assignment")
	public void clickRoleAssignment() {
		roleAssignmentButton.click();
	}

}
