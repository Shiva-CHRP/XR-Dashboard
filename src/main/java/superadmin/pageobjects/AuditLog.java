package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class AuditLog extends AbstractComponent {

	public AuditLog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/super-admin/audit-logs']")
	private WebElement auditLog;

	@StepName("Click on the Audit Log")
	public void clickAuditLog() {
		auditLog.click();
	}

}
