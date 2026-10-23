package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

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

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		searchSetup(query);
	}

	@StepName("Get Setup Rows Count")
	public int getSetupRowsCount() {
		return setupRows.size();
	}

	@StepName("Create Setup Master Record")
	public ToastResponse createSetup(String name, String description) {
		return performCreateMaster(name, description);
	}

	@StepName("Create Master Record (Generic)")
	public ToastResponse createMaster(String name, String description) {
		return createSetup(name, description);
	}

	@StepName("Edit Setup Master Record")
	public ToastResponse editSetup(String currentName, String newName, String newDescription) {
		return performEditMaster(currentName, newName, newDescription);
	}

	@StepName("Edit Master Record (Generic)")
	public ToastResponse editMaster(String currentName, String newName, String newDescription) {
		return editSetup(currentName, newName, newDescription);
	}

	@StepName("Toggle Setup Status (Deactivate/Reactivate)")
	public ToastResponse toggleStatus(String name) {
		return performToggleMasterStatus(name);
	}

	@StepName("Delete Setup Master Record")
	public ToastResponse deleteSetup(String name) {
		return performDeleteMaster(name);
	}
}