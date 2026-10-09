package superadmin.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for the Super Admin Curriculum section:
 * 1. Curriculum Catalogue Listing (/super-admin/curriculum/catalogue)
 * 2. Curriculum Create & Edit Form (/super-admin/curriculum/catalogue/create, /:id/edit)
 * 3. Curriculum Detail View (/super-admin/curriculum/catalogue/:id)
 * 4. Manage Lessons & Version Management (/super-admin/curriculum/catalogue/:id/lessons)
 * 5. Manage Access Modal Dialog
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class CurriculumCatalogue extends AbstractComponent {

	public CurriculumCatalogue(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & CATALOGUE LISTING LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/curriculum/catalogue']")
	private WebElement curriculumCatalogueLink;

	@FindBy(xpath = "//h1[contains(text(),'Curriculum Catalogue')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[@title='Refresh curriculum list' or (contains(.,'Refresh') and not(contains(.,'Revert')))]")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Create Curriculum')]")
	private WebElement createCurriculumButton;

	// Search & Filters
	@FindBy(xpath = "//input[contains(@placeholder,'Search curriculums')]")
	private WebElement searchInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Categories')]]")
	private WebElement categoryFilterSelect;

	@FindBy(xpath = "//select[.//option[contains(text(),'Free & Paid')]]")
	private WebElement pricingFilterSelect;

	@FindBy(xpath = "//button[contains(.,'Clear Filters') or contains(@title,'Clear all filters')]")
	private WebElement clearFiltersButton;

	// View Toggle
	@FindBy(xpath = "//button[@aria-label='Grid view' or @title='Grid view']")
	private WebElement gridViewButton;

	@FindBy(xpath = "//button[@aria-label='List view' or @title='List view']")
	private WebElement listViewButton;

	@FindBy(xpath = "//span[contains(text(),'curriculum') or contains(text(),'curriculums')]")
	private WebElement curriculumCountLabel;

	// Grid Cards & Table Rows
	@FindBy(xpath = "//div[contains(@class,'group relative flex flex-col rounded-2xl border bg-titan-card')]")
	private List<WebElement> curriculumCards;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> curriculumTableRows;

	// Delete Curriculum Modal
	@FindBy(xpath = "//h3[contains(text(),'Delete Curriculum?')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Delete Curriculum?')]")
	private WebElement deleteModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Delete') and (contains(@class,'danger') or contains(@class,'bg-titan-danger'))]")
	private WebElement confirmDeleteButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(text(),'Cancel')]")
	private WebElement cancelDeleteButton;

	// =========================================================================
	// 2. MANAGE ACCESS MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Manage Access')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Manage Access')]")
	private WebElement accessModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//input[contains(@placeholder,'Search organisations')]")
	private WebElement accessSearchInput;

	@FindBy(xpath = "//button[contains(.,'+ Add Organisation')]")
	private WebElement addOrgAccordionButton;

	@FindBy(xpath = "//button[contains(.,'Save Access') or contains(.,'Saving')]")
	private WebElement saveAccessButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Cancel')]")
	private WebElement cancelAccessButton;

	// =========================================================================
	// 3. CURRICULUM FORM LOCATORS (Create & Edit)
	// =========================================================================

	@FindBy(xpath = "//h1[contains(text(),'Create New Curriculum') or contains(text(),'Edit Curriculum')]")
	private WebElement formPageTitle;

	@FindBy(xpath = "//button[contains(.,'Back to Curriculums')]")
	private WebElement backToCurriculumsButton;

	@FindBy(xpath = "//input[contains(@placeholder,'Workplace Safety Essentials')]")
	private WebElement formTitleInput;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Describe what learners will take away')]")
	private WebElement formDescriptionTextarea;

	// Category Dropdown trigger (TitanDropdown)
	@FindBy(xpath = "//button[contains(@class,'TitanDropdown') or .//span[contains(text(),'Select a category') or contains(text(),'Category')]]")
	private WebElement formCategoryDropdownTrigger;

	@FindBy(xpath = "//div[contains(.,'Hrs')]/input")
	private WebElement durationHoursInput;

	@FindBy(xpath = "//div[contains(.,'Mins')]/input")
	private WebElement durationMinsInput;

	@FindBy(xpath = "//input[contains(@placeholder,'tag') or contains(@placeholder,'Type tag')]")
	private WebElement tagDraftInput;

	@FindBy(xpath = "//button[contains(.,'Add') and not(contains(.,'Curriculum')) and not(contains(.,'staging'))]")
	private WebElement addTagButton;

	@FindBy(xpath = "//button[@title='Clear all tags' or contains(.,'Clear All')]")
	private WebElement clearAllTagsButton;

	@FindBy(xpath = "//button[text()='Free' and not(contains(@class,'text-xs'))]")
	private WebElement pricingFreeButton;

	@FindBy(xpath = "//button[text()='Paid' and not(contains(@class,'text-xs'))]")
	private WebElement pricingPaidButton;

	@FindBy(xpath = "//input[contains(@placeholder,'2999')]")
	private WebElement priceInput;

	@FindBy(xpath = "//form//button[contains(text(),'Cancel')]")
	private WebElement formCancelButton;

	@FindBy(xpath = "//form//button[@type='submit' or contains(text(),'Create Curriculum') or contains(text(),'Update Curriculum')]")
	private WebElement formSubmitButton;

	// =========================================================================
	// 4. CURRICULUM DETAIL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//button[contains(.,'Back to Curriculum Catalogue')]")
	private WebElement backToCatalogueFromDetailButton;

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//button[contains(.,'Edit')]")
	private WebElement editFromDetailButton;

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//h1")
	private WebElement detailTitle;

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//button[contains(.,'Manage Lessons')]")
	private WebElement manageLessonsFromDetailButton;

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//button[contains(.,'Manage Access')]")
	private WebElement manageAccessFromDetailButton;

	// =========================================================================
	// 5. MANAGE LESSONS LOCATORS (/lessons)
	// =========================================================================

	@FindBy(xpath = "//h1[contains(text(),'Manage Lessons')]")
	private WebElement manageLessonsTitle;

	@FindBy(xpath = "//button[contains(.,'Back to')]")
	private WebElement backFromLessonsButton;

	@FindBy(xpath = "//button[contains(.,'Add Lesson') or contains(.,'Add First Lesson')]")
	private WebElement addLessonButton;

	// Add Lesson Modal tabs
	@FindBy(xpath = "//button[contains(.,'Upload Files')]")
	private WebElement tabUploadFiles;

	@FindBy(xpath = "//button[contains(.,'Add Link')]")
	private WebElement tabAddLink;

	@FindBy(xpath = "//button[contains(.,'Add Assessment')]")
	private WebElement tabAddAssessment;

	// Add Lesson Inputs
	@FindBy(xpath = "//input[@type='file' and not(contains(@class,'hidden')) or @type='file']")
	private WebElement fileUploadInput;

	@FindBy(xpath = "//input[contains(@placeholder,'Intro to Fire Safety') or contains(@placeholder,'Lesson Name')]")
	private WebElement linkLessonNameInput;

	@FindBy(xpath = "//input[contains(@placeholder,'https://')]")
	private WebElement linkLessonUrlInput;

	@FindBy(xpath = "//button[contains(.,'Add to staging')]")
	private WebElement addToStagingButton;

	@FindBy(xpath = "//select[.//option[contains(text(),'Select an assessment')]]")
	private WebElement assessmentSelect;

	@FindBy(xpath = "//button[contains(.,'Upload All')]")
	private WebElement uploadAllLessonsButton;

	// Version History Modal
	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Version history')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Version history')]")
	private WebElement versionHistoryModalTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Why are you reverting')]")
	private WebElement revertReasonInput;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Revert to v')]")
	private WebElement confirmRevertButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Upload as v') or (contains(.,'Save') and contains(.,'v'))]")
	private WebElement uploadNewVersionButton;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Updated slide') or contains(@placeholder,'notes')]")
	private WebElement versionNotesTextarea;

	// Delete Lesson Modal
	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Remove') and contains(@class,'danger')]")
	private WebElement confirmRemoveLessonButton;


	// =========================================================================
	// ACTION METHODS: Navigation & Page State
	// =========================================================================

	@StepName("Click on Curriculum Catalogue in Sidebar")
	public void clickCurriculumCatalogue() {
		waitUtils.waitForClickable(curriculumCatalogueLink);
		try {
			curriculumCatalogueLink.click();
		} catch (Exception e) {
			clickUsingJS(curriculumCatalogueLink);
		}
		waitForLoadingToComplete();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Curriculum Catalogue is Loaded")
	public boolean isPageLoaded() {
		try {
			waitForLoadingToComplete();
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
		waitForLoadingToComplete();
	}

	@StepName("Click Create Curriculum Button")
	public void clickCreateCurriculum() {
		waitUtils.waitForClickable(createCurriculumButton);
		createCurriculumButton.click();
		waitUtils.waitForVisibility(formPageTitle);
	}

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: Filters, Search & View Modes
	// =========================================================================

	@StepName("Search Curriculum: {0}")
	public void searchCurriculum(String text) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (text != null && !text.isEmpty()) {
			searchInput.sendKeys(text);
		}
		waitForLoadingToComplete();
	}

	@StepName("Filter by Category: {0}")
	public void filterByCategory(String category) {
		waitUtils.waitForVisibility(categoryFilterSelect);
		selectByVisibleText(categoryFilterSelect, category);
		waitForLoadingToComplete();
	}

	@StepName("Filter by Pricing: {0}")
	public void filterByPricing(String pricingType) {
		waitUtils.waitForVisibility(pricingFilterSelect);
		selectByVisibleText(pricingFilterSelect, pricingType);
		waitForLoadingToComplete();
	}

	@StepName("Clear All Filters")
	public void clearAllFilters() {
		try {
			waitUtils.waitForClickable(clearFiltersButton);
			clearFiltersButton.click();
			waitForLoadingToComplete();
		} catch (Exception e) {
			searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE, org.openqa.selenium.Keys.ENTER);
			waitForLoadingToComplete();
		}
	}

	@StepName("Switch to Grid View")
	public void clickGridView() {
		waitUtils.waitForClickable(gridViewButton);
		gridViewButton.click();
	}

	@StepName("Switch to List View")
	public void clickListView() {
		waitUtils.waitForClickable(listViewButton);
		listViewButton.click();
	}

	public boolean isGridViewActive() {
		return !curriculumCards.isEmpty() || gridViewButton.getAttribute("class").contains("bg-titan-primary");
	}

	@StepName("Get total curriculums count summary text")
	public String getCurriculumCountSummary() {
		try {
			waitUtils.waitForVisibility(curriculumCountLabel);
			return curriculumCountLabel.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Get Curriculums Count")
	public int getCurriculumsCount() {
		return curriculumCards.size();
	}

	@StepName("Is Empty Catalogue State Displayed")
	public boolean isEmptyCatalogueStateDisplayed() {
		return !driver.findElements(By.xpath("//div[contains(.,'No') and (contains(.,'found') or contains(.,'available'))] | //p[contains(.,'No') and contains(.,'curricul')]")).isEmpty()
				|| curriculumCards.isEmpty();
	}

	// =========================================================================
	// ACTION METHODS: Curriculum Listing (Cards & Rows)
	// =========================================================================

	@StepName("Get Displayed Curriculum Titles")
	public List<String> getDisplayedCurriculumTitles() {
		List<String> titles = new ArrayList<>();
		if (isGridViewActive()) {
			for (WebElement card : curriculumCards) {
				try {
					WebElement h3 = card.findElement(By.tagName("h3"));
					titles.add(h3.getText().trim());
				} catch (Exception ignored) {}
			}
		} else {
			for (WebElement row : curriculumTableRows) {
				try {
					WebElement titleEl = row.findElement(By.xpath(".//td[1]//p[1]"));
					titles.add(titleEl.getText().trim());
				} catch (Exception ignored) {}
			}
		}
		return titles;
	}

	@StepName("Check if Curriculum '{0}' is Present")
	public boolean isCurriculumPresent(String title) {
		return getDisplayedCurriculumTitles().stream()
				.anyMatch(t -> t.equalsIgnoreCase(title) || t.contains(title));
	}

	public WebElement findCardByTitle(String title) {
		for (WebElement card : curriculumCards) {
			try {
				WebElement h3 = card.findElement(By.tagName("h3"));
				if (h3.getText().equalsIgnoreCase(title) || h3.getText().contains(title)) {
					return card;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	public WebElement findTableRowByTitle(String title) {
		for (WebElement row : curriculumTableRows) {
			try {
				WebElement titleEl = row.findElement(By.xpath(".//td[1]//p[1]"));
				if (titleEl.getText().equalsIgnoreCase(title) || titleEl.getText().contains(title)) {
					return row;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	@StepName("Open Curriculum Details: {0}")
	public void openCurriculumDetail(String title) {
		if (isGridViewActive()) {
			WebElement card = findCardByTitle(title);
			if (card != null) {
				WebElement detailsBtn = card.findElement(By.xpath(".//button[contains(.,'Details')]"));
				waitUtils.waitForClickable(detailsBtn);
				detailsBtn.click();
			} else {
				throw new RuntimeException("Curriculum card not found: " + title);
			}
		} else {
			WebElement row = findTableRowByTitle(title);
			if (row != null) {
				waitUtils.waitForClickable(row);
				row.click();
			} else {
				throw new RuntimeException("Curriculum row not found: " + title);
			}
		}
		waitUtils.waitForVisibility(detailTitle);
	}

	@StepName("Open First Curriculum Detail")
	public boolean openFirstCurriculumDetail() {
		try {
			clearAllFilters();
			waitForLoadingToComplete();
			if (isGridViewActive()) {
				List<WebElement> cards = driver.findElements(By.xpath("//div[contains(@class,'group relative flex flex-col rounded-2xl border bg-titan-card')]"));
				if (cards.isEmpty()) return false;
				WebElement detailsBtn = cards.get(0).findElement(By.xpath(".//button[contains(.,'Details')]"));
				waitUtils.waitForClickable(detailsBtn);
				detailsBtn.click();
			} else {
				waitUtils.waitUntil(d -> {
					List<WebElement> r = d.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
					return !r.isEmpty() && r.get(0).isDisplayed();
				});
				List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[count(td) > 1 and not(.//td[@colSpan])]"));
				if (rows.isEmpty()) return false;
				waitUtils.waitForClickable(rows.get(0));
				rows.get(0).click();
			}
			waitUtils.waitForVisibility(detailTitle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Curriculum Form Page is Loaded")
	public boolean isFormPageLoaded() {
		try {
			waitUtils.waitForVisibility(formPageTitle);
			return formPageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Curriculum Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(detailTitle);
			return detailTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open Manage Access Modal for: {0}")
	public void openCurriculumAccess(String title) {
		if (isGridViewActive()) {
			WebElement card = findCardByTitle(title);
			if (card != null) {
				WebElement accessBtn = card.findElement(By.xpath(".//button[contains(.,'Access')]"));
				waitUtils.waitForClickable(accessBtn);
				accessBtn.click();
			} else {
				throw new RuntimeException("Curriculum card not found: " + title);
			}
		} else {
			WebElement row = findTableRowByTitle(title);
			if (row != null) {
				WebElement accessBtn = row.findElement(By.xpath(".//button[@title='Manage Access']"));
				waitUtils.waitForClickable(accessBtn);
				accessBtn.click();
			} else {
				throw new RuntimeException("Curriculum row not found: " + title);
			}
		}
		waitUtils.waitForVisibility(accessModalTitle);
	}

	@StepName("Open Manage Lessons for: {0}")
	public void openCurriculumLessons(String title) {
		if (isGridViewActive()) {
			WebElement card = findCardByTitle(title);
			if (card != null) {
				WebElement lessonsBtn = card.findElement(By.xpath(".//button[contains(.,'Lessons')]"));
				waitUtils.waitForClickable(lessonsBtn);
				lessonsBtn.click();
			} else {
				throw new RuntimeException("Curriculum card not found: " + title);
			}
		} else {
			WebElement row = findTableRowByTitle(title);
			if (row != null) {
				WebElement lessonsBtn = row.findElement(By.xpath(".//button[@title='Manage Lessons']"));
				waitUtils.waitForClickable(lessonsBtn);
				lessonsBtn.click();
			} else {
				throw new RuntimeException("Curriculum row not found: " + title);
			}
		}
		waitUtils.waitForVisibility(manageLessonsTitle);
	}

	@StepName("Open Edit Curriculum for: {0}")
	public void openCurriculumEdit(String title) {
		if (isGridViewActive()) {
			WebElement card = findCardByTitle(title);
			if (card != null) {
				WebElement menuBtn = card.findElement(By.xpath(".//button[@aria-label='Curriculum actions']"));
				waitUtils.waitForClickable(menuBtn);
				menuBtn.click();
				WebElement editOption = driver.findElement(By.xpath("//button[contains(.,'Edit')]"));
				waitUtils.waitForClickable(editOption);
				editOption.click();
			} else {
				throw new RuntimeException("Curriculum card not found: " + title);
			}
		} else {
			WebElement row = findTableRowByTitle(title);
			if (row != null) {
				WebElement editBtn = row.findElement(By.xpath(".//button[@title='Edit']"));
				waitUtils.waitForClickable(editBtn);
				editBtn.click();
			} else {
				throw new RuntimeException("Curriculum row not found: " + title);
			}
		}
		waitUtils.waitForVisibility(formPageTitle);
	}

	@StepName("Trigger Delete for Curriculum: {0}")
	public void triggerDeleteCurriculum(String title) {
		if (isGridViewActive()) {
			WebElement card = findCardByTitle(title);
			if (card != null) {
				WebElement menuBtn = card.findElement(By.xpath(".//button[@aria-label='Curriculum actions']"));
				waitUtils.waitForClickable(menuBtn);
				menuBtn.click();
				WebElement deleteOption = driver.findElement(By.xpath("//button[contains(.,'Delete')]"));
				waitUtils.waitForClickable(deleteOption);
				deleteOption.click();
			} else {
				throw new RuntimeException("Curriculum card not found: " + title);
			}
		} else {
			WebElement row = findTableRowByTitle(title);
			if (row != null) {
				WebElement deleteBtn = row.findElement(By.xpath(".//button[@title='Delete']"));
				waitUtils.waitForClickable(deleteBtn);
				deleteBtn.click();
			} else {
				throw new RuntimeException("Curriculum row not found: " + title);
			}
		}
		waitUtils.waitForVisibility(deleteModalTitle);
	}

	@StepName("Confirm Curriculum Deletion")
	public void confirmDeleteCurriculum() {
		waitUtils.waitForClickable(confirmDeleteButton);
		confirmDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Cancel Curriculum Deletion")
	public void cancelDeleteCurriculum() {
		waitUtils.waitForClickable(cancelDeleteButton);
		cancelDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Manage Access Modal
	// =========================================================================

	@StepName("Search organisation in access modal: {0}")
	public void searchOrgInAccessModal(String orgName) {
		waitUtils.waitForVisibility(accessSearchInput);
		accessSearchInput.clear();
		accessSearchInput.sendKeys(orgName);
	}

	@StepName("Add organisation access in modal: {0}")
	public void addOrgAccess(String orgName) {
		waitUtils.waitForVisibility(accessModalTitle);
		try {
			if (addOrgAccordionButton.isDisplayed()) {
				addOrgAccordionButton.click();
			}
		} catch (Exception ignored) {}

		searchOrgInAccessModal(orgName);

		By addBtnLocator = By.xpath("//div[contains(@class,'TitanModal')]//div[contains(@class,'rounded-lg')][.//p[contains(text(),'" + orgName + "')]]//button[contains(.,'+ Add')]");
		waitUtils.waitForClickable(addBtnLocator);
		driver.findElement(addBtnLocator).click();
	}

	@StepName("Remove organisation access in modal: {0}")
	public void removeOrgAccess(String orgName) {
		By removeBtnLocator = By.xpath("//div[contains(@class,'TitanModal')]//div[contains(@class,'rounded-lg')][.//p[contains(text(),'" + orgName + "')]]//button[contains(@class,'text-titan-muted')]");
		waitUtils.waitForClickable(removeBtnLocator);
		driver.findElement(removeBtnLocator).click();
	}

	@StepName("Save access changes in modal")
	public void saveAccessChanges() {
		waitUtils.waitForClickable(saveAccessButton);
		saveAccessButton.click();
		waitUtils.waitForInvisibility(accessModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Cancel access modal")
	public void cancelAccessModal() {
		waitUtils.waitForClickable(cancelAccessButton);
		cancelAccessButton.click();
		waitUtils.waitForInvisibility(accessModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Curriculum Create / Edit Form
	// =========================================================================

	@StepName("Enter Curriculum Title: {0}")
	public void enterTitle(String title) {
		waitUtils.waitForVisibility(formTitleInput);
		formTitleInput.clear();
		formTitleInput.sendKeys(title);
	}

	@StepName("Enter Curriculum Description")
	public void enterDescription(String desc) {
		waitUtils.waitForVisibility(formDescriptionTextarea);
		formDescriptionTextarea.clear();
		formDescriptionTextarea.sendKeys(desc);
	}

	@StepName("Select Category: {0}")
	public void selectCategory(String category) {
		waitUtils.waitForClickable(formCategoryDropdownTrigger);
		formCategoryDropdownTrigger.click();

		By optionLocator = By.xpath("//div[contains(@class,'portal') or contains(@class,'z-50') or @role='listbox' or contains(@class,'TitanDropdown')]//*[contains(text(),'" + category + "')]");
		waitUtils.waitForClickable(optionLocator);
		driver.findElement(optionLocator).click();
	}

	@StepName("Enter Duration: {0} hrs, {1} mins")
	public void enterDuration(String hours, String mins) {
		if (hours != null && !hours.isEmpty()) {
			waitUtils.waitForVisibility(durationHoursInput);
			durationHoursInput.clear();
			durationHoursInput.sendKeys(hours);
		}
		if (mins != null && !mins.isEmpty()) {
			waitUtils.waitForVisibility(durationMinsInput);
			durationMinsInput.clear();
			durationMinsInput.sendKeys(mins);
		}
	}

	@StepName("Add Tag: {0}")
	public void addTag(String tag) {
		waitUtils.waitForVisibility(tagDraftInput);
		tagDraftInput.sendKeys(tag);
		waitUtils.waitForClickable(addTagButton);
		addTagButton.click();
	}

	@StepName("Clear All Tags")
	public void clearAllTags() {
		try {
			waitUtils.waitForClickable(clearAllTagsButton);
			clearAllTagsButton.click();
		} catch (Exception ignored) {}
	}

	@StepName("Select Pricing: isPaid={0}, price={1}")
	public void setPricing(boolean isPaid, String price) {
		if (isPaid) {
			waitUtils.waitForClickable(pricingPaidButton);
			pricingPaidButton.click();
			if (price != null && !price.isEmpty()) {
				waitUtils.waitForVisibility(priceInput);
				priceInput.clear();
				priceInput.sendKeys(price);
			}
		} else {
			waitUtils.waitForClickable(pricingFreeButton);
			pricingFreeButton.click();
		}
	}

	@StepName("Submit Curriculum Form")
	public void submitForm() {
		waitUtils.waitForClickable(formSubmitButton);
		formSubmitButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Cancel Curriculum Form")
	public void cancelForm() {
		waitUtils.waitForClickable(formCancelButton);
		formCancelButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Complete Create Curriculum Flow")
	public void createCurriculum(String title, String desc, String category, String hours, String mins, List<String> tags, boolean isPaid, String price) {
		enterTitle(title);
		if (desc != null) enterDescription(desc);
		if (category != null) selectCategory(category);
		enterDuration(hours, mins);
		if (tags != null) {
			for (String tag : tags) {
				addTag(tag);
			}
		}
		setPricing(isPaid, price);
		submitForm();
	}

	// =========================================================================
	// ACTION METHODS: Curriculum Detail Page
	// =========================================================================

	@StepName("Get Detail Curriculum Title")
	public String getDetailTitle() {
		waitUtils.waitForVisibility(detailTitle);
		return detailTitle.getText().trim();
	}

	@StepName("Click Back to Catalogue from Detail")
	public void clickBackToCatalogueFromDetail() {
		waitUtils.waitForClickable(backToCatalogueFromDetailButton);
		backToCatalogueFromDetailButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Click Edit from Detail")
	public void clickEditFromDetail() {
		waitUtils.waitForClickable(editFromDetailButton);
		editFromDetailButton.click();
		waitUtils.waitForVisibility(formPageTitle);
	}

	@StepName("Click Manage Lessons from Detail")
	public void clickManageLessonsFromDetail() {
		waitUtils.waitForClickable(manageLessonsFromDetailButton);
		manageLessonsFromDetailButton.click();
		waitUtils.waitForVisibility(manageLessonsTitle);
	}

	@StepName("Click Manage Access from Detail")
	public void clickManageAccessFromDetail() {
		waitUtils.waitForClickable(manageAccessFromDetailButton);
		manageAccessFromDetailButton.click();
		waitUtils.waitForVisibility(accessModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Manage Lessons Screen
	// =========================================================================

	@StepName("Check if Manage Lessons page is loaded")
	public boolean isManageLessonsLoaded() {
		try {
			waitUtils.waitForVisibility(manageLessonsTitle);
			return manageLessonsTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Back from Lessons")
	public void clickBackFromLessons() {
		waitUtils.waitForClickable(backFromLessonsButton);
		backFromLessonsButton.click();
	}

	@StepName("Click Add Lesson Button")
	public void clickAddLesson() {
		waitUtils.waitForClickable(addLessonButton);
		addLessonButton.click();
	}

	@StepName("Switch Add Lesson Tab: {0}")
	public void switchAddLessonTab(String tab) {
		if (tab.equalsIgnoreCase("files")) {
			waitUtils.waitForClickable(tabUploadFiles);
			tabUploadFiles.click();
		} else if (tab.equalsIgnoreCase("link")) {
			waitUtils.waitForClickable(tabAddLink);
			tabAddLink.click();
		} else if (tab.equalsIgnoreCase("assessment")) {
			waitUtils.waitForClickable(tabAddAssessment);
			tabAddAssessment.click();
		}
	}

	@StepName("Select Content Type Pill: {0}")
	public void selectContentTypePill(String type) {
		By pill = By.xpath("//div[contains(@class,'TitanModal')]//button[contains(.,'" + type + "')]");
		waitUtils.waitForClickable(pill);
		driver.findElement(pill).click();
	}

	@StepName("Upload File for Lesson: {0}")
	public void uploadLessonFile(String filePath) {
		waitUtils.waitForVisibility(fileUploadInput);
		fileUploadInput.sendKeys(filePath);
	}

	@StepName("Stage External Link Lesson: name={0}, url={1}")
	public void stageLinkLesson(String name, String url) {
		switchAddLessonTab("link");
		waitUtils.waitForVisibility(linkLessonNameInput);
		linkLessonNameInput.clear();
		linkLessonNameInput.sendKeys(name);

		waitUtils.waitForVisibility(linkLessonUrlInput);
		linkLessonUrlInput.clear();
		linkLessonUrlInput.sendKeys(url);

		waitUtils.waitForClickable(addToStagingButton);
		addToStagingButton.click();
	}

	@StepName("Stage Assessment Lesson: {0}")
	public void stageAssessmentLesson(String assessmentName) {
		switchAddLessonTab("assessment");
		waitUtils.waitForVisibility(assessmentSelect);
		selectByVisibleText(assessmentSelect, assessmentName);

		waitUtils.waitForClickable(addToStagingButton);
		addToStagingButton.click();
	}

	@StepName("Click Upload All Staged Lessons")
	public void clickUploadAllLessons() {
		waitUtils.waitForClickable(uploadAllLessonsButton);
		uploadAllLessonsButton.click();
		waitForLoadingToComplete();
	}

	@StepName("Get Displayed Lesson Names")
	public List<String> getDisplayedLessonNames() {
		List<String> names = new ArrayList<>();
		By lessonRows = By.xpath("//div[contains(@class,'grid') and @draggable='true']");
		List<WebElement> rows = driver.findElements(lessonRows);
		for (WebElement row : rows) {
			try {
				WebElement nameEl = row.findElement(By.xpath(".//span[contains(@class,'truncate') and (contains(@class,'text-sm') or contains(@class,'font-medium'))]"));
				names.add(nameEl.getText().trim());
			} catch (Exception ignored) {}
		}
		return names;
	}

	@StepName("Open Version History for Lesson: {0}")
	public void openLessonVersionHistory(String lessonName) {
		By rowLocator = By.xpath("//div[contains(@class,'grid') and @draggable='true'][.//span[contains(text(),'" + lessonName + "')]]");
		WebElement row = waitUtils.waitForVisibility(rowLocator);
		WebElement versionBtn = row.findElement(By.xpath(".//button[contains(@title,'Version history') or contains(.,'v')]"));
		waitUtils.waitForClickable(versionBtn);
		versionBtn.click();
		waitUtils.waitForVisibility(versionHistoryModalTitle);
	}

	@StepName("Revert Lesson Version in Modal: version={0}, reason={1}")
	public void revertLessonVersion(String version, String reason) {
		By revertBtnLocator = By.xpath("//div[contains(@class,'TitanModal')]//div[contains(@class,'rounded-lg')][.//span[contains(text(),'v" + version + "')]]//button[contains(.,'Revert')]");
		waitUtils.waitForClickable(revertBtnLocator);
		driver.findElement(revertBtnLocator).click();

		waitUtils.waitForVisibility(revertReasonInput);
		revertReasonInput.clear();
		revertReasonInput.sendKeys(reason);

		waitUtils.waitForClickable(confirmRevertButton);
		confirmRevertButton.click();
		waitForLoadingToComplete();
	}

	@StepName("Upload New Lesson Version: filePath={0}, changeType={1}")
	public void uploadNewLessonVersion(String filePath, String changeType, String notes) {
		waitUtils.waitForVisibility(fileUploadInput);
		fileUploadInput.sendKeys(filePath);

		if (changeType != null) {
			By typeBtn = By.xpath("//div[contains(@class,'TitanModal')]//button[contains(.,'" + changeType.toUpperCase() + "')]");
			waitUtils.waitForClickable(typeBtn);
			driver.findElement(typeBtn).click();
		}

		if (notes != null) {
			waitUtils.waitForVisibility(versionNotesTextarea);
			versionNotesTextarea.clear();
			versionNotesTextarea.sendKeys(notes);
		}

		waitUtils.waitForClickable(uploadNewVersionButton);
		uploadNewVersionButton.click();
		waitForLoadingToComplete();
	}

	@StepName("Delete Lesson: {0}")
	public void deleteLesson(String lessonName) {
		By rowLocator = By.xpath("//div[contains(@class,'grid') and @draggable='true'][.//span[contains(text(),'" + lessonName + "')]]");
		WebElement row = waitUtils.waitForVisibility(rowLocator);
		WebElement deleteBtn = row.findElement(By.xpath(".//button[contains(@title,'Remove') or contains(@class,'danger')]"));
		waitUtils.waitForClickable(deleteBtn);
		deleteBtn.click();

		waitUtils.waitForClickable(confirmRemoveLessonButton);
		confirmRemoveLessonButton.click();
		waitForLoadingToComplete();
	}
}
