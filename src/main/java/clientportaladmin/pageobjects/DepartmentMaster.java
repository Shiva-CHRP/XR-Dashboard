package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DepartmentMaster extends AbstractComponent {

	public DepartmentMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Department Master']]")
	private WebElement departmentMasterButton;

	@StepName("Click Department Master")
	public void clickDepartmentMaster() {
		departmentMasterButton.click();
	}
}
