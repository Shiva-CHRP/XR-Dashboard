package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DeveloperDashboard extends AbstractComponent{

	public DeveloperDashboard(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/developer']")
	private WebElement developer;
	
	@StepName("Click on the Developer Dashboard")
	public void clickDeveloperDashboard() {
		developer.click();
	}

}
