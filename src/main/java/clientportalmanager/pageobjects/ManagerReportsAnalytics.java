package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerReportsAnalytics extends AbstractComponent {

	public ManagerReportsAnalytics(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Reports & Analytics'] or .//span[normalize-space()='Reports']]")
	private WebElement reportsButton;

	@FindBy(xpath = "//h1[contains(.,'Reports & Analytics') or contains(.,'Reports')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[normalize-space()='Training Performance']")
	private WebElement trainingPerformanceTab;

	@FindBy(xpath = "//button[normalize-space()='Certifications']")
	private WebElement certificationsTab;

	@FindBy(xpath = "//button[normalize-space()='Leaderboards']")
	private WebElement leaderboardsTab;

	@FindBy(xpath = "//button[normalize-space()='Analytics']")
	private WebElement analyticsTab;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@StepName("Click Manager Reports & Analytics")
	public void clickManagerReports() {
		waitUtils.waitForClickable(reportsButton);
		reportsButton.click();
	}

	@StepName("Verify Manager Reports & Analytics Page is Loaded")
	public boolean isManagerReportsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Switch to Training Performance Tab")
	public void clickTrainingPerformanceTab() {
		waitUtils.waitForClickable(trainingPerformanceTab);
		trainingPerformanceTab.click();
	}

	@StepName("Switch to Certifications Tab")
	public void clickCertificationsTab() {
		waitUtils.waitForClickable(certificationsTab);
		certificationsTab.click();
	}

	@StepName("Switch to Leaderboards Tab")
	public void clickLeaderboardsTab() {
		waitUtils.waitForClickable(leaderboardsTab);
		leaderboardsTab.click();
	}

	@StepName("Switch to Analytics Tab")
	public void clickAnalyticsTab() {
		waitUtils.waitForClickable(analyticsTab);
		analyticsTab.click();
	}
}
