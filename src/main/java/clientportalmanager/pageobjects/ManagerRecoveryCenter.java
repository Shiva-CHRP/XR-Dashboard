package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerRecoveryCenter extends AbstractComponent {

	public ManagerRecoveryCenter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Recovery Center']]")
	private WebElement recoveryButton;

	@StepName("Click Manager Recovery Center")
	public void clickManagerRecoveryCenter() {
		recoveryButton.click();
	}
}
