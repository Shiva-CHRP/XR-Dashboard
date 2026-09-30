package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class MDMDevices extends AbstractComponent {

	public MDMDevices(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@href='/super-admin/mdm-devices']")
	private WebElement mDMDevices;

	@StepName("Click on the MDM Devices")
	public void clickMDMDevices() {
		mDMDevices.click();
	}
}
