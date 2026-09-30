package clientportaltrainer.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class TrainerOverview extends AbstractComponent {

	public TrainerOverview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Overview']]")
	private WebElement overviewButton;

	@StepName("Click Trainer Overview")
	public void clickTrainerOverview() {
		overviewButton.click();
	}

}
