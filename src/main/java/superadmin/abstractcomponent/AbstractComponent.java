package superadmin.abstractcomponent;

import java.time.Duration;

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
}
