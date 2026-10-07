package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DesignateTrainer extends AbstractComponent {

	public DesignateTrainer(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Designate Trainer']]")
	private WebElement designateTrainerNavButton;

	@FindBy(xpath = "//h1[contains(.,'Designate Trainer')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Designate')]")
	private WebElement saveButton;

	@StepName("Click Designate Trainer from Sidebar")
	public void clickDesignateTrainer() {
		waitUtils.waitForClickable(designateTrainerNavButton);
		designateTrainerNavButton.click();
	}

	@StepName("Verify Designate Trainer Page is Loaded")
	public boolean isDesignateTrainerPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}
}
