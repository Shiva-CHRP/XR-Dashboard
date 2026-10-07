package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DivisionMaster extends AbstractComponent {

	public DivisionMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Division']]")
	private WebElement divisionMasterNavButton;

	@FindBy(xpath = "//h1[contains(.,'Division')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create Division') or contains(normalize-space(),'Add Division')]")
	private WebElement createDivisionButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> divisionRows;

	@StepName("Click Division Master from Sidebar")
	public void clickDivisionMaster() {
		waitUtils.waitForClickable(divisionMasterNavButton);
		divisionMasterNavButton.click();
	}

	@StepName("Verify Division Master Page is Loaded")
	public boolean isDivisionMasterPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Division")
	public void searchDivision(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Division Rows Count")
	public int getDivisionRowsCount() {
		return divisionRows.size();
	}
}
