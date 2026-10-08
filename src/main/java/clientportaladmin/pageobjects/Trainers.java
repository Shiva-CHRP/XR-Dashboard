package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Trainers extends AbstractComponent {

	public Trainers(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Trainers']] | //button[.//span[normalize-space()='Trainers']]")
	private WebElement trainersNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Trainers'] | //header//h1[contains(.,'Trainer')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search trainers') or contains(@placeholder,'Search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> trainerRows;

	@FindBy(xpath = "//table//tbody//tr[1]//button[.//svg or contains(.,'View')]")
	private WebElement firstRowViewButton;

	@FindBy(xpath = "//*[@role='dialog']//*[contains(text(),'Trainer') or contains(text(),'User Details')]")
	private WebElement trainerDetailsModalTitle;

	@FindBy(xpath = "//*[@role='dialog']//button[.//svg or @aria-label='Close']")
	private WebElement closeTrainerDetailsModalButton;

	@StepName("Click Trainers from Sidebar")
	public void clickTrainers() {
		navigateToClientRoute(trainersNavButton, "trainers");
	}

	@StepName("Verify Trainers Page is Loaded")
	public boolean isTrainersPageLoaded() {
		return waitUtils.waitForUrlContains("trainers") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Search Trainers")
	public void searchTrainers(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Trainer Rows Count")
	public int getTrainerRowsCount() {
		return trainerRows.size();
	}

	@StepName("Open First Trainer Details Modal")
	public void openFirstTrainerDetails() {
		waitUtils.waitForClickable(firstRowViewButton);
		firstRowViewButton.click();
	}

	@StepName("Verify Trainer Details Modal is Displayed")
	public boolean isTrainerDetailsModalDisplayed() {
		waitUtils.waitForVisibility(trainerDetailsModalTitle);
		return trainerDetailsModalTitle.isDisplayed();
	}

	@StepName("Close Trainer Details Modal")
	public void closeTrainerDetailsModal() {
		waitUtils.waitForClickable(closeTrainerDetailsModalButton);
		closeTrainerDetailsModalButton.click();
	}
}
