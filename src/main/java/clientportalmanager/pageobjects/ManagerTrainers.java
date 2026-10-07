package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerTrainers extends AbstractComponent {

	public ManagerTrainers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Trainers']]")
	private WebElement trainersNavButton;

	@FindBy(xpath = "//h1[contains(.,'Trainers')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> trainerRows;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@StepName("Click Trainers from Sidebar")
	public void clickTrainers() {
		waitUtils.waitForClickable(trainersNavButton);
		trainersNavButton.click();
	}

	@StepName("Verify Trainers Page is Loaded")
	public boolean isTrainersPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Trainers")
	public void searchTrainers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Trainer Rows Count")
	public int getTrainerRowsCount() {
		return trainerRows.size();
	}
}
