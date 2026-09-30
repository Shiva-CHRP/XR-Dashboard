package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class CertificateTemplates extends AbstractComponent {

	public CertificateTemplates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Cert. Templates']]")
	private WebElement certificateTemplatesButton;

	@StepName("Click Certificate Templates")
	public void clickCertificateTemplates() {
		certificateTemplatesButton.click();
	}

}
