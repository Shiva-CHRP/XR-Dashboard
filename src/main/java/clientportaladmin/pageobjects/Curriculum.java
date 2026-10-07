package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Curriculum extends AbstractComponent {

	public Curriculum(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Curriculum']]")
	private WebElement curriculumNavButton;

	@FindBy(xpath = "//h1[contains(.,'Curriculum') or contains(.,'Curricula')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(.,'Browse Catalogue')] | //a[contains(.,'Browse Catalogue')]")
	private WebElement browseCatalogueItem;

	@FindBy(xpath = "//button[contains(.,'My Curriculums')] | //a[contains(.,'My Curriculums')]")
	private WebElement myCurriculumsItem;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'card')] | //table//tbody//tr")
	private java.util.List<WebElement> curriculumCards;

	@StepName("Click Curriculum from Sidebar")
	public void clickCurriculum() {
		waitUtils.waitForClickable(curriculumNavButton);
		curriculumNavButton.click();
	}

	@StepName("Verify Curriculum Page is Loaded")
	public boolean isCurriculumLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Verify Curriculum Page is Loaded")
	public boolean isCurriculumPageLoaded() {
		return isCurriculumLoaded();
	}

	@StepName("Click Browse Catalogue")
	public void clickBrowseCatalogue() {
		waitUtils.waitForClickable(browseCatalogueItem);
		browseCatalogueItem.click();
	}

	@StepName("Click My Curriculums")
	public void clickMyCurriculums() {
		waitUtils.waitForClickable(myCurriculumsItem);
		myCurriculumsItem.click();
	}

	@StepName("Search Curricula")
	public void searchCurricula(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Curriculums")
	public void searchCurriculums(String query) {
		searchCurricula(query);
	}

	@StepName("Get Curricula Count")
	public int getCurriculaCount() {
		return curriculumCards.size();
	}
}
