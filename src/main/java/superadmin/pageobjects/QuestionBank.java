package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class QuestionBank extends AbstractComponent{

	public QuestionBank(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/super-admin/curriculum/question-bank']")
	private WebElement questionBank;
	
	@StepName("Click on the Question Bank")
	public void clickQuestionBank() {
		questionBank.click();
	}


}
