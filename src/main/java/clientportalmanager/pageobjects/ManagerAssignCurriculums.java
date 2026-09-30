package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerAssignCurriculums extends AbstractComponent{

	public ManagerAssignCurriculums(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Assign Curriculums']]")
	private WebElement assignCurriculumsButton;

	@StepName("Click Manager Assign Modules")
	public void clickManagerAssignCurriculums() {
		assignCurriculumsButton.click();
	}
}
