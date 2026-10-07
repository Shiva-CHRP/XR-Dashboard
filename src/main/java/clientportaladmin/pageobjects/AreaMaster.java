package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AreaMaster extends AbstractComponent {

	public AreaMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Area'] or .//span[normalize-space()='Mine']]")
	private WebElement areaMasterNavButton;

	@FindBy(xpath = "//h1[contains(.,'Area') or contains(.,'Mine')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create') or contains(normalize-space(),'Add')]")
	private WebElement createAreaButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> areaRows;

	@StepName("Click Area / Mine Master from Sidebar")
	public void clickAreaMaster() {
		waitUtils.waitForClickable(areaMasterNavButton);
		areaMasterNavButton.click();
	}

	@StepName("Verify Area Master Page is Loaded")
	public boolean isAreaMasterPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Area")
	public void searchArea(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Area Rows Count")
	public int getAreaRowsCount() {
		return areaRows.size();
	}
}
