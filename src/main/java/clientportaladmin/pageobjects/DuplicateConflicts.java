package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

/**
 * Enterprise Page Object for Duplicate Conflicts (Client Portal):
 * Shared across Admin (/admin/duplicate-conflicts), HOD (/hod/duplicate-conflicts),
 * and Trainer (/trainer/duplicate-conflicts).
 *
 * Handles:
 * - Navigation & Page Verification
 * - Status Filter Tabs (Open / Resolved)
 * - Conflict Records Table & Empty State
 * - Detail Dialog & "Use Existing Record" Resolution
 * - Refresh & Pagination
 */
public class DuplicateConflicts extends AbstractComponent {

	public DuplicateConflicts(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[normalize-space()='Duplicate Conflicts' or .//span[normalize-space()='Duplicate Conflicts']]")
	private WebElement duplicateConflictsNavButton;

	@FindBy(xpath = "//h1[contains(.,'Duplicate Conflicts')] | //header//h1[contains(.,'Duplicate Conflicts')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[@role='tab' and (starts-with(normalize-space(),'Open') or .//span[starts-with(normalize-space(),'Open')])]")
	private WebElement openTab;

	@FindBy(xpath = "//button[@role='tab' and (starts-with(normalize-space(),'Resolved') or .//span[starts-with(normalize-space(),'Resolved')])]")
	private WebElement resolvedTab;

	@FindBy(xpath = "//table/tbody/tr[not(contains(@class,'empty')) and td[1]]")
	private List<WebElement> conflictRows;

	@FindBy(xpath = "//div[contains(.,'No duplicate conflicts') or contains(.,'clean and synced') or contains(.,'nothing to resolve')]")
	private WebElement emptyStateElement;

	@FindBy(xpath = "//button[contains(.,'Refresh') or @title='Refresh']")
	private WebElement refreshButton;

	@FindBy(xpath = "//button[contains(normalize-space(),'Use Existing Record')]")
	private WebElement useExistingRecordButton;

	@FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Confirm' or normalize-space()='Yes' or contains(.,'Confirm')]")
	private WebElement confirmResolveButton;

	@FindBy(xpath = "//*[@role='dialog']//button[normalize-space()='Close' or contains(.,'Close')]")
	private WebElement closeDetailButton;

	@StepName("Click Duplicate Conflicts from Sidebar")
	public void clickDuplicateConflicts() {
		try {
			WebElement btn = driver.findElement(By.xpath("//aside//button[contains(.,'Duplicate Conflicts')]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(duplicateConflictsNavButton, "duplicate-conflicts");
		}
		waitUtils.waitForUrlContains("duplicate-conflicts", 5);
	}

	@StepName("Verify Duplicate Conflicts Page is Loaded")
	public boolean isDuplicateConflictsPageLoaded() {
		try {
			return waitUtils.waitForUrlContains("duplicate-conflicts", 5)
					|| !driver.findElements(By.xpath("//h1[contains(.,'Duplicate Conflicts')] | //header//h1[contains(.,'Duplicate Conflicts')]")).isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	@StepName("Switch to Open Conflicts Tab")
	public void clickOpenTab() {
		waitUtils.waitForClickable(openTab);
		try {
			openTab.click();
		} catch (Exception e) {
			waitUtils.clickUsingJS(openTab);
		}
	}

	public void switchToOpenTab() {
		clickOpenTab();
	}

	@StepName("Switch to Resolved Conflicts Tab")
	public void clickResolvedTab() {
		waitUtils.waitForClickable(resolvedTab);
		try {
			resolvedTab.click();
		} catch (Exception e) {
			waitUtils.clickUsingJS(resolvedTab);
		}
	}

	public void switchToResolvedTab() {
		clickResolvedTab();
	}

	@StepName("Get Conflicts Table Row Count")
	public int getConflictsCount() {
		return conflictRows.size();
	}

	@StepName("Verify Empty State is Displayed")
	public boolean isEmptyStateDisplayed() {
		return !driver.findElements(By.xpath("//div[contains(.,'No duplicate conflicts') or contains(.,'clean and synced') or contains(.,'nothing to resolve') or contains(.,'not available yet')]")).isEmpty()
				|| !driver.findElements(By.xpath("//td[contains(.,'No duplicate conflicts') or contains(.,'nothing to resolve')]")).isEmpty();
	}

	@StepName("Click Refresh Button")
	public void clickRefresh() {
		try {
			waitUtils.waitForClickable(refreshButton);
			refreshButton.click();
		} catch (Exception e) {
			waitUtils.clickUsingJS(refreshButton);
		}
	}

	@StepName("Click First Conflict Row")
	public void clickFirstConflictRow() {
		if (!conflictRows.isEmpty()) {
			waitUtils.waitForClickable(conflictRows.get(0));
			conflictRows.get(0).click();
		}
	}

	@StepName("Verify Conflict Detail Modal is Displayed")
	public boolean isDetailModalOpen() {
		return !driver.findElements(By.xpath("//*[@role='dialog']//h2[contains(.,'Duplicate Conflict')] | //*[@role='dialog']")).isEmpty();
	}

	@StepName("Close Conflict Detail Modal")
	public void closeDetailModal() {
		try {
			if (!driver.findElements(By.xpath("//*[@role='dialog']//button[normalize-space()='Close' or contains(.,'Close')]")).isEmpty()) {
				WebElement btn = driver.findElement(By.xpath("//*[@role='dialog']//button[normalize-space()='Close' or contains(.,'Close')]"));
				waitUtils.waitForClickable(btn);
				btn.click();
			} else {
				driver.findElement(By.xpath("//body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
			}
		} catch (Exception e) {
			driver.findElement(By.xpath("//body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
		}
	}

	@StepName("Resolve Selected Conflict (Use Existing Record)")
	public void resolveConflict() {
		waitUtils.waitForClickable(useExistingRecordButton);
		useExistingRecordButton.click();
		waitUtils.waitForClickable(confirmResolveButton);
		confirmResolveButton.click();
	}
}
