package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DesignationMaster extends AbstractComponent {

	public DesignationMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//svg[contains(@class,'lucide-badge-check')]] | //button[.//span[normalize-space()='Designation'] or .//span[normalize-space()='Designation Master']]")
	private WebElement designationMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(.,'Create')]")
	private WebElement addDesignationButton;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> designationRows;

	@StepName("Click Designation Master from Sidebar")
	public void clickDesignationMaster() {
		waitUtils.waitForClickable(designationMasterNavButton);
		designationMasterNavButton.click();
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
}
