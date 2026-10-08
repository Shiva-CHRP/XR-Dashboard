package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AssignCurriculums extends AbstractComponent {

	public AssignCurriculums(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Assign Curriculums']] | //button[.//span[normalize-space()='Assign Curriculums']]")
	private WebElement assignCurriculumsButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Curriculums') or contains(.,'Curriculum Assignment')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@StepName("Click Assign Curriculums from Sidebar")
	public void clickAssignCurriculums() {
		navigateToClientRoute(assignCurriculumsButton, "curriculum-assignment");
	}

	@StepName("Verify Assign Curriculums Page is Loaded")
	public boolean isAssignCurriculumsLoaded() {
		return waitUtils.waitForUrlContains("curriculum-assignment") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Assign Curriculums Page is Loaded")
	public boolean isAssignCurriculumsPageLoaded() {
		return isAssignCurriculumsLoaded();
	}

	@StepName("Search Trainers / Managers")
	public void searchTrainers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Managers")
	public void searchManagers(String query) {
		searchTrainers(query);
	}
}
