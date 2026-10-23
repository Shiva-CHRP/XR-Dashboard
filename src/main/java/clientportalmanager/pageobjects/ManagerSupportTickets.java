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

public class ManagerSupportTickets extends AbstractComponent {

	public ManagerSupportTickets(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']] | //button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']]")
	private WebElement supportNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Support Tickets'] | //header//h1[contains(.,'Support')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Raise Ticket')]")
	private WebElement raiseTicketButton;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//input[@placeholder='One line, e.g. Headset not syncing results' or contains(@placeholder,'Subject')] | //*[@role='dialog']//input")
	private WebElement ticketSubjectInput;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//textarea | //*[@role='dialog']//textarea")
	private WebElement ticketDescriptionTextarea;

	@FindBy(xpath = "//div[contains(@class,'modal-content')]//button[contains(normalize-space(),'Submit ticket') or normalize-space()='Submit'] | //*[@role='dialog']//button[contains(.,'Submit')]")
	private WebElement submitTicketButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> ticketRows;

	@StepName("Click Support Tickets from Sidebar")
	public void clickSupportTickets() {
		try {
			WebElement btn = driver.findElement(By.xpath("//aside//button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']] | //button[.//span[normalize-space()='Support Tickets'] or .//span[normalize-space()='Support']]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(supportNavButton, "support");
		}
		waitUtils.waitForUrlContains("support", 5);
	}

	@StepName("Verify Support Tickets Page is Loaded")
	public boolean isSupportTicketsPageLoaded() {
		return waitUtils.waitForUrlContains("support", 2)
				|| !driver.findElements(By.xpath("//h1[normalize-space()='Support Tickets'] | //header//h1[contains(.,'Support')]")).isEmpty();
	}

	@StepName("Click Raise Ticket")
	public void clickRaiseTicket() {
		By raiseBtnBy = By.xpath("//button[contains(normalize-space(),'Raise Ticket')]");
		WebElement btn = waitUtils.waitForVisibility(raiseBtnBy);
		waitUtils.waitForClickable(btn);
		try {
			btn.click();
		} catch (Exception e) {
			clickUsingJS(btn);
		}
		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[contains(@class,'modal-content')] | //*[@role='dialog']")).isEmpty(), 5);
	}

	@StepName("Fill Raise Ticket Form")
	public void fillRaiseTicketForm(String subject, String description) {
		By subjBy = By.xpath("//div[contains(@class,'modal-content')]//input[@placeholder='One line, e.g. Headset not syncing results' or contains(@placeholder,'Subject')] | //*[@role='dialog']//input");
		WebElement subjInput = waitUtils.waitForVisibility(subjBy);
		subjInput.clear();
		subjInput.sendKeys(subject);

		By descBy = By.xpath("//div[contains(@class,'modal-content')]//textarea | //*[@role='dialog']//textarea");
		WebElement descTextarea = waitUtils.waitForVisibility(descBy);
		descTextarea.clear();
		descTextarea.sendKeys(description);
	}

	@StepName("Submit Raise Ticket")
	public void submitRaiseTicket() {
		By submitBtnBy = By.xpath("//div[contains(@class,'modal-content')]//button[contains(normalize-space(),'Submit ticket') or normalize-space()='Submit'] | //*[@role='dialog']//button[contains(.,'Submit')]");
		WebElement submitBtn = waitUtils.waitForVisibility(submitBtnBy);
		waitUtils.waitForClickable(submitBtn);
		try {
			submitBtn.click();
		} catch (Exception e) {
			clickUsingJS(submitBtn);
		}
	}

	@StepName("Raise Support Ticket with Toast Verification")
	public ToastResponse raiseSupportTicket(String subject, String description) {
		clickRaiseTicket();
		fillRaiseTicketForm(subject, description);
		submitRaiseTicket();
		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	@StepName("Get Ticket Rows Count")
	public int getTicketRowsCount() {
		return ticketRows.size();
	}
}