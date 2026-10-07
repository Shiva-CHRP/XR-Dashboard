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

	@FindBy(xpath = "//button[contains(.,'Portal Customization')]")
	private WebElement portalCustomizationTab;

	@FindBy(xpath = "//button[contains(.,'Advanced Settings')]")
	private WebElement advancedSettingsTab;

	@FindBy(xpath = "//button[contains(.,'Account')]")
	private WebElement accountTab;

	@FindBy(xpath = "//button[contains(.,'Notifications')]")
	private WebElement notificationsTab;

	@FindBy(xpath = "//button[contains(.,'Security')]")
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
		portalCustomizationTab.click();
	}

	@StepName("Switch to Advanced Settings Tab")
	public void switchToAdvancedSettingsTab() {
		waitUtils.waitForClickable(advancedSettingsTab);
		advancedSettingsTab.click();
	}

	@StepName("Switch to Account Tab")
	public void switchToAccountTab() {
		waitUtils.waitForClickable(accountTab);
		accountTab.click();
	}

	@StepName("Switch to Notifications Tab")
	public void switchToNotificationsTab() {
		waitUtils.waitForClickable(notificationsTab);
		notificationsTab.click();
	}

	@StepName("Switch to Security Tab")
	public void switchToSecurityTab() {
		waitUtils.waitForClickable(securityTab);
		securityTab.click();
	}
}
