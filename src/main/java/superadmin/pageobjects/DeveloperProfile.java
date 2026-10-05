package superadmin.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * DeveloperProfile Page Object
 * 
 * Accurately models the VR Developer Profile at /developer/profile
 * matching src/pages/Developer/Profile/index.jsx.
 * 
 * Strictly utilizes waitUtils and AbstractComponent wait wrappers.
 */
public class DeveloperProfile extends AbstractComponent {

	public DeveloperProfile(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Navigation & Page Header
	// =========================================================================

	@FindBy(xpath = "//a[@href='/developer/profile']")
	private WebElement profileMenuLink;

	@FindBy(xpath = "//h1[text()='My Profile']")
	private WebElement pageTitle;

	// =========================================================================
	// 2. Personal Information Card
	// =========================================================================

	@FindBy(xpath = "//label[text()='Full Name']/following-sibling::p")
	private WebElement fullNameText;

	@FindBy(xpath = "//label[text()='Email']/following-sibling::p")
	private WebElement emailText;

	@FindBy(xpath = "//label[text()='Roles']/following-sibling::div//span")
	private List<WebElement> roleChips;

	@FindBy(xpath = "//label[text()='Total Submissions']/following-sibling::p")
	private WebElement totalSubmissionsText;

	// =========================================================================
	// 3. Security (Password Management) Card
	// =========================================================================

	@FindBy(xpath = "//p[contains(text(),'Password managed in Admin Portal')]")
	private WebElement multiRolePasswordNotice;

	@FindBy(xpath = "//input[@placeholder='Enter current password']")
	private WebElement currentPasswordInput;

	@FindBy(xpath = "//input[@placeholder='Enter new password']")
	private WebElement newPasswordInput;

	@FindBy(xpath = "//input[@placeholder='Re-enter new password']")
	private WebElement confirmPasswordInput;

	@FindBy(xpath = "//button[contains(.,'Update Password')]")
	private WebElement updatePasswordButton;

	// =========================================================================
	// ACTION METHODS: Navigation & Page State
	// =========================================================================

	@StepName("Click on Developer Profile in sidebar")
	public void clickDeveloperProfile() {
		waitUtils.waitForClickable(profileMenuLink);
		try {
			profileMenuLink.click();
		} catch (Exception e) {
			clickUsingJS(profileMenuLink);
		}
		waitUtils.waitUntil(ExpectedConditions.or(
				ExpectedConditions.visibilityOf(pageTitle),
				ExpectedConditions.urlContains("/developer/profile")
		));
	}

	@StepName("Verify Developer Profile page is loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// =========================================================================
	// ACTION METHODS: Personal Information Inspection
	// =========================================================================

	@StepName("Get Full Name on Developer Profile")
	public String getFullName() {
		waitUtils.waitForVisibility(fullNameText);
		return fullNameText.getText().trim();
	}

	@StepName("Get Email on Developer Profile")
	public String getEmail() {
		waitUtils.waitForVisibility(emailText);
		return emailText.getText().trim();
	}

	@StepName("Get Roles assigned to Developer")
	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		for (WebElement chip : roleChips) {
			roles.add(chip.getText().trim());
		}
		return roles;
	}

	@StepName("Get Total Submissions Count from Developer Profile")
	public int getTotalSubmissionsCount() {
		try {
			waitUtils.waitForVisibility(totalSubmissionsText);
			String text = totalSubmissionsText.getText().replaceAll("[^0-9]", "").trim();
			return text.isEmpty() ? 0 : Integer.parseInt(text);
		} catch (Exception e) {
			return 0;
		}
	}

	// =========================================================================
	// ACTION METHODS: Security / Password Management
	// =========================================================================

	@StepName("Check if Password Change form is available")
	public boolean isPasswordChangeAvailable() {
		try {
			return currentPasswordInput.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Change Password: current={0}")
	public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
		waitUtils.waitForVisibility(currentPasswordInput);
		currentPasswordInput.clear();
		currentPasswordInput.sendKeys(currentPassword);

		waitUtils.waitForVisibility(newPasswordInput);
		newPasswordInput.clear();
		newPasswordInput.sendKeys(newPassword);

		waitUtils.waitForVisibility(confirmPasswordInput);
		confirmPasswordInput.clear();
		confirmPasswordInput.sendKeys(confirmPassword);

		waitUtils.waitForClickable(updatePasswordButton);
		updatePasswordButton.click();
	}
}
