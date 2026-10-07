package clientportaltrainer.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class TrainerMyEvents extends AbstractComponent {

	public TrainerMyEvents(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='My Events']]")
	private WebElement myEventsButton;

	@FindBy(xpath = "//h1[contains(.,'My Events') or contains(.,'Events')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[normalize-space()='Events']")
	private WebElement eventsTab;

	@FindBy(xpath = "//button[normalize-space()='Results']")
	private WebElement resultsTab;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> eventRows;

	@StepName("Click Trainer My Events")
	public void clickTrainerMyEvents() {
		waitUtils.waitForClickable(myEventsButton);
		myEventsButton.click();
	}

	@StepName("Verify Trainer My Events Page is Loaded")
	public boolean isTrainerMyEventsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
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
}
