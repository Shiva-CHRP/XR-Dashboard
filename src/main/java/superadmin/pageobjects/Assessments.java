package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Assessments extends AbstractComponent{

	public Assessments(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/super-admin/curriculum/assessments']")
	private WebElement assessments;
	
	@StepName("Click on the Assessments")
	public void clickAssessments() {
		assessments.click();
	}

}
