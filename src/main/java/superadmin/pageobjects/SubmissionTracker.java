package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class SubmissionTracker extends AbstractComponent {

	public SubmissionTracker(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/developer/submissions']']")
	private WebElement submission;

	@StepName("Click on the Submission Tracker")
	public void clickSubmissionTracker() {
		submission.click();
	}

}
