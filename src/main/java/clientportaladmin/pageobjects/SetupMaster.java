package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class SetupMaster extends AbstractComponent {

	public SetupMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Setup']]")
	private WebElement setupMasterNavButton;

	@FindBy(xpath = "//h1[contains(.,'Setup')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create Setup') or contains(normalize-space(),'Add Setup')]")
	private WebElement createSetupButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> setupRows;

	@StepName("Click Setup Master from Sidebar")
	public void clickSetupMaster() {
		waitUtils.waitForClickable(setupMasterNavButton);
		setupMasterNavButton.click();
	}

	@StepName("Verify Setup Master Page is Loaded")
	public boolean isSetupMasterPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Setup")
	public void searchSetup(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Setup Rows Count")
	public int getSetupRowsCount() {
		return setupRows.size();
	}
}
