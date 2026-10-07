package clientportaltrainer.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class TrainerOverview extends AbstractComponent {

	public TrainerOverview(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Overview']]")
	private WebElement overviewButton;

	@FindBy(xpath = "//h1[contains(.,'Dashboard')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'View All Events')]")
	private WebElement viewAllEventsButton;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'cursor-pointer') or contains(@class,'portal-card')]")
	private List<WebElement> kpiCards;

	@StepName("Click Trainer Overview")
	public void clickTrainerOverview() {
		waitUtils.waitForClickable(overviewButton);
		overviewButton.click();
	}

	@StepName("Verify Trainer Overview Page is Loaded")
	public boolean isTrainerOverviewPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Click View All Events Button")
	public void clickViewAllEvents() {
		waitUtils.waitForClickable(viewAllEventsButton);
		viewAllEventsButton.click();
	}

	@StepName("Get KPI Cards Count")
	public int getKPICardsCount() {
		return kpiCards.size();
	}
}
