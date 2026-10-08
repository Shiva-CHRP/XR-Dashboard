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

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//svg[contains(@class,'lucide-wrench')]] | //button[.//svg[contains(@class,'lucide-wrench')]] | //button[.//span[normalize-space()='Setup']]")
	private WebElement setupMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create') or contains(normalize-space(),'Add')]")
	private WebElement createSetupButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> setupRows;

	@StepName("Check if Setup Master is present in sidebar (template-gated)")
	public boolean isSetupMasterPresent() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//button[.//svg[contains(@class,'lucide-wrench')]] | //button[.//span[normalize-space()='Setup']]")).isEmpty();
	}

	@StepName("Click Setup Master from Sidebar")
	public void clickSetupMaster() {
		navigateToClientRoute(setupMasterNavButton, "setup-master");
	}

	@StepName("Verify Setup Master Page is Loaded")
	public boolean isSetupMasterPageLoaded() {
		return waitUtils.waitForUrlContains("setup-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
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
