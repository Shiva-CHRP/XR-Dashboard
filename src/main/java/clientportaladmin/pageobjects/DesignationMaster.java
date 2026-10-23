package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class DesignationMaster extends AbstractComponent {

	public DesignationMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[normalize-space()='Designation' or .//span[normalize-space()='Designation']] | //aside//button[.//svg[contains(@class,'lucide-badge-check')]]")
	private WebElement designationMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@class,'fb-search-input') or contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(.,'Create')]")
	private WebElement addDesignationButton;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> designationRows;

	@StepName("Click Designation Master from Sidebar")
	public void clickDesignationMaster() {
		navigateToClientRoute(designationMasterNavButton, "designation-master");
	}

	@StepName("Verify Designation Master Page is Loaded")
	public boolean isDesignationMasterLoaded() {
		return waitUtils.waitForUrlContains("designation-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Designation Master Page is Loaded")
	public boolean isDesignationMasterPageLoaded() {
		return isDesignationMasterLoaded();
	}

	@StepName("Search Designation")
	public void searchDesignation(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		searchDesignation(query);
	}

	@StepName("Get Designation Rows Count")
	public int getDesignationRowsCount() {
		return designationRows.size();
	}

	@StepName("Is Empty Table State Displayed")
	public boolean isEmptyTableStateDisplayed() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//td[contains(.,'No') or contains(.,'found') or @colSpan] | //div[contains(.,'No') and contains(.,'found')]")).isEmpty()
				|| getDesignationRowsCount() == 0;
	}

	@StepName("Create Designation Master Record")
	public ToastResponse createDesignation(String name, String description) {
		return performCreateMaster(name, description);
	}

	@StepName("Create Master Record (Generic)")
	public ToastResponse createMaster(String name, String description) {
		return createDesignation(name, description);
	}

	@StepName("Edit Designation Master Record")
	public ToastResponse editDesignation(String currentName, String newName, String newDescription) {
		return performEditMaster(currentName, newName, newDescription);
	}

	@StepName("Edit Master Record (Generic)")
	public ToastResponse editMaster(String currentName, String newName, String newDescription) {
		return editDesignation(currentName, newName, newDescription);
	}

	@StepName("Toggle Designation Status (Deactivate/Reactivate)")
	public ToastResponse toggleStatus(String name) {
		return performToggleMasterStatus(name);
	}

	@StepName("Delete Designation Master Record")
	public ToastResponse deleteDesignation(String name) {
		return performDeleteMaster(name);
	}
}