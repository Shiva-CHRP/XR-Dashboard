package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerOverview extends AbstractComponent {

	public ManagerOverview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Overview']]")
	private WebElement overviewButton;

	@StepName("Click Manager Overview")
	public void clickManagerOverview() {
		overviewButton.click();
	}
}
