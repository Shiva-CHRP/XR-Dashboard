package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class AssignEmployees extends AbstractComponent {

	public AssignEmployees(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Assign Employees']] | //button[.//span[normalize-space()='Assign Employees']]")
	private WebElement assignEmployeesNavButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Employees') or contains(.,'Assign Users')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Assign')]")
	private WebElement saveButton;

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> employeeCheckboxes;

	@StepName("Click Assign Employees from Sidebar")
	public void clickAssignEmployees() {
		try {
			WebElement btn = driver.findElement(By.xpath("//aside//button[contains(.,'Assign Employees')]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(assignEmployeesNavButton, "assign-employees");
		}
		waitUtils.waitForUrlContains("assign-employees", 5);
	}

	@StepName("Verify Assign Employees Page is Loaded")
	public boolean isAssignEmployeesPageLoaded() {
		return waitUtils.waitForUrlContains("assign-employees", 2)
				|| !driver.findElements(By.xpath("//h1[contains(.,'Assign Employees') or contains(.,'Assign Users')] | //header//h1")).isEmpty();
	}

	@StepName("Get Employee Checkboxes Count")
	public int getEmployeeCheckboxesCount() {
		return driver.findElements(By.xpath("//input[@type='checkbox']")).size();
	}

	@StepName("Toggle Employee Checkbox")
	public void toggleEmployeeCheckbox(int index) {
		List<WebElement> boxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
		if (index >= 0 && index < boxes.size()) {
			waitUtils.scrollIntoView(boxes.get(index));
			try {
				boxes.get(index).click();
			} catch (Exception e) {
				clickUsingJS(boxes.get(index));
			}
		}
	}

	@StepName("Save Employee Assignments and Capture Toast")
	public ToastResponse saveEmployeeAssignments() {
		By saveBtnBy = By.xpath("//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Assign')]");
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
}