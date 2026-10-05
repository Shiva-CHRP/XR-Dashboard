package superadmin.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for the Super Admin Assessments section:
 * 1. Assessments Listing & KPI Stats (/super-admin/curriculum/assessments)
 * 2. Assessment Create & Edit Form (/super-admin/curriculum/assessments/create, /:id/edit)
 * 3. Assessment Detail View (/super-admin/curriculum/assessments/:id)
 * 4. Assigned Curricula Modal Dialog
 * 5. Delete Assessment Modal Dialog
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class Assessments extends AbstractComponent {

	public Assessments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & LISTING LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/curriculum/assessments']")
	private WebElement assessmentsLink;

	@FindBy(xpath = "//h1[contains(text(),'Assessments')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[contains(.,'Create Assessment')]")
	private WebElement createAssessmentButton;

	// KPI Cards
	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Total Assessments')] or .//p[contains(text(),'Total Assessments')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement totalAssessmentsKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Active')] or .//p[contains(text(),'Active')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement activeAssessmentsKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Inactive')] or .//p[contains(text(),'Inactive')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement inactiveAssessmentsKpi;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[.//span[contains(text(),'Avg. Questions')] or .//p[contains(text(),'Avg. Questions')]]//p[contains(@class,'font-bold') or contains(@class,'text-2xl')]")
	private WebElement avgQuestionsKpi;

	// FilterBar
	@FindBy(xpath = "//input[contains(@placeholder,'Search by assessment name')]")
	private WebElement searchInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Status')]]")
	private WebElement statusFilterSelect;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Assessments')]]")
	private WebElement linkageFilterSelect;

	@FindBy(xpath = "//select[.//option[contains(text(),'Newest first')]]")
	private WebElement sortFilterSelect;

	@FindBy(xpath = "//button[contains(.,'Clear filters') or contains(@title,'Clear all filters')]")
	private WebElement clearFiltersButton;

	@FindBy(xpath = "//p[contains(text(),'assessments') and contains(text(),'of')]")
	private WebElement assessmentsCountLabel;

	// Assessment Cards
	@FindBy(xpath = "//div[contains(@class,'grid grid-cols-1') and contains(@class,'lg:grid-cols-3')]/div")
	private List<WebElement> assessmentCards;

	// Delete Assessment Modal
	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Delete Assessment?')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Delete Assessment?')]")
	private WebElement deleteModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(.,'Delete') and (contains(@class,'danger') or contains(@class,'bg-titan-danger'))]")
	private WebElement confirmDeleteButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(text(),'Cancel')]")
	private WebElement cancelDeleteButton;

	// Assigned Curricula Modal
	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//h3[contains(text(),'Assigned Curriculums')] | //div[contains(@class,'TitanModal')]//div[contains(text(),'Assigned Curriculums')]")
	private WebElement assignedCurriculaModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal')]//button[contains(text(),'Close')]")
	private WebElement closeAssignedCurriculaModalButton;

	// =========================================================================
	// 2. ASSESSMENT FORM LOCATORS (Create & Edit)
	// =========================================================================

	@FindBy(xpath = "//h1[contains(text(),'Create Assessment') or contains(text(),'Edit Assessment')]")
	private WebElement formPageTitle;

	@FindBy(xpath = "//button[contains(.,'Back to Assessments')]")
	private WebElement backToAssessmentsButton;

	@FindBy(xpath = "//input[contains(@placeholder,'Workplace Safety Fundamentals')]")
	private WebElement formNameInput;

	@FindBy(xpath = "//button[@aria-pressed]")
	private WebElement statusToggleButton;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Describe this assessment')]")
	private WebElement formDescriptionTextarea;

	// Subtabs
	@FindBy(xpath = "//button[contains(.,'Available Questions')]")
	private WebElement availableQuestionsTab;

	@FindBy(xpath = "//button[contains(.,'Selected Questions')]")
	private WebElement selectedQuestionsTab;

	@FindBy(xpath = "//button[contains(.,'Clear all') and contains(@class,'text-titan-danger')]")
	private WebElement clearAllSelectedQuestionsButton;

	@FindBy(xpath = "//form//input[contains(@placeholder,'Search questions')]")
	private WebElement questionSearchInput;

	@FindBy(xpath = "//form//select[.//option[contains(text(),'All Topics')]]")
	private WebElement questionTopicFilterSelect;

	@FindBy(xpath = "//form//button[contains(text(),'Cancel')]")
	private WebElement formCancelButton;

	@FindBy(xpath = "//form//button[@type='submit' or contains(text(),'Create Assessment') or contains(text(),'Update Assessment')]")
	private WebElement formSubmitButton;

	// =========================================================================
	// 3. ASSESSMENT DETAIL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//h1")
	private WebElement detailName;

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//button[contains(.,'Edit')]")
	private WebElement editFromDetailButton;

	@FindBy(xpath = "//div[contains(@class,'max-w-6xl')]//button[contains(.,'Back to Assessments')]")
	private WebElement backToAssessmentsFromDetailButton;


	// =========================================================================
	// ACTION METHODS: Navigation & Listing Lifecycle
	// =========================================================================

	@StepName("Click on Assessments in Sidebar")
	public void clickAssessments() {
		waitUtils.waitForClickable(assessmentsLink);
		try {
			assessmentsLink.click();
		} catch (Exception e) {
			clickUsingJS(assessmentsLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Assessments Page is Loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Create Assessment Button")
	public void clickCreateAssessment() {
		waitUtils.waitForClickable(createAssessmentButton);
		createAssessmentButton.click();
		waitUtils.waitForVisibility(formPageTitle);
	}

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: KPI Stat Cards
	// =========================================================================

	@StepName("Get Total Assessments KPI Count")
	public int getTotalAssessmentsCount() {
		try {
			waitUtils.waitForVisibility(totalAssessmentsKpi);
			String txt = totalAssessmentsKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Active Assessments KPI Count")
	public int getActiveAssessmentsCount() {
		try {
			waitUtils.waitForVisibility(activeAssessmentsKpi);
			String txt = activeAssessmentsKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	@StepName("Get Inactive Assessments KPI Count")
	public int getInactiveAssessmentsCount() {
		try {
			waitUtils.waitForVisibility(inactiveAssessmentsKpi);
			String txt = inactiveAssessmentsKpi.getText().replaceAll("[^0-9]", "").trim();
			return txt.isEmpty() ? 0 : Integer.parseInt(txt);
		} catch (Exception e) {
			return 0;
		}
	}

	// =========================================================================
	// ACTION METHODS: Search & Filters
	// =========================================================================

	@StepName("Search Assessments: {0}")
	public void searchAssessments(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (query != null && !query.isEmpty()) {
			searchInput.sendKeys(query);
		}
		waitForLoadingToComplete();
	}

	@StepName("Filter Assessments by Status: {0}")
	public void filterByStatus(String status) {
		waitUtils.waitForVisibility(statusFilterSelect);
		selectByVisibleText(statusFilterSelect, status);
		waitForLoadingToComplete();
	}

	@StepName("Filter Assessments by Linkage: {0}")
	public void filterByLinkage(String linkage) {
		waitUtils.waitForVisibility(linkageFilterSelect);
		selectByVisibleText(linkageFilterSelect, linkage);
		waitForLoadingToComplete();
	}

	@StepName("Sort Assessments by: {0}")
	public void sortAssessments(String sortBy) {
		waitUtils.waitForVisibility(sortFilterSelect);
		selectByVisibleText(sortFilterSelect, sortBy);
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

	@StepName("Get Assessments Count Summary Text")
	public String getAssessmentsCountText() {
		try {
			waitUtils.waitForVisibility(assessmentsCountLabel);
			return assessmentsCountLabel.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	// =========================================================================
	// ACTION METHODS: Assessment Cards Listing
	// =========================================================================

	@StepName("Get Displayed Assessment Titles")
	public List<String> getDisplayedAssessmentTitles() {
		List<String> titles = new ArrayList<>();
		for (WebElement card : assessmentCards) {
			try {
				WebElement h3 = card.findElement(By.tagName("h3"));
				titles.add(h3.getText().trim());
			} catch (Exception ignored) {}
		}
		return titles;
	}

	@StepName("Check if Assessment is Present: {0}")
	public boolean isAssessmentPresent(String name) {
		return getDisplayedAssessmentTitles().stream()
				.anyMatch(t -> t.equalsIgnoreCase(name) || t.contains(name));
	}

	public WebElement findAssessmentCard(String name) {
		for (WebElement card : assessmentCards) {
			try {
				WebElement h3 = card.findElement(By.tagName("h3"));
				if (h3.getText().equalsIgnoreCase(name) || h3.getText().contains(name)) {
					return card;
				}
			} catch (Exception ignored) {}
		}
		return null;
	}

	@StepName("Open Assessment Detail: {0}")
	public void openAssessmentDetail(String name) {
		WebElement card = findAssessmentCard(name);
		if (card != null) {
			WebElement viewBtn = card.findElement(By.xpath(".//button[@aria-label='View Assessment' or @title='View Assessment']"));
			waitUtils.waitForClickable(viewBtn);
			viewBtn.click();
			waitUtils.waitForVisibility(detailName);
		} else {
			throw new RuntimeException("Assessment card not found: " + name);
		}
	}

	@StepName("Open Edit Assessment: {0}")
	public void openEditAssessment(String name) {
		WebElement card = findAssessmentCard(name);
		if (card != null) {
			WebElement editBtn = card.findElement(By.xpath(".//button[@aria-label='Edit Assessment' or @title='Edit Assessment']"));
			waitUtils.waitForClickable(editBtn);
			editBtn.click();
			waitUtils.waitForVisibility(formPageTitle);
		} else {
			throw new RuntimeException("Assessment card not found: " + name);
		}
	}

	@StepName("Open First Assessment Detail")
	public boolean openFirstAssessmentDetail() {
		try {
			clearAllFilters();
			waitForLoadingToComplete();
			By cardBy = By.xpath("//div[contains(@class,'grid grid-cols-1') and contains(@class,'lg:grid-cols-3')]/div[not(contains(.,'No assessments'))]");
			waitUtils.waitUntil(d -> !d.findElements(cardBy).isEmpty());
			List<WebElement> cards = driver.findElements(cardBy);
			if (cards.isEmpty()) return false;
			WebElement card = cards.get(0);
			List<WebElement> viewBtns = card.findElements(By.xpath(".//button[@aria-label='View Assessment' or @title='View Assessment']"));
			if (!viewBtns.isEmpty()) {
				waitUtils.waitForClickable(viewBtns.get(0));
				clickUsingJS(viewBtns.get(0));
			} else {
				waitUtils.waitForClickable(card);
				clickUsingJS(card);
			}
			waitUtils.waitForVisibility(detailName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open First Assessment for Edit")
	public boolean openFirstAssessmentEdit() {
		try {
			clearAllFilters();
			waitForLoadingToComplete();
			By cardBy = By.xpath("//div[contains(@class,'grid grid-cols-1') and contains(@class,'lg:grid-cols-3')]/div[not(contains(.,'No assessments'))]");
			waitUtils.waitUntil(d -> !d.findElements(cardBy).isEmpty());
			List<WebElement> cards = driver.findElements(cardBy);
			if (cards.isEmpty()) return false;
			WebElement editBtn = cards.get(0).findElement(By.xpath(".//button[@aria-label='Edit Assessment' or @title='Edit Assessment']"));
			waitUtils.waitForClickable(editBtn);
			clickUsingJS(editBtn);
			waitUtils.waitForVisibility(formPageTitle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Trigger Delete Assessment: {0}")
	public void triggerDeleteAssessment(String name) {
		WebElement card = findAssessmentCard(name);
		if (card != null) {
			WebElement deleteBtn = card.findElement(By.xpath(".//button[@aria-label='Delete Assessment' or @title='Delete Assessment']"));
			waitUtils.waitForClickable(deleteBtn);
			deleteBtn.click();
			waitUtils.waitForVisibility(deleteModalTitle);
		} else {
			throw new RuntimeException("Assessment card not found: " + name);
		}
	}

	@StepName("Confirm Delete Assessment")
	public void confirmDeleteAssessment() {
		waitUtils.waitForClickable(confirmDeleteButton);
		confirmDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Cancel Delete Assessment")
	public void cancelDeleteAssessment() {
		waitUtils.waitForClickable(cancelDeleteButton);
		cancelDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
	}

	@StepName("Open Assigned Curricula Modal for Assessment: {0}")
	public void openAssignedCurriculaModal(String name) {
		WebElement card = findAssessmentCard(name);
		if (card != null) {
			WebElement linkBtn = card.findElement(By.xpath(".//button[contains(text(),'curriculum') or contains(text(),'curriculums')]"));
			waitUtils.waitForClickable(linkBtn);
			linkBtn.click();
			waitUtils.waitForVisibility(assignedCurriculaModalTitle);
		} else {
			throw new RuntimeException("Assessment card not found: " + name);
		}
	}

	@StepName("Close Assigned Curricula Modal")
	public void closeAssignedCurriculaModal() {
		waitUtils.waitForClickable(closeAssignedCurriculaModalButton);
		closeAssignedCurriculaModalButton.click();
		waitUtils.waitForInvisibility(assignedCurriculaModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Assessment Form (Create / Edit)
	// =========================================================================

	@StepName("Check if Assessment Form is Loaded")
	public boolean isFormLoaded() {
		try {
			waitUtils.waitForVisibility(formPageTitle);
			return formPageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Assessment Form Page is Loaded")
	public boolean isFormPageLoaded() {
		return isFormLoaded();
	}

	@StepName("Click Back to Assessments from Form")
	public void clickBackToAssessments() {
		waitUtils.waitForClickable(backToAssessmentsButton);
		backToAssessmentsButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Enter Assessment Name: {0}")
	public void enterName(String name) {
		waitUtils.waitForVisibility(formNameInput);
		formNameInput.clear();
		formNameInput.sendKeys(name);
	}

	@StepName("Set Status Toggle: active={0}")
	public void setStatus(boolean active) {
		waitUtils.waitForVisibility(statusToggleButton);
		boolean currentActive = "true".equalsIgnoreCase(statusToggleButton.getAttribute("aria-pressed"));
		if (currentActive != active) {
			waitUtils.waitForClickable(statusToggleButton);
			statusToggleButton.click();
		}
	}

	@StepName("Enter Description: {0}")
	public void enterDescription(String desc) {
		if (desc != null) {
			waitUtils.waitForVisibility(formDescriptionTextarea);
			formDescriptionTextarea.clear();
			formDescriptionTextarea.sendKeys(desc);
		}
	}

	@StepName("Switch Question Subtab: {0}")
	public void switchQuestionTab(String tab) {
		if (tab.equalsIgnoreCase("available")) {
			waitUtils.waitForClickable(availableQuestionsTab);
			availableQuestionsTab.click();
		} else if (tab.equalsIgnoreCase("selected")) {
			waitUtils.waitForClickable(selectedQuestionsTab);
			selectedQuestionsTab.click();
		}
	}

	@StepName("Search Available Questions in Form: {0}")
	public void searchAvailableQuestions(String query) {
		switchQuestionTab("available");
		waitUtils.waitForVisibility(questionSearchInput);
		questionSearchInput.clear();
		questionSearchInput.sendKeys(query);
	}

	@StepName("Filter Available Questions by Topic in Form: {0}")
	public void filterAvailableQuestionsByTopic(String topic) {
		switchQuestionTab("available");
		waitUtils.waitForVisibility(questionTopicFilterSelect);
		selectByVisibleText(questionTopicFilterSelect, topic);
	}

	@StepName("Select Available Question: {0}")
	public void selectAvailableQuestion(String questionText) {
		switchQuestionTab("available");
		By qRow = By.xpath("//div[contains(@class,'max-h-[420px]')]//div[contains(@class,'rounded-lg')][.//p[contains(text(),'" + questionText + "')]]");
		WebElement row = waitUtils.waitForVisibility(qRow);
		waitUtils.waitForClickable(row);
		row.click();
	}

	@StepName("Remove Selected Question: {0}")
	public void removeSelectedQuestion(String questionText) {
		switchQuestionTab("selected");
		By removeBtnLocator = By.xpath("//div[contains(@class,'max-h-[420px]')]//div[contains(@class,'rounded-lg')][.//p[contains(text(),'" + questionText + "')]]//button[contains(@class,'hover:bg-titan-surface')]");
		WebElement removeBtn = waitUtils.waitForVisibility(removeBtnLocator);
		waitUtils.waitForClickable(removeBtn);
		removeBtn.click();
	}

	@StepName("Clear All Selected Questions")
	public void clearAllSelectedQuestions() {
		switchQuestionTab("selected");
		try {
			waitUtils.waitForClickable(clearAllSelectedQuestionsButton);
			clearAllSelectedQuestionsButton.click();
		} catch (Exception ignored) {}
	}

	@StepName("Submit Assessment Form")
	public void submitForm() {
		waitUtils.waitForClickable(formSubmitButton);
		formSubmitButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Cancel Assessment Form")
	public void cancelForm() {
		waitUtils.waitForClickable(formCancelButton);
		formCancelButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Complete Create Assessment Flow")
	public void createAssessment(String name, String desc, boolean active, List<String> questionTexts) {
		enterName(name);
		setStatus(active);
		if (desc != null) enterDescription(desc);
		if (questionTexts != null) {
			for (String qText : questionTexts) {
				selectAvailableQuestion(qText);
			}
		}
		submitForm();
	}

	// =========================================================================
	// ACTION METHODS: Assessment Detail Page
	// =========================================================================

	@StepName("Check if Assessment Detail Page is Loaded")
	public boolean isDetailPageLoaded() {
		try {
			waitUtils.waitForVisibility(detailName);
			return detailName.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Detail Assessment Name")
	public String getDetailName() {
		waitUtils.waitForVisibility(detailName);
		return detailName.getText().trim();
	}

	@StepName("Click Edit from Assessment Detail")
	public void clickEditFromDetail() {
		waitUtils.waitForClickable(editFromDetailButton);
		editFromDetailButton.click();
		waitUtils.waitForVisibility(formPageTitle);
	}

	@StepName("Click Back to Assessments from Detail")
	public void clickBackFromDetail() {
		waitUtils.waitForClickable(backToAssessmentsFromDetailButton);
		backToAssessmentsFromDetailButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}
}
