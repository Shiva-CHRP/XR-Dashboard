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

	@FindBy(xpath = "//aside//button[normalize-space()='Assign Modules' or .//span[normalize-space()='Assign Modules']]")
	private WebElement assignModulesButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Modules') or contains(.,'Module Assignment')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@class,'fb-search-input') or contains(@placeholder,'Search') or contains(@placeholder,'search') or contains(@placeholder,'modules')]")
	private WebElement searchInput;

	@StepName("Click Assign Modules from Sidebar")
	public void clickAssignModules() {
		try {
			org.openqa.selenium.WebElement btn = driver.findElement(org.openqa.selenium.By.xpath("//aside//button[contains(.,'Assign Modules')]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(assignModulesButton, "module-assignment");
		}
		waitUtils.waitForUrlContains("module-assignment", 5);
	}

	@StepName("Verify Assign Modules Page is Loaded")
	public boolean isAssignModulesLoaded() {
		try {
			return waitUtils.waitForUrlContains("module-assignment", 5) 
					|| !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(.,'Assign Modules') or contains(.,'Module Assignment')] | //header//h1")).isEmpty();
		} catch (Exception e) {
			return false;
		}
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
