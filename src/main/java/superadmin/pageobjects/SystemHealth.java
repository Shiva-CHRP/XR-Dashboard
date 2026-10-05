package superadmin.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for Super Admin System Health & Observability:
 * 1. Global Platform Status Banner & Refresh Controls (/super-admin/system-health)
 * 2. Active Incidents & Connection Alerts
 * 3. Platform Services Grid (PostgreSQL, AWS S3, User Service, Identity Service, Client Service, Cloudsync Service, License Service, Notification Service)
 * 4. Traffic & Errors Telemetry (24-hour Gateway Analytics)
 * 5. 30-day Availability, Uptime, Downtime & Incidents
 * 6. API Response Times & Modules Latency
 * 7. Failing & Slowest Routes Analysis
 * 8. Infrastructure Resources, Security Signals & Background Jobs
 * 9. Platform Metrics KPI Cards
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class SystemHealth extends AbstractComponent {

	public SystemHealth(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/system-health']")
	private WebElement systemHealthLink;

	@FindBy(xpath = "//h1[contains(text(),'System Health')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//h1[contains(text(),'System Health')]/following-sibling::span | //h1[contains(text(),'System Health')]/..//span[contains(@class,'rounded-full')][span]")
	private WebElement globalStatusPill;

	@FindBy(xpath = "//h1[contains(text(),'System Health')]/parent::*/following-sibling::p | //h1[contains(text(),'System Health')]/../p[contains(@class,'text-titan-muted')]")
	private WebElement globalStatusDetail;

	@FindBy(xpath = "//span[contains(text(),'Updated')]")
	private WebElement updatedTimeText;

	@FindBy(xpath = "//button[.//span[contains(text(),'Live') or contains(text(),'Paused')] or contains(.,'Live') or contains(.,'Paused')]")
	private WebElement liveToggleButton;

	@FindBy(xpath = "//button[contains(.,'Refresh') and not(contains(.,'Live')) and not(contains(.,'Paused'))]")
	private WebElement refreshButton;

	// Active Incidents Banner
	@FindBy(xpath = "//div[contains(@class,'rounded-xl border') and .//h3[contains(text(),'Active Incident')]]")
	private List<WebElement> activeIncidentContainers;

	@FindBy(xpath = "//h3[contains(text(),'Active Incident')]")
	private WebElement activeIncidentHeading;

	@FindBy(xpath = "//button[contains(.,'Retry Connection')]")
	private WebElement retryConnectionButton;

	// Platform Services Section
	@FindBy(xpath = "//h2[contains(text(),'Platform Services')]")
	private WebElement platformServicesHeading;

	@FindBy(xpath = "//div[contains(@class,'svc-rise')]")
	private List<WebElement> serviceCards;

	// Traffic & Availability Cards
	@FindBy(xpath = "//h2[contains(text(),'Traffic & Errors')]")
	private WebElement trafficErrorsHeading;

	@FindBy(xpath = "//h2[contains(text(),'Traffic & Errors')]/../../span[contains(@class,'tabular-nums')]")
	private WebElement totalRequestsBadge;

	@FindBy(xpath = "//h2[contains(text(),'Availability & Incidents')]")
	private WebElement availabilityIncidentsHeading;

	@FindBy(xpath = "//div[contains(@class,'pointer-events-none')]//span[contains(@class,'tabular-nums')]")
	private WebElement runningComponentsRatio;

	// API Response Times & Modules Latency
	@FindBy(xpath = "//h2[contains(text(),'API Response Times & Modules Latency')]")
	private WebElement apiResponseTimesHeading;

	// Resources & Security
	@FindBy(xpath = "//h2[contains(text(),'Resources, Security & Jobs')]")
	private WebElement resourcesSecurityHeading;

	@FindBy(xpath = "//h2[contains(text(),'Resources, Security & Jobs')]/../../span")
	private WebElement environmentBadge;

	// Platform Metrics KPI Section
	@FindBy(xpath = "//h2[contains(text(),'Platform Metrics')]")
	private WebElement platformMetricsHeading;

	@FindBy(xpath = "//div[contains(@class,'group/kpi')]")
	private List<WebElement> platformMetricCards;


	// =========================================================================
	// 2. ACTION METHODS: Navigation & Lifecycle
	// =========================================================================

	@StepName("Click on System Health in Sidebar")
	public void clickSystemHealth() {
		waitUtils.waitForClickable(systemHealthLink);
		try {
			systemHealthLink.click();
		} catch (Exception e) {
			clickUsingJS(systemHealthLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if System Health Page is Loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Page Title Text")
	public String getPageTitle() {
		waitUtils.waitForVisibility(pageTitle);
		return pageTitle.getText().trim();
	}


	// =========================================================================
	// 3. ACTION METHODS: Status Banner & Controls
	// =========================================================================

	@StepName("Get Global Platform Status Label")
	public String getGlobalStatus() {
		try {
			waitUtils.waitForVisibility(globalStatusPill);
			return globalStatusPill.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Global Status Detail Description")
	public String getGlobalStatusDetail() {
		try {
			waitUtils.waitForVisibility(globalStatusDetail);
			return globalStatusDetail.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Last Updated Time Text")
	public String getLastUpdatedText() {
		try {
			waitUtils.waitForVisibility(updatedTimeText);
			return updatedTimeText.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Toggle Live Auto-Refresh Mode")
	public void toggleLiveMode() {
		waitUtils.waitForClickable(liveToggleButton);
		liveToggleButton.click();
	}

	@StepName("Check if Live Auto-Refresh Mode is Active")
	public boolean isLiveModeActive() {
		try {
			waitUtils.waitForVisibility(liveToggleButton);
			String text = liveToggleButton.getText();
			return text.contains("Live");
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Manual Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		waitForRefreshToComplete();
	}

	@StepName("Wait for Telemetry Refresh to Complete")
	public void waitForRefreshToComplete() {
		try {
			By spinnerLocator = By.xpath("//button[contains(.,'Refreshing') or .//*[contains(@class,'animate-spin')]]");
			waitUtils.waitForInvisibility(spinnerLocator);
		} catch (Exception ignored) {
		}
	}


	// =========================================================================
	// 4. ACTION METHODS: Active Incidents
	// =========================================================================

	@StepName("Check if an Active Incident Banner is Displayed")
	public boolean isIncidentActive() {
		return !activeIncidentContainers.isEmpty() && activeIncidentContainers.get(0).isDisplayed();
	}

	@StepName("Get Active Incident Banner Heading")
	public String getActiveIncidentHeading() {
		if (isIncidentActive()) {
			return activeIncidentHeading.getText().trim();
		}
		return "";
	}

	@StepName("Click Retry Connection on Incident Banner")
	public void clickRetryConnection() {
		if (isElementPresent(retryConnectionButton)) {
			waitUtils.waitForClickable(retryConnectionButton);
			retryConnectionButton.click();
			waitForRefreshToComplete();
		}
	}


	// =========================================================================
	// 5. ACTION METHODS: Platform Services Grid
	// =========================================================================

	@StepName("Get Total Number of Displayed Platform Services")
	public int getPlatformServicesCount() {
		return serviceCards.size();
	}

	@StepName("Get List of All Displayed Service Names")
	public List<String> getAllServiceNames() {
		List<String> names = new ArrayList<>();
		for (WebElement card : serviceCards) {
			try {
				WebElement nameEl = card.findElement(By.xpath(".//h3"));
				names.add(nameEl.getText().trim());
			} catch (Exception ignored) {
			}
		}
		return names;
	}

	private WebElement getServiceCardElement(String serviceName) {
		By locator = By.xpath("//div[contains(@class,'svc-rise') and .//h3[normalize-space()='" + serviceName + "']]");
		return waitUtils.waitForVisibility(locator);
	}

	@StepName("Get Health Status of Service: {0}")
	public String getServiceStatus(String serviceName) {
		try {
			WebElement card = getServiceCardElement(serviceName);
			WebElement badge = card.findElement(By.xpath(".//span[contains(@class,'svc-badge') or contains(@class,'rounded-full')][span or text()]"));
			return badge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Detail Text of Service: {0}")
	public String getServiceDetail(String serviceName) {
		try {
			WebElement card = getServiceCardElement(serviceName);
			WebElement detail = card.findElement(By.xpath(".//p[contains(@class,'text-[11px]')]"));
			return detail.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Uptime/Downtime Value of Service: {0}")
	public String getServiceUptime(String serviceName) {
		try {
			WebElement card = getServiceCardElement(serviceName);
			WebElement uptime = card.findElement(By.xpath(".//div[contains(@class,'text-left')]//span[contains(@class,'tabular-nums')]"));
			return uptime.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Latency Value of Service: {0}")
	public String getServiceLatency(String serviceName) {
		try {
			WebElement card = getServiceCardElement(serviceName);
			WebElement latency = card.findElement(By.xpath(".//div[contains(@class,'text-center')]//span[contains(@class,'tabular-nums')]"));
			return latency.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get 24h Availability Percentage of Service: {0}")
	public String getService24hAvailability(String serviceName) {
		try {
			WebElement card = getServiceCardElement(serviceName);
			WebElement avail = card.findElement(By.xpath(".//div[contains(@class,'text-right')]//span[contains(@class,'tabular-nums')]"));
			return avail.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Check if Service is Operational: {0}")
	public boolean isServiceOperational(String serviceName) {
		String status = getServiceStatus(serviceName);
		return status.equalsIgnoreCase("Operational") || status.equalsIgnoreCase("Running") || status.equalsIgnoreCase("Healthy");
	}


	// =========================================================================
	// 6. ACTION METHODS: Traffic, Availability & Recent Incidents
	// =========================================================================

	@StepName("Get 24-Hour Total Requests Badge Text")
	public String getTraffic24hTotalRequests() {
		try {
			waitUtils.waitForVisibility(totalRequestsBadge);
			return totalRequestsBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Running Components Ratio Text")
	public String getRunningComponentsRatio() {
		try {
			waitUtils.waitForVisibility(runningComponentsRatio);
			return runningComponentsRatio.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Count of Recent Incidents in Last 30 Days")
	public int getRecentIncidentsCount() {
		List<WebElement> incidents = driver.findElements(By.xpath("//h3[text()='Recent incidents']/following-sibling::div//div[contains(@class,'rounded-lg border')]"));
		return incidents.size();
	}


	// =========================================================================
	// 7. ACTION METHODS: API Response Times & Modules Latency
	// =========================================================================

	@StepName("Get API Latency for Endpoint: {0}")
	public String getApiLatency(String apiName) {
		try {
			By locator = By.xpath("//div[contains(@class,'space-y-1.5') and .//span[normalize-space()='" + apiName + "']]//span[contains(@class,'tabular-nums')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get API Status for Endpoint: {0}")
	public String getApiStatus(String apiName) {
		try {
			By locator = By.xpath("//div[contains(@class,'space-y-1.5') and .//span[normalize-space()='" + apiName + "']]//span[contains(@class,'rounded-full')]");
			WebElement el = driver.findElement(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "Normal";
		}
	}


	// =========================================================================
	// 8. ACTION METHODS: Failing & Slowest Routes
	// =========================================================================

	@StepName("Check if Failing Routes are Present")
	public boolean hasFailingRoutes() {
		List<WebElement> headings = driver.findElements(By.xpath("//h3[contains(text(),'What is failing')]"));
		return !headings.isEmpty();
	}

	@StepName("Get Failing Route Failure Percentage: {0}")
	public String getFailingRouteRate(String routePath) {
		try {
			By locator = By.xpath("//h3[contains(text(),'What is failing')]/ancestor::div[contains(@class,'border-t')]//div[.//p[contains(text(),'" + routePath + "')]]//span[contains(text(),'%')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Check if Slowest Routes are Present")
	public boolean hasSlowestRoutes() {
		List<WebElement> headings = driver.findElements(By.xpath("//h3[contains(text(),'What is slow')]"));
		return !headings.isEmpty();
	}

	@StepName("Get Slowest Route Average Duration: {0}")
	public String getSlowestRouteAverageTime(String routePath) {
		try {
			By locator = By.xpath("//h3[contains(text(),'What is slow')]/ancestor::div[contains(@class,'border-t')]//div[.//p[contains(text(),'" + routePath + "')]]//span[contains(text(),'ms') or contains(text(),'s')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}


	// =========================================================================
	// 9. ACTION METHODS: Resources, Security & Background Jobs
	// =========================================================================

	@StepName("Get Deployed Environment Label")
	public String getEnvironment() {
		try {
			waitUtils.waitForVisibility(environmentBadge);
			return environmentBadge.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Resource Metric Value: {0}")
	public String getResourceMetricValue(String label) {
		try {
			By locator = By.xpath("//div[contains(@class,'rounded-lg bg-titan-card') and .//span[normalize-space()='" + label + "']]//p[contains(@class,'text-xl')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Resource Metric Subtitle: {0}")
	public String getResourceMetricSub(String label) {
		try {
			By locator = By.xpath("//div[contains(@class,'rounded-lg bg-titan-card') and .//span[normalize-space()='" + label + "']]//p[contains(@class,'text-[10px]')]");
			WebElement el = driver.findElement(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get DB Connections Metric")
	public String getDbConnections() {
		return getResourceMetricValue("DB connections");
	}

	@StepName("Get Database Size Metric")
	public String getDatabaseSize() {
		return getResourceMetricValue("Database size");
	}

	@StepName("Get S3 Bucket Size Metric")
	public String getS3BucketSize() {
		return getResourceMetricValue("S3 bucket");
	}

	@StepName("Get Failed Logins in Last Hour")
	public String getFailedLoginsLastHour() {
		return getResourceMetricValue("Failed logins (1 h)");
	}

	@StepName("Get Sessions Expiring Soon")
	public String getSessionsExpiringSoon() {
		return getResourceMetricValue("Sessions expiring soon");
	}

	@StepName("Get Status of Background Job: {0}")
	public String getBackgroundJobStatus(String jobName) {
		try {
			By locator = By.xpath("//div[contains(@class,'rounded-lg border') and .//p[normalize-space()='" + jobName + "']]//span[contains(@class,'rounded-full')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Last Run Time of Background Job: {0}")
	public String getBackgroundJobLastRun(String jobName) {
		try {
			By locator = By.xpath("//div[contains(@class,'rounded-lg border') and .//p[normalize-space()='" + jobName + "']]//span[contains(@class,'tabular-nums')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}


	// =========================================================================
	// 10. ACTION METHODS: Platform Metrics KPI Cards
	// =========================================================================

	@StepName("Get Platform Metric Value: {0}")
	public String getPlatformMetricValue(String label) {
		try {
			By locator = By.xpath("//div[contains(@class,'group/kpi') and .//span[normalize-space()='" + label + "']]//p[contains(@class,'text-xl')]");
			WebElement el = waitUtils.waitForVisibility(locator);
			return el.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Click Platform Metric KPI Card: {0}")
	public void clickPlatformMetric(String label) {
		By locator = By.xpath("//div[contains(@class,'group/kpi') and .//span[normalize-space()='" + label + "']]");
		WebElement el = waitUtils.waitForVisibility(locator);
		waitUtils.waitForClickable(el);
		try {
			el.click();
		} catch (Exception e) {
			clickUsingJS(el);
		}
	}

	// Helper to safely check element presence without throwing
	private boolean isElementPresent(WebElement element) {
		try {
			return element != null && element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
