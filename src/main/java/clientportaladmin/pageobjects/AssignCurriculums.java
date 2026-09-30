package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AssignCurriculums extends AbstractComponent {

	public AssignCurriculums(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Curriculums']]")
	private WebElement assignCurriculumsButton;

	@StepName("Click Assign Curriculums")
	public void clickAssignCurriculums() {
		assignCurriculumsButton.click();
	}

}
