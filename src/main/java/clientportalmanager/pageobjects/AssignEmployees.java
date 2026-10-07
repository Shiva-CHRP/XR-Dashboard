package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AssignEmployees extends AbstractComponent {

	public AssignEmployees(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Employees']]")
	private WebElement assignEmployeesNavButton;

	@FindBy(xpath = "//h1[contains(.,'Assign Employees') or contains(.,'Assign Users')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Assign')]")
	private WebElement saveButton;

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> employeeCheckboxes;

	@StepName("Click Assign Employees from Sidebar")
	public void clickAssignEmployees() {
		waitUtils.waitForClickable(assignEmployeesNavButton);
		assignEmployeesNavButton.click();
	}

	@StepName("Verify Assign Employees Page is Loaded")
	public boolean isAssignEmployeesPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
	}

	@StepName("Get Employee Checkboxes Count")
	public int getEmployeeCheckboxesCount() {
		return employeeCheckboxes.size();
	}
}
