package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Synchronization extends AbstractComponent {

	public Synchronization(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Synchronization']]")
	private WebElement synchronizationNavButton;

	@FindBy(xpath = "//h1[contains(.,'Synchronization') or contains(.,'Sync')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Sync Now') or contains(normalize-space(),'Trigger Sync') or contains(.,'Sync')]")
	private WebElement triggerSyncButton;

	@StepName("Click Synchronization from Sidebar")
	public void clickSynchronization() {
		waitUtils.waitForClickable(synchronizationNavButton);
		synchronizationNavButton.click();
	}

	@StepName("Verify Synchronization Page is Loaded")
	public boolean isSynchronizationPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Click Trigger Sync Now")
	public void clickTriggerSync() {
		waitUtils.waitForClickable(triggerSyncButton);
		triggerSyncButton.click();
	}
}
