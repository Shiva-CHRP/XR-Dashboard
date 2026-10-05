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
 * Enterprise Page Object for the Super Admin Question Bank section:
 * 1. Question Bank Listing (/super-admin/curriculum/question-bank)
 * 2. Question Create & Edit Form (/super-admin/curriculum/question-bank/create, /:id/edit)
 * 3. Delete Confirmation Modal
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class QuestionBank extends AbstractComponent {

	public QuestionBank(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & LISTING LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/curriculum/question-bank']")
	private WebElement questionBankLink;

	@FindBy(xpath = "//h1[contains(text(),'Question Bank')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//button[@title='Refresh question bank' or (contains(.,'Refresh') and not(contains(.,'Revert')))]")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Create Question')]")
	private WebElement createQuestionButton;

	// FilterBar
	@FindBy(xpath = "//input[contains(@placeholder,'Search by question text')]")
	private WebElement searchInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'All Topics')]]")
	private WebElement topicFilterSelect;

	@FindBy(xpath = "//button[contains(.,'Clear Filters') or contains(@title,'Clear all filters')]")
	private WebElement clearFiltersButton;

	@FindBy(xpath = "//p[contains(text(),'questions') and contains(text(),'of')]")
	private WebElement questionsCountLabel;

	@FindBy(xpath = "//button[contains(.,'Expand all') or contains(.,'Compress all')]")
	private WebElement toggleExpandAllButton;

	// Question Cards
	@FindBy(xpath = "//div[contains(@class,'space-y-6')]//div[contains(@class,'flex flex-col gap-2.5')]/div")
	private List<WebElement> questionCards;

	// Delete Question Modal
	@FindBy(xpath = "//div[@role='dialog']//h3[contains(text(),'Delete Question?')] | //h3[contains(text(),'Delete Question?')]")
	private WebElement deleteModalTitle;

	@FindBy(xpath = "//div[@role='dialog']//button[contains(.,'Delete') and (contains(@class,'danger') or contains(@class,'bg-titan-danger'))]")
	private WebElement confirmDeleteButton;

	@FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Cancel'] | //button[normalize-space()='Cancel']")
	private WebElement cancelDeleteButton;

	// =========================================================================
	// 2. QUESTION FORM LOCATORS (Create & Edit)
	// =========================================================================

	@FindBy(xpath = "//h1[contains(text(),'Create Question') or contains(text(),'Edit Question')]")
	private WebElement formPageTitle;

	@FindBy(xpath = "//button[contains(.,'Back to Question Bank')]")
	private WebElement backToQuestionBankButton;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Enter the question')]")
	private WebElement questionTextInput;

	@FindBy(xpath = "//select[.//option[contains(text(),'Select a topic')]]")
	private WebElement formTopicSelect;

	@FindBy(xpath = "//textarea[contains(@placeholder,'Explanation text')]")
	private WebElement explanationTextInput;

	@FindBy(xpath = "//form//button[contains(text(),'Cancel')]")
	private WebElement formCancelButton;

	@FindBy(xpath = "//form//button[@type='submit' or contains(text(),'Create Question') or contains(text(),'Update Question')]")
	private WebElement formSubmitButton;


	// =========================================================================
	// ACTION METHODS: Navigation & Listing Lifecycle
	// =========================================================================

	@StepName("Click on Question Bank in Sidebar")
	public void clickQuestionBank() {
		waitUtils.waitForClickable(questionBankLink);
		try {
			questionBankLink.click();
		} catch (Exception e) {
			clickUsingJS(questionBankLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Question Bank Page is Loaded")
	public boolean isPageLoaded() {
		try {
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

	@StepName("Click Create Question Button")
	public void clickCreateQuestion() {
		waitUtils.waitForClickable(createQuestionButton);
		createQuestionButton.click();
		waitUtils.waitForVisibility(formPageTitle);
	}

	public void waitForLoadingToComplete() {
		try {
			By loader = By.xpath("//div[contains(@class,'animate-spin') or contains(@class,'PageLoader')]");
			waitUtils.waitForInvisibility(loader);
		} catch (Exception ignored) {}
	}

	// =========================================================================
	// ACTION METHODS: Search & Filters
	// =========================================================================

	@StepName("Search Questions: {0}")
	public void searchQuestions(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
		if (query != null && !query.isEmpty()) {
			searchInput.sendKeys(query, org.openqa.selenium.Keys.ENTER);
		}
		waitForLoadingToComplete();
	}

	@StepName("Filter Questions by Topic: {0}")
	public void filterByTopic(String topicName) {
		waitUtils.waitForVisibility(topicFilterSelect);
		selectByVisibleText(topicFilterSelect, topicName);
		waitForLoadingToComplete();
	}

	@StepName("Clear All Filters")
	public void clearAllFilters() {
		try {
			if (clearFiltersButton.isDisplayed()) {
				waitUtils.waitForClickable(clearFiltersButton);
				clearFiltersButton.click();
				waitForLoadingToComplete();
			}
		} catch (Exception e) {
			try {
				searchInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE, org.openqa.selenium.Keys.ENTER);
				waitForLoadingToComplete();
			} catch (Exception ignored) {}
		}
	}

	@StepName("Get Questions Count Summary Text")
	public String getQuestionsCountText() {
		try {
			waitUtils.waitForVisibility(questionsCountLabel);
			return questionsCountLabel.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Toggle Expand / Compress All Questions")
	public void toggleExpandAll() {
		waitUtils.waitForClickable(toggleExpandAllButton);
		toggleExpandAllButton.click();
	}

	// =========================================================================
	// ACTION METHODS: Question Cards Listing
	// =========================================================================

	@StepName("Get Displayed Question Texts")
	public List<String> getDisplayedQuestionTexts() {
		List<String> texts = new ArrayList<>();
		By qLocator = By.xpath("//div[contains(@class,'space-y-6')]//div[contains(@class,'flex flex-col gap-2.5')]//p[contains(@class,'text-sm')]");
		List<WebElement> els = driver.findElements(qLocator);
		for (WebElement el : els) {
			try {
				texts.add(el.getText().trim());
			} catch (Exception ignored) {}
		}
		return texts;
	}

	@StepName("Check if Question with Text is Present: {0}")
	public boolean isQuestionPresent(String questionText) {
		return getDisplayedQuestionTexts().stream()
				.anyMatch(t -> t.equalsIgnoreCase(questionText) || t.contains(questionText));
	}

	public WebElement findQuestionCard(String questionText) {
		By cardLocator = By.xpath("//div[contains(@class,'space-y-6')]//div[contains(@class,'flex flex-col gap-2.5')]/div[.//p[contains(text(),'" + questionText + "')]]");
		return waitUtils.waitForVisibility(cardLocator);
	}

	@StepName("Expand / Collapse Question Card: {0}")
	public void toggleQuestionCard(String questionText) {
		WebElement card = findQuestionCard(questionText);
		WebElement clickArea = card.findElement(By.xpath(".//div[@role='button']"));
		waitUtils.waitForClickable(clickArea);
		clickArea.click();
	}

	@StepName("Get Correct Answer Pill for Question: {0}")
	public String getCorrectAnswerBadge(String questionText) {
		WebElement card = findQuestionCard(questionText);
		WebElement badge = card.findElement(By.xpath(".//span[contains(@title,'Correct answer')]"));
		return badge.getText().trim();
	}

	@StepName("Click Edit Question: {0}")
	public void clickEditQuestion(String questionText) {
		WebElement card = findQuestionCard(questionText);
		WebElement editBtn = card.findElement(By.xpath(".//button[@aria-label='Edit' or @title='Edit']"));
		waitUtils.waitForClickable(editBtn);
		editBtn.click();
		waitUtils.waitForVisibility(formPageTitle);
	}

	@StepName("Click Edit First Question")
	public boolean clickEditFirstQuestion() {
		try {
			clearAllFilters();
			waitForLoadingToComplete();
			By editBy = By.xpath("//button[@aria-label='Edit' or @title='Edit']");
			waitUtils.waitUntil(d -> !d.findElements(editBy).isEmpty());
			List<WebElement> editBtns = driver.findElements(editBy);
			if (editBtns.isEmpty()) return false;
			WebElement editBtn = editBtns.get(0);
			waitUtils.waitForClickable(editBtn);
			clickUsingJS(editBtn);
			waitUtils.waitForVisibility(formPageTitle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Check if Question Form Page is Loaded")
	public boolean isFormPageLoaded() {
		try {
			waitUtils.waitForVisibility(formPageTitle);
			return formPageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Expand First Question")
	public boolean expandFirstQuestion() {
		try {
			clearAllFilters();
			waitForLoadingToComplete();
			By cardBy = By.xpath("//div[contains(@class,'space-y-6')]//div[contains(@class,'flex flex-col gap-2.5')]/div");
			waitUtils.waitUntil(d -> !d.findElements(cardBy).isEmpty());
			List<WebElement> cards = driver.findElements(cardBy);
			if (cards.isEmpty()) return false;
			WebElement clickArea = cards.get(0).findElement(By.xpath(".//div[@role='button']"));
			waitUtils.waitForClickable(clickArea);
			clickUsingJS(clickArea);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Trigger Delete Question: {0}")
	public void triggerDeleteQuestion(String questionText) {
		WebElement card = findQuestionCard(questionText);
		WebElement deleteBtn = card.findElement(By.xpath(".//button[@aria-label='Delete' or @title='Delete']"));
		waitUtils.waitForClickable(deleteBtn);
		deleteBtn.click();
		waitUtils.waitForVisibility(deleteModalTitle);
	}

	@StepName("Confirm Delete Question")
	public void confirmDeleteQuestion() {
		waitUtils.waitForClickable(confirmDeleteButton);
		confirmDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
		waitForLoadingToComplete();
	}

	@StepName("Cancel Delete Question")
	public void cancelDeleteQuestion() {
		waitUtils.waitForClickable(cancelDeleteButton);
		cancelDeleteButton.click();
		waitUtils.waitForInvisibility(deleteModalTitle);
	}

	// =========================================================================
	// ACTION METHODS: Question Form (Create / Edit)
	// =========================================================================

	@StepName("Check if Question Form is Loaded")
	public boolean isFormLoaded() {
		try {
			waitUtils.waitForVisibility(formPageTitle);
			return formPageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Click Back to Question Bank from Form")
	public void clickBackToQuestionBank() {
		waitUtils.waitForClickable(backToQuestionBankButton);
		backToQuestionBankButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Enter Question Text: {0}")
	public void enterQuestionText(String text) {
		waitUtils.waitForVisibility(questionTextInput);
		questionTextInput.clear();
		questionTextInput.sendKeys(text);
	}

	@StepName("Select Topic in Form: {0}")
	public void selectTopic(String topicName) {
		waitUtils.waitForVisibility(formTopicSelect);
		selectByVisibleText(formTopicSelect, topicName);
	}

	@StepName("Set Option {0} Text: {1}")
	public void setOptionText(String label, String optionText) {
		By inputLocator = By.xpath("//input[contains(@placeholder,'Option " + label + " text')]");
		WebElement input = waitUtils.waitForVisibility(inputLocator);
		input.clear();
		input.sendKeys(optionText);
	}

	@StepName("Select Correct Answer Radio: {0}")
	public void selectCorrectAnswer(String label) {
		By radioLocator = By.xpath("//input[@type='radio' and @value='" + label + "']");
		WebElement radio = driver.findElement(radioLocator);
		if (!radio.isSelected()) {
			try {
				radio.click();
			} catch (Exception e) {
				clickUsingJS(radio);
			}
		}
	}

	@StepName("Enter Explanation: {0}")
	public void enterExplanation(String explanation) {
		if (explanation != null) {
			waitUtils.waitForVisibility(explanationTextInput);
			explanationTextInput.clear();
			explanationTextInput.sendKeys(explanation);
		}
	}

	@StepName("Submit Question Form")
	public void submitForm() {
		waitUtils.waitForClickable(formSubmitButton);
		formSubmitButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Cancel Question Form")
	public void cancelForm() {
		waitUtils.waitForClickable(formCancelButton);
		formCancelButton.click();
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Complete Create Question Flow")
	public void createQuestion(String text, String topic, String optA, String optB, String optC, String optD, String correctLabel, String explanation) {
		enterQuestionText(text);
		selectTopic(topic);
		setOptionText("A", optA);
		setOptionText("B", optB);
		setOptionText("C", optC);
		setOptionText("D", optD);
		selectCorrectAnswer(correctLabel);
		if (explanation != null) {
			enterExplanation(explanation);
		}
		submitForm();
	}
}
