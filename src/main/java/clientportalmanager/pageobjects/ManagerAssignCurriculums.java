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

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Assign Curriculums']] | //button[.//span[normalize-space()='Assign Curriculums']]")
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
		try {
			WebElement btn = driver.findElement(org.openqa.selenium.By.xpath("//aside//button[contains(.,'Assign Curricul')]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(assignCurriculumsButton, "assign-curricula");
		}
		try {
			waitUtils.waitUntil(d -> isManagerAssignCurriculumsPageLoaded(), 5);
		} catch (Exception ignored) {
		}
	}

	@StepName("Verify Manager Assign Curriculums Page is Loaded")
	public boolean isManagerAssignCurriculumsPageLoaded() {
		return waitUtils.waitForUrlContains("assign-curricula", 2)
				|| waitUtils.waitForUrlContains("curriculum-assignment", 2)
				|| !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(.,'Assign Curricul')] | //header//h1")).isEmpty();
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
