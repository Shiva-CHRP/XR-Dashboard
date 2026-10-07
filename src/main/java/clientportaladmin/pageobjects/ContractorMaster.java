package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ContractorMaster extends AbstractComponent {

	public ContractorMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//svg[contains(@class,'lucide-handshake')]] | //button[.//span[normalize-space()='Contractor'] or .//span[normalize-space()='Contractor Master'] or .//span[normalize-space()='Partner'] or .//span[normalize-space()='Vendor']]")
	private WebElement contractorMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(.,'Create')]")
	private WebElement addContractorButton;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> contractorRows;

	@StepName("Click Contractor Master from Sidebar")
	public void clickContractorMaster() {
		waitUtils.waitForClickable(contractorMasterNavButton);
		contractorMasterNavButton.click();
	}

	@StepName("Verify Contractor Master Page is Loaded")
	public boolean isContractorMasterLoaded() {
		return waitUtils.waitForUrlContains("partner-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Contractor Master Page is Loaded")
	public boolean isContractorMasterPageLoaded() {
		return isContractorMasterLoaded();
	}

	@StepName("Search Contractor")
	public void searchContractor(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		searchContractor(query);
	}

	@StepName("Get Contractor Rows Count")
	public int getContractorRowsCount() {
		return contractorRows.size();
	}
}
