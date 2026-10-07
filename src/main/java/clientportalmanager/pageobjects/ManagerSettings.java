package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerSettings extends AbstractComponent {

	public ManagerSettings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Settings']]")
	private WebElement settingsButton;

	@FindBy(xpath = "//h1[contains(.,'Settings')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[normalize-space()='Account'] | //nav//button[contains(.,'Account')]")
	private WebElement accountTab;

	@FindBy(xpath = "//button[normalize-space()='Notifications'] | //nav//button[contains(.,'Notifications')]")
	private WebElement notificationsTab;

	@FindBy(xpath = "//button[normalize-space()='Security'] | //nav//button[contains(.,'Security')]")
	private WebElement securityTab;

	@StepName("Click Manager Settings")
	public void clickManagerSettings() {
		waitUtils.waitForClickable(settingsButton);
		settingsButton.click();
	}

	@StepName("Verify Manager Settings Page is Loaded")
	public boolean isManagerSettingsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Switch to Account Tab")
	public void clickAccountTab() {
		waitUtils.waitForClickable(accountTab);
		accountTab.click();
	}

	@StepName("Switch to Notifications Tab")
	public void clickNotificationsTab() {
		waitUtils.waitForClickable(notificationsTab);
		notificationsTab.click();
	}

	@StepName("Switch to Security Tab")
	public void clickSecurityTab() {
		waitUtils.waitForClickable(securityTab);
		securityTab.click();
	}
}
