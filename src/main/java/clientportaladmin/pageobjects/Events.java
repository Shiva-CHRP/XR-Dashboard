package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Events extends AbstractComponent {

	public Events(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Events'] or .//span[normalize-space()='Sessions']] | //button[.//span[normalize-space()='Events'] or .//span[normalize-space()='Sessions']]")
	private WebElement eventsNavButton;

	@FindBy(xpath = "//h1[contains(.,'Event')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[normalize-space()='Events']")
	private WebElement eventsOuterTab;

	@FindBy(xpath = "//button[normalize-space()='Results']")
	private WebElement resultsOuterTab;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create Event')] | //button[contains(normalize-space(),'Create Session')]")
	private WebElement createEventButton;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> eventRows;

	@StepName("Click Events from Sidebar")
	public void clickEvents() {
		navigateToClientRoute(eventsNavButton, "sessions");
	}

	@StepName("Click Sessions (Backward Compatibility)")
	public void clickSessions() {
		clickEvents();
	}

	@StepName("Verify Events Page is Loaded")
	public boolean isEventsPageLoaded() {
		return waitUtils.waitForUrlContains("sessions") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Switch to Events Tab")
	public void switchToEventsTab() {
		waitUtils.waitForClickable(eventsOuterTab);
		eventsOuterTab.click();
	}

	@StepName("Switch to Results Tab")
	public void switchToResultsTab() {
		waitUtils.waitForClickable(resultsOuterTab);
		resultsOuterTab.click();
	}

	@StepName("Click Create Event")
	public void clickCreateEvent() {
		waitUtils.waitForClickable(createEventButton);
		createEventButton.click();
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
}
