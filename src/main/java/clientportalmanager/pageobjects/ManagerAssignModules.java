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

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Assign Modules']] | //button[.//span[normalize-space()='Assign Modules']]")
	private WebElement assignModulesButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Modules') or contains(.,'Module Assignment')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search Trainers') or contains(@placeholder,'Search')]")
	private WebElement trainerSearchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Assign Modules') or contains(normalize-space(),'Assign')]")
	private WebElement assignButton;

	@FindBy(xpath = "//button[.//div[contains(@class,'rounded-full')]]")
	private List<WebElement> trainerItems;

	@StepName("Click Manager Assign Modules")
	public void clickManagerAssignModules() {
		try {
			WebElement btn = driver.findElement(org.openqa.selenium.By.xpath("//aside//button[contains(.,'Assign Modules')]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(assignModulesButton, "assign-modules");
		}
		try {
			waitUtils.waitUntil(d -> isManagerAssignModulesPageLoaded(), 5);
		} catch (Exception ignored) {
		}
	}

	@StepName("Verify Manager Assign Modules Page is Loaded")
	public boolean isManagerAssignModulesPageLoaded() {
		return waitUtils.waitForUrlContains("assign-modules", 2)
				|| waitUtils.waitForUrlContains("module-assignment", 2)
				|| !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(.,'Assign Modules') or contains(.,'Module Assignment')] | //header//h1")).isEmpty();
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

	@StepName("Is Assign Button Enabled")
	public boolean isAssignButtonEnabled() {
		try {
			return assignButton.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}
}
