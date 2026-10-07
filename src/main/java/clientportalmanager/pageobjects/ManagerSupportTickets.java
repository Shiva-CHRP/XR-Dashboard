package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerSupportTickets extends AbstractComponent {

	public ManagerSupportTickets(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']]")
	private WebElement supportNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Support Tickets'] | //header//h1[contains(.,'Support')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Raise Ticket')]")
	private WebElement raiseTicketButton;

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

	@StepName("Get Ticket Rows Count")
	public int getTicketRowsCount() {
		return ticketRows.size();
	}
}
