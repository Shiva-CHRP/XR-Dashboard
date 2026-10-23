package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

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
				|| !driver.findElements(By.xpath("//h1[contains(.,'Event') or contains(.,'Session')] | //header//h1")).isEmpty();
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
		return !driver.findElements(By.xpath("//td[contains(.,'No') or contains(.,'found') or @colSpan] | //div[contains(.,'No') and contains(.,'found')]")).isEmpty()
				|| getEventRowsCount() == 0;
	}

	@StepName("Click Create Event")
	public void clickCreateEvent() {
		By createBtnBy = By.xpath("//button[contains(normalize-space(),'Create Event')]");
		WebElement btn = waitUtils.waitForVisibility(createBtnBy);
		waitUtils.waitForClickable(btn);
		try {
			btn.click();
		} catch (Exception e) {
			clickUsingJS(btn);
		}
	}

	@StepName("Verify Create Event Page is Loaded")
	public boolean isCreateEventPageLoaded() {
		return waitUtils.waitForUrlContains("sessions/create", 5)
				|| !driver.findElements(By.xpath("//h1[contains(.,'Create Event') or contains(.,'Create Session')]")).isEmpty();
	}

	@StepName("Cancel Create Event and Return")
	public void cancelCreateEvent() {
		By backBtnBy = By.xpath("//button[@aria-label='Back' or contains(normalize-space(),'Cancel')]");
		List<WebElement> backBtns = driver.findElements(backBtnBy);
		if (!backBtns.isEmpty()) {
			backBtns.get(0).click();
		} else {
			clickManagerMyEvents();
		}
	}

	@StepName("Manager Cancel Event with Reason")
	public ToastResponse cancelEvent(String eventName, String reason) {
		searchEvents(eventName);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By cancelItemBy = By.xpath("//div[@role='menuitem' and (contains(.,'Cancel') or contains(.,'Cancel Event'))]");
		WebElement cancelItem = waitUtils.waitForVisibility(cancelItemBy);
		waitUtils.waitForClickable(cancelItem);
		try {
			cancelItem.click();
		} catch (Exception e) {
			clickUsingJS(cancelItem);
		}

		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[@role='dialog']")).isEmpty(), 5);
		By reasonBy = By.xpath("//div[@role='dialog']//textarea");
		List<WebElement> reasonTextareas = driver.findElements(reasonBy);
		if (!reasonTextareas.isEmpty()) {
			reasonTextareas.get(0).clear();
			reasonTextareas.get(0).sendKeys(reason);
		}

		By confirmCancelBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Confirm Cancellation') or contains(.,'Confirm')]");
		WebElement confirmBtn = waitUtils.waitForVisibility(confirmCancelBtnBy);
		waitUtils.waitForClickable(confirmBtn);
		try {
			confirmBtn.click();
		} catch (Exception e) {
			clickUsingJS(confirmBtn);
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	@StepName("Manager Delete Event")
	public ToastResponse deleteEvent(String eventName) {
		searchEvents(eventName);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By deleteItemBy = By.xpath("//div[@role='menuitem' and contains(.,'Delete')]");
		WebElement deleteItem = waitUtils.waitForVisibility(deleteItemBy);
		waitUtils.waitForClickable(deleteItem);
		try {
			deleteItem.click();
		} catch (Exception e) {
			clickUsingJS(deleteItem);
		}

		By confirmDeleteBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Delete Event') or contains(.,'Delete')]");
		WebElement confirmBtn = waitUtils.waitForVisibility(confirmDeleteBtnBy);
		waitUtils.waitForClickable(confirmBtn);
		try {
			confirmBtn.click();
		} catch (Exception e) {
			clickUsingJS(confirmBtn);
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}
}