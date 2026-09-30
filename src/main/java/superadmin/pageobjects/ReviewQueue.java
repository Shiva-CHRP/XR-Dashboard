package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ReviewQueue extends AbstractComponent{

	public ReviewQueue(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/super-admin/modules/review']")
	private WebElement reviewQueue;
	
	@StepName("Click on the Review Queue")
	public void clickReviewQueue() {
		reviewQueue.click();
	}

}
