package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Curriculum extends AbstractComponent {

	public Curriculum(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Curriculum']]")
	private WebElement curriculumButton;

	@StepName("Click Curriculum")
	public void clickCurriculum() {
		curriculumButton.click();
	}

}
