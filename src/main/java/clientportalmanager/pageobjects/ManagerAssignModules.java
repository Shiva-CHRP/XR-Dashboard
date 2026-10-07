package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerAssignModules extends AbstractComponent {

	public ManagerAssignModules(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Modules']]")
	private WebElement assignModulesButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Modules')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search Trainers') or contains(@placeholder,'Search')]")
	private WebElement trainerSearchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Assign Modules') or contains(normalize-space(),'Assign')]")
	private WebElement assignButton;

	@FindBy(xpath = "//button[.//div[contains(@class,'rounded-full')]]")
	private List<WebElement> trainerItems;

	@StepName("Click Manager Assign Modules")
	public void clickManagerAssignModules() {
		waitUtils.waitForClickable(assignModulesButton);
		assignModulesButton.click();
	}

	@StepName("Verify Manager Assign Modules Page is Loaded")
	public boolean isManagerAssignModulesPageLoaded() {
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
