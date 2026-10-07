package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerContentHub extends AbstractComponent {

	public ManagerContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Content Hub']]")
	private WebElement contentHubButton;

	@FindBy(xpath = "//h1[contains(.,'Content Hub')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'portal-card') or contains(@class,'card')]")
	private List<WebElement> moduleCards;

	@StepName("Click Manager Content Hub")
	public void clickManagerContentHub() {
		waitUtils.waitForClickable(contentHubButton);
		contentHubButton.click();
	}

	@StepName("Verify Manager Content Hub Page is Loaded")
	public boolean isManagerContentHubPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Search Content Hub Modules")
	public void searchModules(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Content Hub Module Cards Count")
	public int getModuleCardsCount() {
		return moduleCards.size();
	}
}
