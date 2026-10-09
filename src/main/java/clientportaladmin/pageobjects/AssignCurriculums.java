package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AssignCurriculums extends AbstractComponent {

	public AssignCurriculums(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[normalize-space()='Assign Curriculums' or .//span[normalize-space()='Assign Curriculums']]")
	private WebElement assignCurriculumsButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Curriculums') or contains(.,'Curriculum Assignment')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@class,'fb-search-input') or contains(@placeholder,'Search') or contains(@placeholder,'search') or contains(@placeholder,'Managers')]")
	private WebElement searchInput;

	@StepName("Click Assign Curriculums from Sidebar")
	public void clickAssignCurriculums() {
		if (waitUtils.waitForUrlContains("curriculum-assignment", 1)) {
			return;
		}
		navigateToClientRoute(assignCurriculumsButton, "curriculum-assignment");
		if (!waitUtils.waitForUrlContains("curriculum-assignment", 3)) {
			try {
				org.openqa.selenium.WebElement btn = driver.findElement(org.openqa.selenium.By.xpath("//aside//button[contains(.,'Assign Curriculums')]"));
				waitUtils.scrollIntoView(btn);
				waitUtils.clickUsingJS(btn);
			} catch (Exception ignored) {
			}
		}
		waitUtils.waitForUrlContains("curriculum-assignment", 5);
	}

	@StepName("Verify Assign Curriculums Page is Loaded")
	public boolean isAssignCurriculumsLoaded() {
		try {
			return waitUtils.waitForUrlContains("curriculum-assignment", 5) 
					|| !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(.,'Assign Curriculums') or contains(.,'Curriculum Assignment')] | //header//h1")).isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Verify Assign Curriculums Page is Loaded")
	public boolean isAssignCurriculumsPageLoaded() {
		return isAssignCurriculumsLoaded();
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
