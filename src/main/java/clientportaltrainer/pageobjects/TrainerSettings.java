package clientportaltrainer.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class TrainerSettings extends AbstractComponent {

	public TrainerSettings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Settings']]")
	private WebElement settingsButton;

	@StepName("Click Trainer Settings")
	public void clickTrainerSettings() {
		settingsButton.click();
	}

}
