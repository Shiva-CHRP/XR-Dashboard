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

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Curriculum']] | //button[.//span[normalize-space()='Curriculum']]")
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
		navigateToClientRoute(curriculumNavButton, "curricula");
	}

	@StepName("Verify Curriculum Page is Loaded")
	public boolean isCurriculumLoaded() {
		return waitUtils.waitForUrlContains("curricula") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Curriculum Page is Loaded")
	public boolean isCurriculumPageLoaded() {
		return isCurriculumLoaded();
	}

	@StepName("Click Browse Catalogue")
	public void clickBrowseCatalogue() {
		java.util.List<WebElement> items = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(.,'Browse Catalogue')] | //a[contains(.,'Browse Catalogue')] | //button[contains(.,'All')]"));
		if (!items.isEmpty()) {
			items.get(0).click();
		}
	}

	@StepName("Click My Curriculums")
	public void clickMyCurriculums() {
		java.util.List<WebElement> items = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(.,'My Curriculums')] | //a[contains(.,'My Curriculums')] | //button[contains(.,'Free')]"));
		if (!items.isEmpty()) {
			items.get(0).click();
		}
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

	@StepName("Click Curriculum Card by Index")
	public void clickCurriculumCard(int index) {
		if (index >= 0 && index < curriculumCards.size()) {
			WebElement card = curriculumCards.get(index);
			waitUtils.scrollIntoView(card);
			waitUtils.clickUsingJS(card);
		}
	}

	@StepName("Click View Assessment on Lesson Row")
	public void clickViewAssessment() {
		java.util.List<WebElement> viewBtns = driver.findElements(org.openqa.selenium.By.xpath("//button[contains(.,'View') or .//*[local-name()='svg' and contains(@class,'lucide-eye')]]"));
		if (!viewBtns.isEmpty()) {
			waitUtils.scrollIntoView(viewBtns.get(0));
			waitUtils.clickUsingJS(viewBtns.get(0));
		}
	}

	@StepName("Verify Assessment Preview Dialog is Open")
	public boolean isAssessmentPreviewOpen() {
		return waitUtils.waitUntil(d -> !d.findElements(org.openqa.selenium.By.xpath("//div[contains(@class,'modal-content')][.//button[normalize-space()='Close'] or contains(.,'Preview only')]")).isEmpty());
	}

	@StepName("Close Assessment Preview Dialog")
	public void closeAssessmentPreview() {
		java.util.List<WebElement> closeBtns = driver.findElements(org.openqa.selenium.By.xpath("//div[contains(@class,'modal-content')]//button[normalize-space()='Close'] | //button[@aria-label='Close']"));
		if (!closeBtns.isEmpty()) {
			waitUtils.clickUsingJS(closeBtns.get(0));
		}
	}
}
