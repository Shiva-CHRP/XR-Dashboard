package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AssignModules extends AbstractComponent {

	public AssignModules(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Modules']]")
	private WebElement assignModulesButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Modules') or contains(.,'Module Assignment')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@StepName("Click Assign Modules from Sidebar")
	public void clickAssignModules() {
		waitUtils.waitForClickable(assignModulesButton);
		assignModulesButton.click();
	}

	@StepName("Verify Assign Modules Page is Loaded")
	public boolean isAssignModulesLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Verify Assign Modules Page is Loaded")
	public boolean isAssignModulesPageLoaded() {
		return isAssignModulesLoaded();
	}

	@StepName("Search Trainers / Managers")
	public void searchTrainers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Managers")
	public void searchManagers(String query) {
		searchTrainers(query);
	}
}
