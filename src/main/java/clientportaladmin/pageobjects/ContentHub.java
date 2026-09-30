package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ContentHub extends AbstractComponent {

	public ContentHub(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Content Hub']]")
	private WebElement contentHubButton;

	@StepName("Click Content Hub")
	public void clickContentHub() {
		contentHubButton.click();
	}
}
