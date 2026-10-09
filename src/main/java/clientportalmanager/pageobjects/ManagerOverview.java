package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerOverview extends AbstractComponent {

	public ManagerOverview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Overview'] or .//span[normalize-space()='Dashboard']] | //button[.//span[normalize-space()='Overview'] or .//span[normalize-space()='Dashboard']]")
	private WebElement overviewButton;

	@FindBy(xpath = "//h1[contains(.,'Dashboard') or contains(.,'Overview')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'View Events')]")
	private WebElement viewEventsButton;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'cursor-pointer') or contains(@class,'portal-card')]")
	private List<WebElement> kpiCards;

	@FindBy(xpath = "//input[contains(@placeholder,'Search events') or contains(@placeholder,'Search')]")
	private WebElement sessionSearchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> sessionRows;

	@StepName("Click Manager Overview")
	public void clickManagerOverview() {
		navigateToClientRoute(overviewButton, "dashboard");
	}

	@StepName("Verify Manager Overview Page is Loaded")
	public boolean isManagerOverviewPageLoaded() {
		return waitUtils.waitForUrlContains("dashboard", 3)
				|| waitUtils.waitForUrlContains("overview", 3)
				|| !driver.findElements(By.xpath("//aside//button[.//span[normalize-space()='Overview'] or .//span[normalize-space()='Dashboard']]")).isEmpty();
	}

	@StepName("Click View Events Button")
	public void clickViewEvents() {
		waitUtils.waitForClickable(viewEventsButton);
		viewEventsButton.click();
	}

	@StepName("Get KPI Cards Count")
	public int getKPICardsCount() {
		return kpiCards.size();
	}

	@StepName("Search Manager Sessions")
	public void searchSessions(String query) {
		waitUtils.waitForVisibility(sessionSearchInput);
		sessionSearchInput.clear();
		sessionSearchInput.sendKeys(query);
	}

	@StepName("Get Sessions Rows Count")
	public int getSessionsRowsCount() {
		return sessionRows.size();
	}
}
