package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerAssignModules extends AbstractComponent {

	public ManagerAssignModules(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Modules']]")
	private WebElement assignModulesButton;

	@StepName("Click Manager Assign Modules")
	public void clickManagerAssignModules() {
		assignModulesButton.click();
	}

}
