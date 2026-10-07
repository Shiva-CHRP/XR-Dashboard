package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DepartmentMaster extends AbstractComponent {

	public DepartmentMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Department'] or .//span[normalize-space()='Department Master']]")
	private WebElement departmentMasterNavButton;

	@FindBy(xpath = "//h1[contains(.,'Department')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(.,'Create')]")
	private WebElement addDepartmentButton;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> departmentRows;

	@StepName("Click Department Master from Sidebar")
	public void clickDepartmentMaster() {
		waitUtils.waitForClickable(departmentMasterNavButton);
		departmentMasterNavButton.click();
	}

	@StepName("Verify Department Master Page is Loaded")
	public boolean isDepartmentMasterLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Verify Department Master Page is Loaded")
	public boolean isDepartmentMasterPageLoaded() {
		return isDepartmentMasterLoaded();
	}

	@StepName("Search Department")
	public void searchDepartment(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		searchDepartment(query);
	}

	@StepName("Get Department Rows Count")
	public int getDepartmentRowsCount() {
		return departmentRows.size();
	}
}
