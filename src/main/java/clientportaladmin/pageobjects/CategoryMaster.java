package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class CategoryMaster extends AbstractComponent {

	public CategoryMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//svg[contains(@class,'lucide-tags')]] | //button[.//span[normalize-space()='Category']]")
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
		waitUtils.waitForClickable(categoryMasterNavButton);
		categoryMasterNavButton.click();
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

	@StepName("Get Category Rows Count")
	public int getCategoryRowsCount() {
		return categoryRows.size();
	}
}
