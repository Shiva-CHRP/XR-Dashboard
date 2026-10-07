package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Settings extends AbstractComponent {

	public Settings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Settings']]")
	private WebElement settingsNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Settings'] | //header//h1[contains(.,'Settings')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//nav//button[contains(.,'Portal Customization')] | (//button[contains(.,'Portal Customization')])[last()]")
	private WebElement portalCustomizationTab;

	@FindBy(xpath = "//nav//button[contains(.,'Advanced Settings')] | (//button[contains(.,'Advanced Settings')])[last()]")
	private WebElement advancedSettingsTab;

	@FindBy(xpath = "//nav//button[contains(.,'Sync Sharing')] | (//button[contains(.,'Sync Sharing')])[last()]")
	private WebElement syncSharingTab;

	@FindBy(xpath = "//nav//button[contains(.,'Account')] | (//button[contains(.,'Account')])[last()]")
	private WebElement accountTab;

	@FindBy(xpath = "//nav//button[contains(.,'Notifications')] | (//button[contains(.,'Notifications')])[last()]")
	private WebElement notificationsTab;

	@FindBy(xpath = "//nav//button[contains(.,'Security')] | (//button[contains(.,'Security')])[last()]")
	private WebElement securityTab;

	@StepName("Click Settings from Sidebar")
	public void clickSettings() {
		waitUtils.waitForClickable(settingsNavButton);
		settingsNavButton.click();
	}

	@StepName("Verify Settings Page is Loaded")
	public boolean isSettingsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Switch to Portal Customization Tab")
	public void switchToPortalCustomizationTab() {
		waitUtils.waitForClickable(portalCustomizationTab);
		waitUtils.clickUsingJS(portalCustomizationTab);
	}

	@StepName("Switch to Advanced Settings Tab")
	public void switchToAdvancedSettingsTab() {
		waitUtils.waitForClickable(advancedSettingsTab);
		waitUtils.clickUsingJS(advancedSettingsTab);
	}

	@StepName("Switch to Sync Sharing Tab")
	public void switchToSyncSharingTab() {
		waitUtils.waitForClickable(syncSharingTab);
		waitUtils.clickUsingJS(syncSharingTab);
	}

	@StepName("Switch to Account Tab")
	public void switchToAccountTab() {
		waitUtils.waitForClickable(accountTab);
		waitUtils.clickUsingJS(accountTab);
	}

	@StepName("Switch to Notifications Tab")
	public void switchToNotificationsTab() {
		waitUtils.waitForClickable(notificationsTab);
		waitUtils.clickUsingJS(notificationsTab);
	}

	@StepName("Switch to Security Tab")
	public void switchToSecurityTab() {
		waitUtils.waitForClickable(securityTab);
		waitUtils.clickUsingJS(securityTab);
	}
}
