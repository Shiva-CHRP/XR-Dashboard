package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class RecoveryCenter extends AbstractComponent {

	public RecoveryCenter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Recovery Center']]")
	private WebElement recoveryCenterButton;

	@StepName("Click Recovery Center")
	public void clickRecoveryCenter() {
		recoveryCenterButton.click();
	}

}
