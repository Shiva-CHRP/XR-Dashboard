package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class DesignationMaster extends AbstractComponent {

	public DesignationMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Designation Master']]")
	private WebElement designationMasterButton;

	@StepName("Click Designation Master")
	public void clickDesignationMaster() {
		designationMasterButton.click();
	}

}
