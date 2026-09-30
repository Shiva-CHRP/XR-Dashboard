package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class MyEvents extends AbstractComponent {

	public MyEvents(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='My Events']]")
	private WebElement myEventsButton;

	@StepName("Click Manager My Events")
	public void clickManagerMyEvents() {
		myEventsButton.click();
	}
}
