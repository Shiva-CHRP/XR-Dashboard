package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerReportsAnalytics extends AbstractComponent {

	public ManagerReportsAnalytics(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Reports & Analytics']]")
	private WebElement reportsButton;

	@StepName("Click Manager Reports & Analytics")
	public void clickManagerReportsAnalytics() {
		reportsButton.click();
	}

}
