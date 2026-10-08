package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Overview extends AbstractComponent {

	public Overview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Overview'] or .//span[normalize-space()='Dashboard']] | //button[.//span[normalize-space()='Overview'] or .//span[normalize-space()='Dashboard']]")
	private WebElement overviewNavButton;

	@FindBy(xpath = "//h1[contains(.,'Overview') or contains(.,'Dashboard')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'card') or contains(@class,'portal-card')]")
	private java.util.List<WebElement> kpiCards;

	@StepName("Click Overview from Sidebar")
	public void clickOverview() {
		navigateToClientRoute(overviewNavButton, "dashboard");
	}

	@StepName("Verify Overview Page is Loaded")
	public boolean isOverviewLoaded() {
		return waitUtils.waitForUrlContains("dashboard") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Overview Page is Loaded")
	public boolean isOverviewPageLoaded() {
		return isOverviewLoaded();
	}

	@StepName("Get KPI Cards Count")
	public int getKpiCardsCount() {
		return kpiCards.size();
	}

	@StepName("Get KPI Cards Count")
	public int getKPICardsCount() {
		return getKpiCardsCount();
	}
}
