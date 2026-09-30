package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerContentHub extends AbstractComponent{

	public ManagerContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[.//span[normalize-space()='Content Hub']]")
	private WebElement contentHubButton;

	@StepName("Click Manager Content Hub")
	public void clickManagerContentHub() {
		contentHubButton.click();
	}

}
