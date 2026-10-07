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

	@FindBy(xpath = "//button[.//span[normalize-space()='Plant'] or .//span[normalize-space()='Area']]")
	private WebElement plantMasterNavButton;

	@FindBy(xpath = "//h1[contains(.,'Plant') or contains(.,'Area')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(normalize-space(),'Create')]")
	private WebElement addMasterButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> masterRows;

	@StepName("Click Plant / Area Master from Sidebar")
	public void clickPlantMaster() {
		waitUtils.waitForClickable(plantMasterNavButton);
		plantMasterNavButton.click();
	}

	@StepName("Verify Plant Master Page is Loaded")
	public boolean isPlantMasterPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
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
