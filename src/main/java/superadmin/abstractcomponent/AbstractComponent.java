package superadmin.abstractcomponent;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import superadmin.utils.ToastResponse;
import superadmin.utils.ToastUtils;
import superadmin.utils.WaitUtils;

public class AbstractComponent {
	protected WebDriver driver;
	protected WaitUtils waitUtils;
	public ToastUtils toastUtils;

	public AbstractComponent(WebDriver driver) {

		this.driver = driver;

		this.waitUtils = new WaitUtils(driver);
		toastUtils = new ToastUtils(driver);
		PageFactory.initElements(driver, this);
	}

	// WAIT WRAPPERS
	public void waitElementToAppear(WebElement element) {

		waitUtils.waitForVisibility(element);
	}

	public void waitElementToBeClickable(WebElement element) {

		waitUtils.waitForClickable(element);
	}

	public void waitElementToBeClickable(By locator) {

		waitUtils.waitForClickable(locator);
	}

	public void waitForElementToDisappear(WebElement element) {

		waitUtils.waitForInvisibility(element);
	}

	public WebElement waitForVisibility(By locator) {

		return waitUtils.waitForVisibility(locator);
	}

	public void waitForVisibility(WebElement element) {

		waitUtils.waitForVisibility(element);
	}

	public <T> T waitUntil(java.util.function.Function<? super WebDriver, T> condition) {

		return waitUtils.waitUntil(condition);
	}

	public void clickUsingJS(WebElement element) {

		waitUtils.clickUsingJS(element);
	}

	// Drop Downs
	public void selectByVisibleText(WebElement element, String text) {

		new Select(element).selectByVisibleText(text);
	}

	public void selectByValue(WebElement element, String value) {

		new Select(element).selectByValue(value);
	}

	public void selectByIndex(WebElement element, int index) {

		new Select(element).selectByIndex(index);
	}

	// Alert
	public void acceptAlert() {

		waitUtils.waitForAlert();

		driver.switchTo().alert().accept();
	}

	public void dismissAlert() {

		waitUtils.waitForAlert();

		driver.switchTo().alert().dismiss();
	}

	public String getAlertText() {

		waitUtils.waitForAlert();

		return driver.switchTo().alert().getText();
	}

	// Scroll
	public void scrollToElement(WebElement element) {

		waitUtils.scrollIntoView(element);
	}

	// Window Handlers
	public boolean switchToWindow(String title) {

		for (String handle : driver.getWindowHandles()) {

			driver.switchTo().window(handle);

			if (driver.getTitle().contains(title)) {
				 return true;
			}
		}
		return false;
	}

	// protected By addBtn = By.xpath("//div[@title='Add']");

	public void clickAddButton() {
		By addBtn = By.xpath("//div[@title='Add']");
		WebElement addElement = waitUtils.waitForVisibility(addBtn);
		waitUtils.scrollIntoView(addElement);
		waitUtils.waitForClickable(addElement);
		addElement.click();
	}

	public void search(String value) {
	    By searchBox = By.xpath("//input[contains(translate(@placeholder,'SEARCH','search'),'search')]");
	    By rows = By.xpath("//table//tbody//tr");
	    By noData = By.xpath("//table//tbody//td[contains(text(),'No data found')]");

	    WebElement field = waitUtils.waitForVisibility(searchBox);
	    waitUtils.waitForClickable(field);
	    field.clear();
	    field.sendKeys(value);

	    // Wait until either rows are displayed or "No data found" appears
	    waitUtils.waitUntil(driver ->
	            !driver.findElements(rows).isEmpty() ||
	            !driver.findElements(noData).isEmpty());
	}

	// ================= TOAST NOTIFICATION HELPERS =================
	public ToastResponse captureToast() {
		return toastUtils.captureToast();
	}

	public String getToastMessage() {
		return toastUtils.captureToast().getMessage();
	}

	public String getToastType() {
		return toastUtils.captureToast().getType();
	}

