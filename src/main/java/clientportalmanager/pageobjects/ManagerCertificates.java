package clientportalmanager.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerCertificates extends AbstractComponent{

	public ManagerCertificates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Certificates']]")
	private WebElement certificatesButton;

	@StepName("Click Manager Certificates")
	public void clickManagerCertificates() {
		certificatesButton.click();
	}
}
