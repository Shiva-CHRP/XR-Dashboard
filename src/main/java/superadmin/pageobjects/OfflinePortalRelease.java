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
 * Enterprise Page Object for Super Admin Offline Portal Releases:
 * 1. Offline Portal Release Management (/super-admin/offline-releases)
 * 2. Header Actions (Deployment Environment Badge, Refresh, Upload Release Modal)
 * 3. Releases Table (Version, Description, Status, File Size, Upload Date)
 * 4. Release Actions (Activate, Download Link Generation, Delete Release)
 * 5. Full Release Notes Modal (Inspection, Copy to Clipboard)
 * 6. Upload Release Modal (SemVer Version, Suggestions, Notes, EXE Dropzone File Upload)
 *
 * Strict Architectural Invariants:
 * - Extends AbstractComponent
 * - Uses waitUtils exclusively (Zero new WebDriverWait)
 * - Inherits ToastUtils helpers (captureToast, waitForToastToDisappear)
 */
public class OfflinePortalRelease extends AbstractComponent {

	public OfflinePortalRelease(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =========================================================================
	// 1. SIDEBAR & HEADER LOCATORS
	// =========================================================================

	@FindBy(xpath = "//a[@href='/super-admin/offline-releases']")
	private WebElement offlineReleaseLink;

	@FindBy(xpath = "//h1[contains(text(),'Offline Portal Releases') or contains(text(),'Offline Portal APK Releases')]")
	private WebElement pageTitle;

	@FindBy(xpath = "//*[@id='release-tab-exe'] | //button[@role='tab' and contains(.,'EXE')]")
	private WebElement exeTab;

	@FindBy(xpath = "//*[@id='release-tab-apk'] | //button[@role='tab' and contains(.,'APK')]")
	private WebElement apkTab;

	@FindBy(xpath = "//span[contains(text(),'Deployment:')]")
	private WebElement deploymentEnvironmentBadge;

	@FindBy(xpath = "//button[contains(.,'Refresh')]")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(.,'Upload Release') or contains(.,'Upload APK Release')]")
	private WebElement uploadReleaseButton;

	// =========================================================================
	// 2. TABLE LOCATORS
	// =========================================================================

	@FindBy(xpath = "//table/tbody/tr[not(contains(@class,'empty')) and td[1]]")
	private List<WebElement> releaseTableRows;

	@FindBy(xpath = "//button[contains(.,'Next') or @aria-label='Next page']")
	private WebElement nextPageButton;

	@FindBy(xpath = "//button[contains(.,'Previous') or @aria-label='Previous page']")
	private WebElement prevPageButton;

	// =========================================================================
	// 3. UPLOAD RELEASE MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//h3[contains(text(),'Upload Offline Portal Release')]")
	private WebElement uploadModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//input[contains(@class,'font-mono') or @placeholder='2.0.0']")
	private WebElement versionInput;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//textarea")
	private WebElement descriptionTextarea;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//input[@type='file']")
	private WebElement fileInput;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//button[normalize-space()='Upload' or contains(.,'Upload')]")
	private WebElement modalUploadButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//button[normalize-space()='Cancel']")
	private WebElement modalCancelButton;

	// =========================================================================
	// 4. RELEASE NOTES MODAL LOCATORS
	// =========================================================================

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//h3[contains(text(),'Release notes')]")
	private WebElement releaseNotesModalTitle;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//p[contains(@class,'whitespace-pre-wrap')]")
	private WebElement releaseNotesContent;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//button[contains(.,'Copy') or contains(.,'Copied')]")
	private WebElement copyNotesButton;

	@FindBy(xpath = "//div[contains(@class,'TitanModal') or @role='dialog']//button[normalize-space()='Close']")
	private WebElement closeNotesButton;


	// =========================================================================
	// 5. ACTION METHODS: Navigation & Lifecycle
	// =========================================================================

	@StepName("Click on Offline Portal Release in Sidebar")
	public void clickOfflinePortalRelease() {
		waitUtils.waitForClickable(offlineReleaseLink);
		try {
			offlineReleaseLink.click();
		} catch (Exception e) {
			clickUsingJS(offlineReleaseLink);
		}
		waitUtils.waitForVisibility(pageTitle);
	}

