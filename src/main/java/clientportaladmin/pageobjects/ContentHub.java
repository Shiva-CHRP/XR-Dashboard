package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ContentHub extends AbstractComponent {

	public ContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Content Hub'] or .//span[normalize-space()='Modules']]")
	private WebElement contentHubButton;

	@FindBy(xpath = "//h1[contains(.,'Content Hub') or contains(.,'Modules')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'cursor-pointer') or contains(@class,'card')]")
	private java.util.List<WebElement> moduleCards;

	@StepName("Click Content Hub from Sidebar")
	public void clickContentHub() {
		waitUtils.waitForClickable(contentHubButton);
		contentHubButton.click();
	}

	@StepName("Verify Content Hub Page is Loaded")
	public boolean isContentHubLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Verify Content Hub Page is Loaded")
	public boolean isContentHubPageLoaded() {
		return isContentHubLoaded();
	}

	@StepName("Search Modules")
	public void searchModules(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Module Cards Count")
	public int getModuleCardsCount() {
		return moduleCards.size();
	}
}
