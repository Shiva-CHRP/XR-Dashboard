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

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//svg[contains(@class,'lucide-split')]] | //button[.//svg[contains(@class,'lucide-split')]] | //button[.//span[normalize-space()='Division'] or .//span[normalize-space()='Nodal Office']]")
	private WebElement divisionMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create') or contains(normalize-space(),'Add')]")
	private WebElement createDivisionButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> divisionRows;

	@StepName("Check if Division Master is present in sidebar (template-gated)")
	public boolean isDivisionMasterPresent() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//button[.//svg[contains(@class,'lucide-split')]] | //button[.//span[normalize-space()='Division'] or .//span[normalize-space()='Nodal Office']]")).isEmpty();
	}

	@StepName("Click Division Master from Sidebar")
	public void clickDivisionMaster() {
		navigateToClientRoute(divisionMasterNavButton, "division-master");
	}

	@StepName("Verify Division Master Page is Loaded")
	public boolean isDivisionMasterPageLoaded() {
		return waitUtils.waitForUrlContains("division-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
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
