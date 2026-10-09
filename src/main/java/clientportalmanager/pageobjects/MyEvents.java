package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class MyEvents extends AbstractComponent {

	public MyEvents(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Events'] or .//span[normalize-space()='Sessions'] or .//span[normalize-space()='My Events']] | //button[.//span[normalize-space()='Events'] or .//span[normalize-space()='Sessions'] or .//span[normalize-space()='My Events']]")
	private WebElement myEventsButton;

	@FindBy(xpath = "//h1[contains(.,'Event') or contains(.,'Session')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[normalize-space()='Events']")
	private WebElement eventsTab;

	@FindBy(xpath = "//button[normalize-space()='Results']")
	private WebElement resultsTab;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create Event')]")
	private WebElement createEventButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> eventRows;

	@StepName("Click Manager My Events")
	public void clickManagerMyEvents() {
		navigateToClientRoute(myEventsButton, "sessions");
	}

	@StepName("Verify My Events Page is Loaded")
	public boolean isMyEventsPageLoaded() {
		return waitUtils.waitForUrlContains("sessions", 2)
				|| waitUtils.waitForUrlContains("events", 2)
				|| !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(.,'Event') or contains(.,'Session')] | //header//h1")).isEmpty();
	}

	@StepName("Switch to Events Tab")
	public void clickEventsTab() {
		waitUtils.waitForClickable(eventsTab);
		eventsTab.click();
	}

	@StepName("Switch to Results Tab")
	public void clickResultsTab() {
		waitUtils.waitForClickable(resultsTab);
		resultsTab.click();
	}

	@StepName("Search Events")
	public void searchEvents(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Event Rows Count")
	public int getEventRowsCount() {
		return eventRows.size();
	}

	@StepName("Is Empty Table State Displayed")
	public boolean isEmptyTableStateDisplayed() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//td[contains(.,'No') or contains(.,'found') or @colSpan] | //div[contains(.,'No') and contains(.,'found')]")).isEmpty()
				|| getEventRowsCount() == 0;
	}
}
