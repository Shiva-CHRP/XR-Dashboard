package superadmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Super Admin Settings Page Object (/super-admin/settings).
 * 
 * Accurately models the comprehensive Super Admin Settings screen from
 * src/pages/Admin/Settings/index.jsx:
 * 1. Brand Assets: Logo & Favicon Card
 * 2. Appearance Card (Dark/Light mode, Primary Color swatches & presets)
 * 3. Sidebar Style Card (Interactive interaction pattern selection)
 * 4. Navigation Colors Card (Theme Presets, Sidebar Bg, Top Nav Bg, Live Preview)
 * 5. Notifications Card (Email, Push, Weekly Reports toggles)
 * 6. Security Card (Session Timeout)
 * 7. General Card (Platform Name, Support Email, Session Timeout auto-save on blur)
 * 
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Exclusively utilizes protected waitUtils (Zero ad-hoc WebDriverWait)
 * - Toast notification validations inherit toastUtils
 */
public class Settings extends AbstractComponent {

	public Settings(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. Header & Navigation Locators
	// =========================================================================

	@FindBy(xpath = "//h1[normalize-space()='Settings']")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//a[@href='/super-admin/settings'] | //button[normalize-space()='Settings']")
	private WebElement settingsMenuLink;

	// User pill at bottom of sidebar that triggers the user popover
	@FindBy(xpath = "//aside//button[.//div[contains(@class,'rounded-full') and contains(@class,'bg-titan-primary')]] | //aside//button[contains(@class,'group') and .//p]")
	private WebElement userProfilePill;

	// =========================================================================
	// 2. Section Card Headings
	// =========================================================================

	@FindBy(xpath = "//h2[contains(.,'Logo & Favicon') or contains(.,'Brand Assets') or contains(.,'Logo')]")
	private WebElement brandAssetsCardHeading;

	@FindBy(xpath = "//h2[normalize-space()='Appearance']")
	private WebElement appearanceCardHeading;

	@FindBy(xpath = "//h2[contains(.,'Sidebar Style')]")
	private WebElement sidebarStyleCardHeading;

	@FindBy(xpath = "//h2[contains(.,'Navigation Colors')]")
	private WebElement navigationColorsCardHeading;

	@FindBy(xpath = "//h2[normalize-space()='Notifications']")
	private WebElement notificationsCardHeading;

	@FindBy(xpath = "//h2[contains(.,'Security')]")
	private WebElement securityCardHeading;

	@FindBy(xpath = "//h2[normalize-space()='General']")
	private WebElement generalCardHeading;

	// =========================================================================
	// 3. Interactive Controls & Form Inputs
	// =========================================================================

	@FindBy(xpath = "//input[@placeholder='SuperAdmin']")
	private WebElement platformNameInput;

	@FindBy(xpath = "//input[@placeholder='support@example.com']")
	private WebElement supportEmailInput;

	@FindBy(xpath = "//input[@type='number']")
	private WebElement sessionTimeoutInput;

	@FindBy(xpath = "//h2[normalize-space()='Notifications']/ancestor::div[contains(@class,'TitanCard') or contains(@class,'rounded')]//input[@type='checkbox']")
	private List<WebElement> notificationCheckboxes;

	// =========================================================================
	// ACTION METHODS: Navigation & Verification
	// =========================================================================

	@StepName("Navigate to Super Admin Settings Page")
	public void navigateToSettings() {
		try {
			// Try clicking user profile pill to open popover if settingsMenuLink not directly visible
			List<WebElement> directLinks = driver.findElements(By.xpath("//a[@href='/super-admin/settings']"));
			if (!directLinks.isEmpty() && directLinks.get(0).isDisplayed()) {
				waitUtils.clickUsingJS(directLinks.get(0));
			} else if (userProfilePill != null && userProfilePill.isDisplayed()) {
				waitUtils.clickUsingJS(userProfilePill);
				By popoverBy = By.xpath("//a[@href='/super-admin/settings'] | //button[normalize-space()='Settings']");
				waitUtils.waitForClickable(popoverBy);
				WebElement popoverLink = driver.findElement(popoverBy);
				waitUtils.clickUsingJS(popoverLink);
			} else {
				driver.get(driver.getCurrentUrl().split("/super-admin")[0] + "/super-admin/settings");
			}
		} catch (Exception e) {
			driver.get(driver.getCurrentUrl().split("/super-admin")[0] + "/super-admin/settings");
		}
		waitUtils.waitForUrlContains("/super-admin/settings");
	}

	@StepName("Verify Settings Page is Loaded")
	public boolean isSettingsPageLoaded() {
		return waitUtils.waitForUrlContains("/super-admin/settings") 
				|| (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Verify Brand Assets Card is Present")
	public boolean isBrandAssetsCardPresent() {
		return !driver.findElements(By.xpath("//h2[contains(.,'Logo & Favicon') or contains(.,'Brand Assets') or contains(.,'Logo')]")).isEmpty();
	}

	@StepName("Verify Appearance Card is Present")
	public boolean isAppearanceCardPresent() {
		return !driver.findElements(By.xpath("//h2[normalize-space()='Appearance']")).isEmpty();
	}

	@StepName("Verify Sidebar Style Card is Present")
	public boolean isSidebarStyleCardPresent() {
		return !driver.findElements(By.xpath("//h2[contains(.,'Sidebar Style')]")).isEmpty();
	}

	@StepName("Verify Navigation Colors Card is Present")
	public boolean isNavigationColorsCardPresent() {
		return !driver.findElements(By.xpath("//h2[contains(.,'Navigation Colors')]")).isEmpty();
	}

	@StepName("Verify Notifications Card is Present")
	public boolean isNotificationsCardPresent() {
		return !driver.findElements(By.xpath("//h2[normalize-space()='Notifications']")).isEmpty();
	}

	@StepName("Verify Security Card is Present")
	public boolean isSecurityCardPresent() {
		return !driver.findElements(By.xpath("//h2[contains(.,'Security')]")).isEmpty();
	}

	@StepName("Verify General Card is Present")
	public boolean isGeneralCardPresent() {
		return !driver.findElements(By.xpath("//h2[normalize-space()='General']")).isEmpty();
	}

	@StepName("Update Platform Name")
	public void updatePlatformName(String name) {
		waitUtils.waitForVisibility(platformNameInput);
		platformNameInput.clear();
		platformNameInput.sendKeys(name);
	}

	@StepName("Update Support Email")
	public void updateSupportEmail(String email) {
		waitUtils.waitForVisibility(supportEmailInput);
		supportEmailInput.clear();
		supportEmailInput.sendKeys(email);
	}
}
