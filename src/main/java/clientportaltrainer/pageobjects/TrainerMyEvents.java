package clientportaltrainer.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class TrainerMyEvents extends AbstractComponent {

	public TrainerMyEvents(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='My Events']]")
	private WebElement eventsButton;

	@StepName("Click Trainer My Events")
	public void clickTrainerMyEvents() {
		eventsButton.click();
	}
}
