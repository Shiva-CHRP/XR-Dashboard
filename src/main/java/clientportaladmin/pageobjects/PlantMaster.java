package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class PlantMaster extends AbstractComponent {

	public PlantMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//svg[contains(@class,'lucide-factory')]] | //button[.//svg[contains(@class,'lucide-factory')]] | //button[.//span[normalize-space()='Plant'] or .//span[normalize-space()='Area']]")
	private WebElement plantMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(normalize-space(),'Create')]")
	private WebElement addMasterButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> masterRows;

	@StepName("Check if Plant Master is present in sidebar (template-gated)")
	public boolean isPlantMasterPresent() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//button[.//svg[contains(@class,'lucide-factory')]] | //button[.//span[normalize-space()='Plant'] or .//span[normalize-space()='Area']]")).isEmpty();
	}

	@StepName("Click Plant / Area Master from Sidebar")
	public void clickPlantMaster() {
		navigateToClientRoute(plantMasterNavButton, "plant-master");
	}

	@StepName("Verify Plant Master Page is Loaded")
	public boolean isPlantMasterPageLoaded() {
		return waitUtils.waitForUrlContains("plant-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Master Rows Count")
	public int getMasterRowsCount() {
		return masterRows.size();
	}
}
