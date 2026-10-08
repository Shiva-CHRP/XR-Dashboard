package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class SupportTickets extends AbstractComponent {

	public SupportTickets(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']] | //button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']]")
	private WebElement supportNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Support Tickets'] | //header//h1[contains(.,'Support')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Raise Ticket')]")
	private WebElement raiseTicketButton;

	@FindBy(xpath = "//button[contains(.,'Active')]")
	private WebElement activeTab;

	@FindBy(xpath = "//button[contains(.,'In Progress')]")
	private WebElement inProgressTab;

	@FindBy(xpath = "//button[contains(.,'Resolved')]")
	private WebElement resolvedTab;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//input[@placeholder='One line, e.g. Headset not syncing results' or contains(@placeholder,'Subject')] | //*[@role='dialog']//input")
	private WebElement ticketSubjectInput;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//textarea | //*[@role='dialog']//textarea")
	private WebElement ticketDescriptionTextarea;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//button[contains(normalize-space(),'Submit ticket') or normalize-space()='Submit'] | //*[@role='dialog']//button[contains(.,'Submit')]")
	private WebElement submitTicketButton;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//button[@aria-label='Close' or normalize-space()='Cancel'] | //*[@role='dialog']//button[@aria-label='Close' or normalize-space()='Cancel'] | //button[@aria-label='Close']")
	private WebElement closeTicketModalButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> ticketRows;

	@StepName("Click Support Tickets from Sidebar")
	public void clickSupportTickets() {
		navigateToClientRoute(supportNavButton, "support");
	}

	@StepName("Verify Support Tickets Page is Loaded")
	public boolean isSupportTicketsPageLoaded() {
		return waitUtils.waitForUrlContains("support") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Click Raise Ticket")
	public void clickRaiseTicket() {
		org.openqa.selenium.By raiseBtnBy = org.openqa.selenium.By.xpath("//button[contains(normalize-space(),'Raise Ticket')]");
		waitUtils.waitForClickable(raiseBtnBy);
		List<WebElement> btns = driver.findElements(raiseBtnBy);
		if (!btns.isEmpty()) {
			waitUtils.scrollIntoView(btns.get(0));
			waitUtils.clickUsingJS(btns.get(0));
		}
		// Ensure modal dialog appears
		waitUtils.waitUntil(d -> {
			try {
				List<WebElement> dialogs = d.findElements(org.openqa.selenium.By.xpath("//div[contains(@class,'modal-content')] | //*[@role='dialog']"));
				return !dialogs.isEmpty() && dialogs.stream().anyMatch(WebElement::isDisplayed);
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				return false;
			}
		});
	}

	@StepName("Fill Raise Ticket Form")
	public void fillRaiseTicketForm(String subject, String description) {
		waitUtils.waitForVisibility(ticketSubjectInput);
		ticketSubjectInput.clear();
		ticketSubjectInput.sendKeys(subject);
		waitUtils.waitForVisibility(ticketDescriptionTextarea);
		ticketDescriptionTextarea.clear();
		ticketDescriptionTextarea.sendKeys(description);
	}

	@StepName("Submit Raise Ticket")
	public void submitRaiseTicket() {
		waitUtils.waitForClickable(submitTicketButton);
		submitTicketButton.click();
	}

	@StepName("Close Raise Ticket Modal")
	public void closeRaiseTicketModal() {
		org.openqa.selenium.By closeBtnBy = org.openqa.selenium.By.xpath("//div[contains(@class,'modal-content')]//button[@aria-label='Close' or normalize-space()='Cancel'] | //*[@role='dialog']//button[@aria-label='Close' or normalize-space()='Cancel'] | //button[@aria-label='Close']");
		List<WebElement> closeBtns = driver.findElements(closeBtnBy);
		if (!closeBtns.isEmpty()) {
			waitUtils.clickUsingJS(closeBtns.get(0));
		}
		waitUtils.waitUntil(d -> {
			try {
				List<WebElement> dialogs = d.findElements(org.openqa.selenium.By.xpath("//div[contains(@class,'modal-content')] | //*[@role='dialog']"));
				return dialogs.isEmpty() || dialogs.stream().noneMatch(WebElement::isDisplayed);
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				return true;
			}
		});
	}

	@StepName("Switch to Active Tab")
	public void switchToActiveTab() {
		waitUtils.waitForClickable(activeTab);
		activeTab.click();
	}

	@StepName("Switch to In Progress Tab")
	public void switchToInProgressTab() {
		java.util.List<WebElement> inProg = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(.,'In Progress')]"));
		if (!inProg.isEmpty()) {
			inProg.get(0).click();
		}
	}

	@StepName("Switch to Resolved Tab")
	public void switchToResolvedTab() {
		waitUtils.waitForClickable(resolvedTab);
		resolvedTab.click();
	}

	@FindBy(xpath = "//button[contains(normalize-space(),'Reopen ticket') or contains(.,'Reopen ticket')]")
	private WebElement reopenTicketButton;

	@FindBy(xpath = "//div[contains(.,'Support marked this ticket resolved')]")
	private WebElement resolvedNoticeBanner;

	@StepName("Get Ticket Rows Count")
	public int getTicketRowsCount() {
		return ticketRows.size();
	}

	@StepName("Click Ticket Row by Index")
	public void clickTicketRow(int index) {
		if (index >= 0 && index < ticketRows.size()) {
			WebElement row = ticketRows.get(index);
			waitUtils.scrollIntoView(row);
			waitUtils.clickUsingJS(row);
		}
	}

	@StepName("Check if Reopen Ticket Button is Present")
	public boolean isReopenButtonPresent() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//button[contains(normalize-space(),'Reopen ticket')]")).isEmpty();
	}

	@StepName("Click Reopen Ticket")
	public void clickReopenTicket() {
		waitUtils.waitForClickable(reopenTicketButton);
		try {
			reopenTicketButton.click();
		} catch (Exception e) {
			waitUtils.clickUsingJS(reopenTicketButton);
		}
	}

	@StepName("Verify Ticket Reopened Toast")
	public boolean verifyTicketReopenedToast() {
		superadmin.utils.ToastResponse toast = toastUtils.captureToast();
		return toast != null && "success".equalsIgnoreCase(toast.getType()) && toast.getMessage().contains("Ticket reopened");
	}
}
