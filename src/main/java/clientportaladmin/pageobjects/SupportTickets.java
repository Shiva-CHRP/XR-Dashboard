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

	@FindBy(xpath = "//*[@role='dialog']//input[@placeholder='Brief summary of the issue' or contains(@placeholder,'Subject')]")
	private WebElement ticketSubjectInput;

	@FindBy(xpath = "//*[@role='dialog']//textarea[contains(@placeholder,'Provide details') or contains(@placeholder,'Description')]")
	private WebElement ticketDescriptionTextarea;

	@FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Submit Ticket' or normalize-space()='Submit']")
	private WebElement submitTicketButton;

	@FindBy(xpath = "//*[@role='dialog']//button[.//svg or @aria-label='Close' or normalize-space()='Cancel']")
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
		waitUtils.waitForClickable(raiseTicketButton);
		raiseTicketButton.click();
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
		waitUtils.waitForClickable(closeTicketModalButton);
		closeTicketModalButton.click();
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
