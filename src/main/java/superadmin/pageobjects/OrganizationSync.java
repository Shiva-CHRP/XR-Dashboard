package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class OrganizationSync extends AbstractComponent {

	public OrganizationSync(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/super-admin/organization-sync']")
	private WebElement organizationSync;

	@StepName("Click on the Organization Sync")
	public void clickOrganizationSync() {
		organizationSync.click();
	}

}
