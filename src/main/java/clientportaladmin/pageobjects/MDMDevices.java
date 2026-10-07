package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class MDMDevices extends AbstractComponent {

	public MDMDevices(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='MDM Devices'] or .//span[normalize-space()='Devices']]")
	private WebElement mdmDevicesNavButton;

	@FindBy(xpath = "//h1[normalize-space()='MDM Devices'] | //header//h1[contains(.,'Device')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Register Device')]")
	private WebElement registerDeviceButton;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[normalize-space()='Devices' or (contains(.,'Devices') and not(contains(.,'Register')))]")
	private WebElement devicesTab;

	@FindBy(xpath = "//button[contains(.,'Pending Requests') or contains(.,'Pending')]")
	private WebElement pendingRequestsTab;

	@FindBy(xpath = "//button[contains(.,'Rejected')]")
	private WebElement rejectedTab;

	@FindBy(xpath = "//*[@role='dialog']//input[@placeholder='Device name' or contains(@placeholder,'Name')]")
	private WebElement deviceNameInput;

	@FindBy(xpath = "//*[@role='dialog']//input[@placeholder='Hardware ID' or contains(@placeholder,'Hardware')]")
	private WebElement hardwareIdInput;

	@FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Register Device' or normalize-space()='Register']")
	private WebElement submitRegisterButton;

	@FindBy(xpath = "//*[@role='dialog']//button[.//svg or @aria-label='Close' or normalize-space()='Cancel']")
	private WebElement closeRegisterModalButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> deviceRows;

	@StepName("Click MDM Devices from Sidebar")
	public void clickMDMDevices() {
		waitUtils.waitForClickable(mdmDevicesNavButton);
		mdmDevicesNavButton.click();
	}

	@StepName("Verify MDM Devices Page is Loaded")
	public boolean isMDMDevicesPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Devices")
	public void searchDevices(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Switch to Devices Tab")
	public void switchToDevicesTab() {
		waitUtils.waitForClickable(devicesTab);
		devicesTab.click();
	}

	@StepName("Switch to Pending Requests Tab")
	public void switchToPendingRequestsTab() {
		waitUtils.waitForClickable(pendingRequestsTab);
		pendingRequestsTab.click();
	}

	@StepName("Switch to Rejected Tab")
	public void switchToRejectedTab() {
		waitUtils.waitForClickable(rejectedTab);
		rejectedTab.click();
	}

	@StepName("Switch to Compliant Tab (Fallback to Devices Tab)")
	public void switchToCompliantTab() {
		List<WebElement> comp = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(.,'Compliant')]"));
		if (!comp.isEmpty()) {
			comp.get(0).click();
		} else {
			switchToDevicesTab();
		}
	}

	@StepName("Switch to Non-Compliant Tab (Fallback to Pending Tab)")
	public void switchToNonCompliantTab() {
		List<WebElement> nonComp = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(.,'Non-Compliant')]"));
		if (!nonComp.isEmpty()) {
			nonComp.get(0).click();
		} else {
			switchToPendingRequestsTab();
		}
	}

	@StepName("Click Register Device")
	public void clickRegisterDevice() {
		waitUtils.waitForClickable(registerDeviceButton);
		registerDeviceButton.click();
	}

	@StepName("Close Register Device Modal")
	public void closeRegisterDeviceModal() {
		waitUtils.waitForClickable(closeRegisterModalButton);
		closeRegisterModalButton.click();
	}

	@StepName("Get Device Rows Count")
	public int getDeviceRowsCount() {
		return deviceRows.size();
	}
}