	public void waitForToastToDisappear() {
		toastUtils.waitForToastToDisappear();
	}

	// ================= MASTER LABEL RESOLUTION HELPERS =================
	public String getDynamicMasterLabel(String key) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String label = (String) js.executeScript(
					"try {" +
					"  const raw = localStorage.getItem('master-labels-v1');" +
					"  if (!raw) return '';" +
					"  return JSON.parse(raw)['" + key + "'] || '';" +
					"} catch (e) { return ''; }");
			return (label != null && !label.isEmpty()) ? label : key;
		} catch (Exception e) {
			return key;
		}
	}

	// ================= CLIENT ROUTE NAVIGATION HELPER =================
	public void navigateToClientRoute(WebElement navButton, String relativeRoute) {
		String cleanRoute = relativeRoute.replaceAll("^/", "");
		if (waitUtils.waitForUrlContains(cleanRoute, 1)) {
			return;
		}
		boolean clicked = false;
		try {
			if (navButton != null) {
				waitUtils.waitForClickable(navButton);
				waitUtils.scrollIntoView(navButton);
				try {
					navButton.click();
					clicked = true;
				} catch (Exception clickEx) {
					waitUtils.clickUsingJS(navButton);
					clicked = true;
				}
			}
		} catch (Exception e) {
			// Proxy element resolution fallback
		}

		if (!clicked || !waitUtils.waitForUrlContains(cleanRoute, 3)) {
			try {
				String label = cleanRoute.replace("-master", "").replace("-", " ").trim().toLowerCase();
				java.util.List<WebElement> matchingButtons = driver.findElements(org.openqa.selenium.By.xpath(
						"//aside//button[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + label + "')]"));
				if (!matchingButtons.isEmpty()) {
					waitUtils.scrollIntoView(matchingButtons.get(0));
					waitUtils.clickUsingJS(matchingButtons.get(0));
				}
			} catch (Exception ignored) {
			}
		}
		if (!driver.findElements(org.openqa.selenium.By.xpath("//button[contains(normalize-space(),'Log Out Device')]")).isEmpty()) {
			try {
				driver.findElements(org.openqa.selenium.By.xpath("//button[contains(normalize-space(),'Log Out Device')]")).get(0).click();
				waitUtils.waitUntil(d -> !d.findElements(org.openqa.selenium.By.xpath("//aside//button")).isEmpty(), 15);
				if (!waitUtils.waitForUrlContains(cleanRoute, 2)) {
					String label = cleanRoute.replace("-master", "").replace("-", " ").trim().toLowerCase();
					java.util.List<WebElement> matchingButtons = driver.findElements(org.openqa.selenium.By.xpath(
							"//aside//button[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + label + "')]"));
					if (!matchingButtons.isEmpty()) {
						waitUtils.scrollIntoView(matchingButtons.get(0));
						waitUtils.clickUsingJS(matchingButtons.get(0));
					}
				}
			} catch (Exception ignored) {
			}
		}
		waitUtils.waitForUrlContains(cleanRoute, 5);
	}

	// ================= MASTER ENTITY CRUD HELPERS =================
	public ToastResponse performCreateMaster(String name, String description) {
		By createBtnBy = By.xpath("//button[contains(normalize-space(),'Create') or contains(normalize-space(),'Add Master') or (contains(normalize-space(),'Add') and not(contains(.,'User')))]");
		WebElement btn = waitUtils.waitForVisibility(createBtnBy);
		waitUtils.waitForClickable(btn);
		try {
			btn.click();
		} catch (Exception e) {
			clickUsingJS(btn);
		}
		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[@role='dialog']")).isEmpty(), 5);

		By nameInputBy = By.xpath("//div[@role='dialog']//label[contains(.,'Name')]/..//input | //div[@role='dialog']//input[1]");
		WebElement nameInput = waitUtils.waitForVisibility(nameInputBy);
		nameInput.clear();
		nameInput.sendKeys(name);

		if (description != null && !description.isEmpty()) {
			By descInputBy = By.xpath("//div[@role='dialog']//label[contains(.,'Description')]/..//input | //div[@role='dialog']//input[2]");
			List<WebElement> descInputs = driver.findElements(descInputBy);
			if (!descInputs.isEmpty()) {
				descInputs.get(0).clear();
				descInputs.get(0).sendKeys(description);
			}
		}

		By saveBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Save') or normalize-space()='Save']");
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

	public ToastResponse performEditMaster(String currentName, String newName, String newDescription) {
		search(currentName);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By editItemBy = By.xpath("//div[@role='menuitem' and contains(.,'Edit')]");
		WebElement editItem = waitUtils.waitForVisibility(editItemBy);
		waitUtils.waitForClickable(editItem);
		try {
			editItem.click();
		} catch (Exception e) {
			clickUsingJS(editItem);
		}
		waitUtils.waitUntil(d -> !d.findElements(By.xpath("//div[@role='dialog']")).isEmpty(), 5);

		By nameInputBy = By.xpath("//div[@role='dialog']//label[contains(.,'Name')]/..//input | //div[@role='dialog']//input[1]");
		WebElement nameInput = waitUtils.waitForVisibility(nameInputBy);
		nameInput.clear();
		nameInput.sendKeys(newName);

		if (newDescription != null && !newDescription.isEmpty()) {
			By descInputBy = By.xpath("//div[@role='dialog']//label[contains(.,'Description')]/..//input | //div[@role='dialog']//input[2]");
			List<WebElement> descInputs = driver.findElements(descInputBy);
			if (!descInputs.isEmpty()) {
				descInputs.get(0).clear();
				descInputs.get(0).sendKeys(newDescription);
			}
		}

		By saveBtnBy = By.xpath("//div[@role='dialog']//button[contains(normalize-space(),'Save Changes') or contains(.,'Save')]");
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

	public ToastResponse performToggleMasterStatus(String name) {
		search(name);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By toggleItemBy = By.xpath("//div[@role='menuitem' and (contains(.,'Deactivate') or contains(.,'Reactivate'))]");
		WebElement toggleItem = waitUtils.waitForVisibility(toggleItemBy);
		waitUtils.waitForClickable(toggleItem);
		try {
			toggleItem.click();
		} catch (Exception e) {
			clickUsingJS(toggleItem);
		}

		By confirmBtnBy = By.xpath("//div[@role='dialog']//button[contains(.,'Deactivate') or contains(.,'Confirm') or contains(.,'Yes')]");
		List<WebElement> confirmBtns = driver.findElements(confirmBtnBy);
		if (!confirmBtns.isEmpty()) {
			confirmBtns.get(0).click();
		}

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}

	public ToastResponse performDeleteMaster(String name) {
		search(name);
		By actionMenuBy = By.xpath("//table//tbody//tr[1]//button[.//svg or @aria-haspopup='menu']");
		WebElement actionMenu = waitUtils.waitForVisibility(actionMenuBy);
		waitUtils.waitForClickable(actionMenu);
		try {
			actionMenu.click();
		} catch (Exception e) {
			clickUsingJS(actionMenu);
		}

		By deleteItemBy = By.xpath("//div[@role='menuitem' and contains(.,'Delete')]");
		WebElement deleteItem = waitUtils.waitForVisibility(deleteItemBy);
		waitUtils.waitForClickable(deleteItem);
		try {
			deleteItem.click();
		} catch (Exception e) {
			clickUsingJS(deleteItem);
		}

		By confirmDeleteBtnBy = By.xpath("//div[@role='dialog']//button[contains(.,'Delete') or contains(.,'Confirm')]");
		WebElement confirmBtn = waitUtils.waitForVisibility(confirmDeleteBtnBy);
		waitUtils.waitForClickable(confirmBtn);
		confirmBtn.click();

		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}
}