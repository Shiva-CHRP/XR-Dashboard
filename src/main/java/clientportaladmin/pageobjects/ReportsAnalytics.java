package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ReportsAnalytics extends AbstractComponent {

	public ReportsAnalytics(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[contains(normalize-space(),'Reports')] or .//span[contains(normalize-space(),'Analytics')]]")
	private WebElement reportsNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Reports & Analytics'] | //header//h1[contains(.,'Reports')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(.,'Training Performance')]")
	private WebElement trainingPerformanceTab;

	@FindBy(xpath = "//button[contains(.,'Certifications')]")
	private WebElement certificationsTab;

	@FindBy(xpath = "//button[contains(.,'Leaderboard')]")
	private WebElement leaderboardsTab;

	@FindBy(xpath = "//button[contains(.,'Analytics')]")
	private WebElement analyticsTab;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@StepName("Click Reports & Analytics from Sidebar")
	public void clickReports() {
		waitUtils.waitForClickable(reportsNavButton);
		reportsNavButton.click();
	}

	@StepName("Verify Reports & Analytics Page is Loaded")
	public boolean isReportsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Switch to Training Performance Tab")
	public void switchToTrainingPerformanceTab() {
		waitUtils.waitForClickable(trainingPerformanceTab);
		trainingPerformanceTab.click();
	}

	@StepName("Switch to Certifications Tab")
	public void switchToCertificationsTab() {
		waitUtils.waitForClickable(certificationsTab);
		certificationsTab.click();
	}

	@StepName("Switch to Leaderboards Tab")
	public void switchToLeaderboardsTab() {
		waitUtils.waitForClickable(leaderboardsTab);
		leaderboardsTab.click();
	}

	@StepName("Switch to Analytics Tab")
	public void switchToAnalyticsTab() {
		waitUtils.waitForClickable(analyticsTab);
		analyticsTab.click();
	}

	@StepName("Click Export Button")
	public void clickExport() {
		waitUtils.waitForClickable(exportButton);
		exportButton.click();
	}
}
