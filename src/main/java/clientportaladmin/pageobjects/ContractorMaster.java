package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class ContractorMaster extends AbstractComponent {

	public ContractorMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//svg[contains(@class,'lucide-handshake')]] | //button[.//svg[contains(@class,'lucide-handshake')]] | //button[.//span[normalize-space()='Contractor'] or .//span[normalize-space()='Contractor Master'] or .//span[normalize-space()='Partner'] or .//span[normalize-space()='Vendor']]")
	private WebElement contractorMasterNavButton;

	@FindBy(xpath = "//header//h1 | //h1[contains(.,'Master')] | //h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Add') or contains(.,'Create')]")
	private WebElement addContractorButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> contractorRows;

	@StepName("Click Contractor Master from Sidebar")
	public void clickContractorMaster() {
		navigateToClientRoute(contractorMasterNavButton, "partner-master");
	}

	@StepName("Verify Contractor Master Page is Loaded")
	public boolean isContractorMasterLoaded() {
		return waitUtils.waitForUrlContains("partner-master") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Contractor Master Page is Loaded")
	public boolean isContractorMasterPageLoaded() {
		return isContractorMasterLoaded();
	}

	@StepName("Search Contractor")
	public void searchContractor(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Search Master Records")
	public void searchMaster(String query) {
		searchContractor(query);
	}

	@StepName("Get Contractor Rows Count")
	public int getContractorRowsCount() {
		return contractorRows.size();
	}

	@StepName("Create Contractor Master Record")
	public ToastResponse createContractor(String name, String code, String email, String phone, String description) {
		By addBtnBy = By.xpath("//button[contains(normalize-space(),'Add') or contains(.,'Create')]");
		WebElement btn = waitUtils.waitForVisibility(addBtnBy);
		waitUtils.waitForClickable(btn);
		try {
			btn.click();
		} catch (Exception e) {
			clickUsingJS(btn);
		}
		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[@role='dialog']")).isEmpty(), 5);

		By nameBy = By.xpath("//div[@role='dialog']//label[contains(.,'Name') or contains(.,'Company')]/..//input | //div[@role='dialog']//input[1]");
		WebElement nameInput = waitUtils.waitForVisibility(nameBy);
		nameInput.clear();
		nameInput.sendKeys(name);

		if (code != null && !code.isEmpty()) {
			List<WebElement> codeInputs = driver.findElements(By.xpath("//div[@role='dialog']//label[contains(.,'Code')]/..//input"));
			if (!codeInputs.isEmpty()) {
				codeInputs.get(0).clear();
				codeInputs.get(0).sendKeys(code);
			}
		}

		if (email != null && !email.isEmpty()) {
			List<WebElement> emailInputs = driver.findElements(By.xpath("//div[@role='dialog']//label[contains(.,'Email')]/..//input"));
			if (!emailInputs.isEmpty()) {
				emailInputs.get(0).clear();
				emailInputs.get(0).sendKeys(email);
			}
		}

		if (phone != null && !phone.isEmpty()) {
			List<WebElement> phoneInputs = driver.findElements(By.xpath("//div[@role='dialog']//label[contains(.,'Phone')]/..//input"));
			if (!phoneInputs.isEmpty()) {
				phoneInputs.get(0).clear();
				phoneInputs.get(0).sendKeys(phone);
			}
		}

		if (description != null && !description.isEmpty()) {
			List<WebElement> descInputs = driver.findElements(By.xpath("//div[@role='dialog']//label[contains(.,'Description')]/..//input"));
			if (!descInputs.isEmpty()) {
				descInputs.get(0).clear();
				descInputs.get(0).sendKeys(description);
			}
		}

		By saveBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Add') or normalize-space()='Save']");
		WebElement saveBtn = waitUtils.waitForVisibility(saveBtnBy);
		waitUtils.waitForClickable(saveBtn);
		try {
			saveBtn.click();
		} catch (Exception e) {
			clickUsingJS(saveBtn);
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	@StepName("Create Master Record (Generic)")
	public ToastResponse createMaster(String name, String description) {
		return createContractor(name, "", "", "", description);
	}

	@StepName("Edit Contractor Master Record")
	public ToastResponse editContractor(String currentName, String newName, String newDescription) {
		return performEditMaster(currentName, newName, newDescription);
	}

	@StepName("Edit Master Record (Generic)")
	public ToastResponse editMaster(String currentName, String newName, String newDescription) {
		return editContractor(currentName, newName, newDescription);
	}

	@StepName("Toggle Contractor Status (Deactivate/Reactivate)")
	public ToastResponse toggleStatus(String name) {
		return performToggleMasterStatus(name);
	}

	@StepName("Delete Contractor Master Record")
	public ToastResponse deleteContractor(String name) {
		return performDeleteMaster(name);
	}
}