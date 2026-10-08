package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class VRModules extends AbstractComponent {

	public VRModules(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='VR Modules']] | //button[.//span[normalize-space()='VR Modules']]")
	private WebElement vrModulesNavButton;

	@FindBy(xpath = "//h1[contains(.,'VR Modules')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'portal-card') or contains(@class,'card')]")
	private List<WebElement> moduleCards;

	@StepName("Check if VR Modules is present in sidebar")
	public boolean isVRModulesPresent() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='VR Modules']] | //button[.//span[normalize-space()='VR Modules']]")).isEmpty();
	}

	@StepName("Click VR Modules from Sidebar")
	public void clickVRModules() {
		navigateToClientRoute(vrModulesNavButton, "vr-modules");
	}

	@StepName("Verify VR Modules Page is Loaded")
	public boolean isVRModulesPageLoaded() {
		return waitUtils.waitForUrlContains("vr-modules") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Search VR Modules")
	public void searchVRModules(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get VR Module Cards Count")
	public int getVRModuleCardsCount() {
		return moduleCards.size();
	}
}
