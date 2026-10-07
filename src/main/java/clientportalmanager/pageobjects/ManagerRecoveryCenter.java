package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerRecoveryCenter extends AbstractComponent {

	public ManagerRecoveryCenter(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Recovery Center']]")
	private WebElement recoveryCenterButton;

	@FindBy(xpath = "//h1[contains(.,'Recovery Center')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'portal-card') or contains(@class,'card')] | //table//tbody//tr")
	private List<WebElement> recoveryItems;

	@StepName("Click Manager Recovery Center")
	public void clickManagerRecoveryCenter() {
		waitUtils.waitForClickable(recoveryCenterButton);
		recoveryCenterButton.click();
	}

	@StepName("Verify Manager Recovery Center Page is Loaded")
	public boolean isManagerRecoveryCenterPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Recovery Items")
	public void searchRecoveryItems(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Recovery Items Count")
	public int getRecoveryItemsCount() {
		return recoveryItems.size();
	}
}
