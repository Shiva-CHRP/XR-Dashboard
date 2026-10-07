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

	@FindBy(xpath = "//button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']]")
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
		waitUtils.waitForClickable(supportNavButton);
		supportNavButton.click();
	}

	@StepName("Verify Support Tickets Page is Loaded")
	public boolean isSupportTicketsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
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
		waitUtils.waitForClickable(inProgressTab);
		inProgressTab.click();
	}

	@StepName("Switch to Resolved Tab")
	public void switchToResolvedTab() {
		waitUtils.waitForClickable(resolvedTab);
		resolvedTab.click();
	}

	@StepName("Get Ticket Rows Count")
	public int getTicketRowsCount() {
		return ticketRows.size();
	}
}
