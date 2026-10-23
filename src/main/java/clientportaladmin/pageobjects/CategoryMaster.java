package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class CategoryMaster extends AbstractComponent {

	public CategoryMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//svg[contains(@class,'lucide-tags')]] | //button[.//svg[contains(@class,'lucide-tags')]] | //button[.//span[normalize-space()='Category']]")
	private WebElement categoryMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create') or contains(normalize-space(),'Add')]")
	private WebElement createCategoryButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> categoryRows;

	@StepName("Check if Category Master is present in sidebar (template-gated)")
	public boolean isCategoryMasterPresent() {
		return !driver.findElements(org.openqa.selenium.By.xpath("//button[.//svg[contains(@class,'lucide-tags')]] | //button[.//span[normalize-space()='Category']]")).isEmpty();
	}

	@StepName("Click Category Master from Sidebar")
	public void clickCategoryMaster() {
		navigateToClientRoute(categoryMasterNavButton, "category-master");
	}

	@StepName("Verify Category Master Page is Loaded")
	public boolean isCategoryMasterPageLoaded() {
		return waitUtils.waitForUrlContains("category-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Search Category")
	public void searchCategory(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		searchCategory(query);
	}

	@StepName("Get Category Rows Count")
	public int getCategoryRowsCount() {
		return categoryRows.size();
	}

	@StepName("Create Category Master Record")
	public ToastResponse createCategory(String name, String description) {
		return performCreateMaster(name, description);
	}

	@StepName("Create Master Record (Generic)")
	public ToastResponse createMaster(String name, String description) {
		return createCategory(name, description);
	}

	@StepName("Edit Category Master Record")
	public ToastResponse editCategory(String currentName, String newName, String newDescription) {
		return performEditMaster(currentName, newName, newDescription);
	}

	@StepName("Edit Master Record (Generic)")
	public ToastResponse editMaster(String currentName, String newName, String newDescription) {
		return editCategory(currentName, newName, newDescription);
	}

	@StepName("Toggle Category Status (Deactivate/Reactivate)")
	public ToastResponse toggleStatus(String name) {
		return performToggleMasterStatus(name);
	}

	@StepName("Delete Category Master Record")
	public ToastResponse deleteCategory(String name) {
		return performDeleteMaster(name);
	}
}