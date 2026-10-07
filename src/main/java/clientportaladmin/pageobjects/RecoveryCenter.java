package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class RecoveryCenter extends AbstractComponent {

	public RecoveryCenter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Recovery Center']]")
	private WebElement recoveryCenterNavButton;

	@FindBy(xpath = "//h1[contains(.,'Recovery') or contains(.,'Recycle')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private java.util.List<WebElement> recoveryRows;

	@StepName("Click Recovery Center from Sidebar")
	public void clickRecoveryCenter() {
		waitUtils.waitForClickable(recoveryCenterNavButton);
		recoveryCenterNavButton.click();
	}

	@StepName("Verify Recovery Center Page is Loaded")
	public boolean isRecoveryCenterLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Deleted Records")
	public void searchDeletedRecords(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Deleted Rows Count")
	public int getDeletedRowsCount() {
		return recoveryRows.size();
	}
}
