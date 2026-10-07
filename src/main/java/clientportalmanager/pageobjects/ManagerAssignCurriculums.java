package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerAssignCurriculums extends AbstractComponent {

	public ManagerAssignCurriculums(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Curriculums']]")
	private WebElement assignCurriculumsButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Curricul')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search Trainers') or contains(@placeholder,'Search')]")
	private WebElement trainerSearchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Assign Curricul') or contains(normalize-space(),'Assign')]")
	private WebElement assignButton;

	@FindBy(xpath = "//button[.//div[contains(@class,'rounded-full')]]")
	private List<WebElement> trainerItems;

	@StepName("Click Manager Assign Curriculums")
	public void clickManagerAssignCurriculums() {
		waitUtils.waitForClickable(assignCurriculumsButton);
		assignCurriculumsButton.click();
	}

	@StepName("Verify Manager Assign Curriculums Page is Loaded")
	public boolean isManagerAssignCurriculumsPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Trainers")
	public void searchTrainers(String query) {
		waitUtils.waitForVisibility(trainerSearchInput);
		trainerSearchInput.clear();
		trainerSearchInput.sendKeys(query);
	}

	@StepName("Get Trainer Items Count")
	public int getTrainerItemsCount() {
		return trainerItems.size();
	}
}
