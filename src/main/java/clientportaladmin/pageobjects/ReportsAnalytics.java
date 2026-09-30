package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ReportsAnalytics extends AbstractComponent {

	public ReportsAnalytics(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Reports']]")
	private WebElement reportsButton;

	@StepName("Click Reports")
	public void clickReports() {
		reportsButton.click();
	}

}
