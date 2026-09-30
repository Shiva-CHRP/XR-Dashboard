package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DeveloperProfile extends AbstractComponent{

	public DeveloperProfile(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/developer/profile']")
	private WebElement profile;

	@StepName("Click on the Developer Profile")
	public void clickDeveloperProfile() {
		profile.click();
	}
}
