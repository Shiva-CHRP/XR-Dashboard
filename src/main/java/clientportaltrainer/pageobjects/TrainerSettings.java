package clientportaltrainer.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class TrainerSettings extends AbstractComponent {

	public TrainerSettings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Settings']] | //button[.//span[normalize-space()='Settings']]")
	private WebElement settingsButton;

	@FindBy(xpath = "//h1[contains(.,'Settings')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[normalize-space()='Offline Enrollment'] | //nav//button[contains(.,'Offline Enrollment')]")
	private WebElement offlineEnrollmentTab;

	@FindBy(xpath = "//button[normalize-space()='Account'] | //nav//button[contains(.,'Account')]")
	private WebElement accountTab;

	@FindBy(xpath = "//button[normalize-space()='Notifications'] | //nav//button[contains(.,'Notifications')]")
	private WebElement notificationsTab;

	@FindBy(xpath = "//button[normalize-space()='Security'] | //nav//button[contains(.,'Security')]")
	private WebElement securityTab;

	@StepName("Click Trainer Settings")
	public void clickTrainerSettings() {
		navigateToClientRoute(settingsButton, "settings");
	}

	@StepName("Verify Trainer Settings Page is Loaded")
	public boolean isTrainerSettingsPageLoaded() {
		return waitUtils.waitForUrlContains("settings", 2)
				|| !driver.findElements(By.xpath("//h1[contains(.,'Settings')] | //header//h1")).isEmpty();
	}

	@StepName("Switch to Offline Enrollment Tab")
	public void clickOfflineEnrollmentTab() {
		waitUtils.waitForClickable(offlineEnrollmentTab);
		offlineEnrollmentTab.click();
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

	@StepName("Save Account Settings with Toast Verification")
	public ToastResponse saveAccountSettings() {
		clickAccountTab();
		By saveBtnBy = By.xpath("//button[contains(normalize-space(),'Save Changes') or contains(normalize-space(),'Save')]");
		WebElement saveBtn = waitUtils.waitForVisibility(saveBtnBy);
		waitUtils.waitForClickable(saveBtn);
		try {
			saveBtn.click();
		} catch (Exception e) {
			clickUsingJS(saveBtn);
		}
		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}
}