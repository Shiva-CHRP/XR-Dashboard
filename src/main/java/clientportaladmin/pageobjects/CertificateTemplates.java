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

	@FindBy(xpath = "//aside[contains(@class,'lg:flex')]//button[.//span[normalize-space()='Cert. Templates'] or .//span[normalize-space()='Certificate Templates']] | //button[.//span[normalize-space()='Cert. Templates'] or .//span[normalize-space()='Certificate Templates']]")
	private WebElement certificateTemplatesNavButton;

	@FindBy(xpath = "//h1[contains(.,'Certificate Templates')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Create Template') or contains(.,'Create')]")
	private WebElement createTemplateButton;

	@FindBy(xpath = "//div[contains(@class,'grid')]//div[contains(@class,'card') or contains(@class,'border')]")
	private java.util.List<WebElement> templateCards;

	@StepName("Click Certificate Templates from Sidebar")
	public void clickCertificateTemplates() {
		navigateToClientRoute(certificateTemplatesNavButton, "certificate-templates");
	}

	@StepName("Verify Certificate Templates Page is Loaded")
	public boolean isCertificateTemplatesLoaded() {
		return waitUtils.waitForUrlContains("certificate-templates") || (pageHeaderTitle != null && pageHeaderTitle.isDisplayed());
	}

	@StepName("Click Create Template Button")
	public void clickCreateTemplate() {
		waitUtils.waitForClickable(createTemplateButton);
		createTemplateButton.click();
	}

	@StepName("Get Template Cards Count")
	public int getTemplateCardsCount() {
		return templateCards.size();
	}
}
