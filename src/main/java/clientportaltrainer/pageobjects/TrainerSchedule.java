package clientportaltrainer.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class TrainerSchedule extends AbstractComponent {

	public TrainerSchedule(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Schedule']]")
	private WebElement scheduleNavButton;

	@FindBy(xpath = "//h1[contains(.,'Schedule')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//div[contains(@class,'space-y-2')]//div[contains(@class,'cursor-pointer') or contains(@class,'border')]")
	private List<WebElement> scheduledEventCards;

	@StepName("Click Schedule from Sidebar")
	public void clickSchedule() {
		waitUtils.waitForClickable(scheduleNavButton);
		scheduleNavButton.click();
	}

	@StepName("Verify Schedule Page is Loaded")
	public boolean isSchedulePageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Get Scheduled Event Cards Count")
	public int getScheduledEventCardsCount() {
		return scheduledEventCards.size();
	}
}
