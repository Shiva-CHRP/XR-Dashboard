package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Synchronization extends AbstractComponent {

	public Synchronization(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Synchronization']]")
	private WebElement synchronizationButton;

	@StepName("Click Synchronization")
	public void clickSynchronization() {
		synchronizationButton.click();
	}
}
