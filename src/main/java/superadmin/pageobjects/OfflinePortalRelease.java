package superadmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class OfflinePortalRelease extends AbstractComponent{

	public OfflinePortalRelease(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@href='/super-admin/offline-releases']")
	private WebElement offlineRelease;

	@StepName("Click on the Offline Portal Release")
	public void clickOfflinePortalRelease() {
		offlineRelease.click();
	}

}
