package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class SystemHealth  extends AbstractComponent {

	public SystemHealth(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/super-admin/system-health']")
	private WebElement systemHealth;

	@StepName("Click on the System Health")
	public void clickSystemHealth() {
		systemHealth.click();
	}

}
