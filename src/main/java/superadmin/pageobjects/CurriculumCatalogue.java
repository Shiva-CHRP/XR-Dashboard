package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class CurriculumCatalogue extends AbstractComponent{

	public CurriculumCatalogue(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/super-admin/curriculum/catalogue']")
	private WebElement curriculumCatalogue;
	
	@StepName("Click on the Curriculum Catalogue")
	public void clickCurriculumCatalogue() {
		curriculumCatalogue.click();
	}

}