	@StepName("Check if Offline Releases Page is Loaded")
	public boolean isPageLoaded() {
		try {
			waitUtils.waitForVisibility(pageTitle);
			return pageTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Get Page Title Text")
	public String getPageTitle() {
		waitUtils.waitForVisibility(pageTitle);
		return pageTitle.getText().trim();
	}

	@StepName("Get Deployment Environment Label")
	public String getDeploymentEnvironment() {
		try {
			waitUtils.waitForVisibility(deploymentEnvironmentBadge);
			return deploymentEnvironmentBadge.getText().replace("Deployment:", "").trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		waitUtils.waitForClickable(refreshButton);
		refreshButton.click();
	}


	// =========================================================================
	// 6. ACTION METHODS: Table Inspection
	// =========================================================================

	@StepName("Get Total Number of Displayed Releases")
	public int getReleasesCount() {
		return releaseTableRows.size();
	}

	private WebElement getRow(int rowIndex) {
		if (rowIndex < 1 || rowIndex > releaseTableRows.size()) {
			throw new IndexOutOfBoundsException("Row index " + rowIndex + " out of bounds for releases count " + releaseTableRows.size());
		}
		return releaseTableRows.get(rowIndex - 1);
	}

	@StepName("Get Release Version of Row {0}")
	public String getReleaseVersion(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement versionEl = row.findElement(By.xpath("./td[1]//span[contains(@class,'font-mono')] | ./td[1]"));
		return versionEl.getText().trim();
	}

	@StepName("Get Release Description of Row {0}")
	public String getReleaseDescription(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement descEl = row.findElement(By.xpath("./td[2]"));
		return descEl.getText().trim();
	}

	@StepName("Get Release Status of Row {0}")
	public String getReleaseStatus(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement statusEl = row.findElement(By.xpath("./td[3]//span[contains(@class,'rounded-full') or text()]"));
		return statusEl.getText().trim();
	}

	@StepName("Get Release File Size of Row {0}")
	public String getReleaseFileSize(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement sizeEl = row.findElement(By.xpath("./td[4]"));
		return sizeEl.getText().trim();
	}

	@StepName("Get Release Upload Date of Row {0}")
	public String getReleaseUploadDate(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement dateEl = row.findElement(By.xpath("./td[5]"));
		return dateEl.getText().trim();
	}


	// =========================================================================
	// 7. ACTION METHODS: Row Actions (Activate, Download, Delete, Notes)
	// =========================================================================

	@StepName("Activate Release at Row {0}")
	public void activateRelease(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement activateBtn = row.findElement(By.xpath(".//button[contains(.,'Activate')]"));
		waitUtils.waitForClickable(activateBtn);
		activateBtn.click();
	}

	@StepName("Download Release at Row {0}")
	public void downloadRelease(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement downloadBtn = row.findElement(By.xpath(".//button[.//svg[contains(@class,'lucide-download')]]"));
		waitUtils.waitForClickable(downloadBtn);
		downloadBtn.click();
	}

	@StepName("Delete Release at Row {0}")
	public void deleteRelease(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement deleteBtn = row.findElement(By.xpath(".//button[.//svg[contains(@class,'lucide-trash-2')]]"));
		waitUtils.waitForClickable(deleteBtn);
		deleteBtn.click();
		// Handle native confirm dialog if present
		try {
			driver.switchTo().alert().accept();
		} catch (Exception ignored) {
		}
	}

	@StepName("Open Full Description for Row {0}")
	public void openFullDescription(int rowIndex) {
		WebElement row = getRow(rowIndex);
		WebElement viewBtn = row.findElement(By.xpath(".//button[contains(.,'View full description')]"));
		waitUtils.waitForClickable(viewBtn);
		viewBtn.click();
		waitUtils.waitForVisibility(releaseNotesModalTitle);
	}

	@StepName("Get Full Release Notes from Modal")
	public String getModalReleaseNotes() {
		try {
			waitUtils.waitForVisibility(releaseNotesContent);
			return releaseNotesContent.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	@StepName("Close Release Notes Modal")
	public void closeReleaseNotesModal() {
		waitUtils.waitForClickable(closeNotesButton);
		closeNotesButton.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}


	// =========================================================================
	// 8. ACTION METHODS: Upload Release Modal
	// =========================================================================

	@StepName("Open Upload Release Modal")
	public void openUploadReleaseModal() {
		waitUtils.waitForClickable(uploadReleaseButton);
		uploadReleaseButton.click();
		waitUtils.waitForVisibility(uploadModalTitle);
	}

	@StepName("Enter Release Version: {0}")
	public void enterVersion(String version) {
		waitUtils.waitForVisibility(versionInput);
		versionInput.clear();
		versionInput.sendKeys(version);
	}

	@StepName("Enter Release Description: {0}")
	public void enterDescription(String description) {
		waitUtils.waitForVisibility(descriptionTextarea);
		descriptionTextarea.clear();
		descriptionTextarea.sendKeys(description);
	}

	@StepName("Upload Exe File: {0}")
	public void uploadExeFile(String absoluteFilePath) {
		fileInput.sendKeys(absoluteFilePath);
	}

	@StepName("Click Modal Upload Button")
	public void clickModalUpload() {
		waitUtils.waitForClickable(modalUploadButton);
		modalUploadButton.click();
	}

	@StepName("Upload Offline Portal Release with Version: {0}, Description: {1}, File: {2}")
	public void uploadRelease(String version, String description, String absoluteFilePath) {
		openUploadReleaseModal();
		enterVersion(version);
		if (description != null && !description.isEmpty()) {
			enterDescription(description);
		}
		uploadExeFile(absoluteFilePath);
		clickModalUpload();
	}

	@StepName("Close Upload Release Modal")
	public void closeUploadModal() {
		waitUtils.waitForClickable(modalCancelButton);
		modalCancelButton.click();
		waitUtils.waitForInvisibility(By.xpath("//div[@role='dialog']"));
	}

	@StepName("Check if Upload Release Modal is Displayed")
	public boolean isUploadModalDisplayed() {
		try {
			return uploadModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Open Release Notes for first row if available")
	public boolean openFirstReleaseNotes() {
		try {
			By btnBy = By.xpath("//button[contains(.,'View full description')]");
			List<WebElement> notesBtns = driver.findElements(btnBy);
			if (!notesBtns.isEmpty()) {
				WebElement btn = notesBtns.get(0);
				waitUtils.waitForClickable(btn);
				clickUsingJS(btn);
				waitUtils.waitForVisibility(releaseNotesModalTitle);
				return true;
			}
		} catch (Exception e) {
			// Not present
		}
		return false;
	}

	@StepName("Check if Release Notes Modal is Displayed")
	public boolean isReleaseNotesModalDisplayed() {
		try {
			return releaseNotesModalTitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Switch to EXE Releases Tab")
	public void clickExeTab() {
		try {
			if (isApkTabPresent()) {
				waitUtils.waitForClickable(exeTab);
				exeTab.click();
			}
		} catch (Exception e) {
			try {
				waitUtils.clickUsingJS(exeTab);
			} catch (Exception ignored) {
			}
		}
	}

	public void switchToExeTab() {
		clickExeTab();
	}

	@StepName("Switch to APK Releases Tab")
	public void clickApkTab() {
		try {
			if (isApkTabPresent()) {
				waitUtils.waitForClickable(apkTab);
				apkTab.click();
			}
		} catch (Exception e) {
			try {
				waitUtils.clickUsingJS(apkTab);
			} catch (Exception ignored) {
			}
		}
	}

	public void switchToApkTab() {
		clickApkTab();
	}

	@StepName("Verify APK Tab is Present")
	public boolean isApkTabPresent() {
		return !driver.findElements(By.xpath("//*[@id='release-tab-apk'] | //button[@role='tab' and contains(.,'APK')]")).isEmpty();
	}
}
