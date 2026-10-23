package clientportalmanager.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;
import superadmin.utils.ToastResponse;

public class DesignateTrainer extends AbstractComponent {

	public DesignateTrainer(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Designate Trainer']] | //button[.//span[normalize-space()='Designate Trainer']]")
	private WebElement designateTrainerNavButton;

	@FindBy(xpath = "//h1[contains(.,'Designate Trainer')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Designate')]")
	private WebElement saveButton;

	@StepName("Click Designate Trainer from Sidebar")
	public void clickDesignateTrainer() {
		try {
			WebElement btn = driver.findElement(By.xpath("//aside//button[contains(.,'Designate Trainer')]"));
			waitUtils.scrollIntoView(btn);
			waitUtils.clickUsingJS(btn);
		} catch (Exception e) {
			navigateToClientRoute(designateTrainerNavButton, "designate-trainer");
		}
		waitUtils.waitForUrlContains("designate-trainer", 5);
	}

	@StepName("Verify Designate Trainer Page is Loaded")
	public boolean isDesignateTrainerPageLoaded() {
		return waitUtils.waitForUrlContains("designate-trainer", 2)
				|| !driver.findElements(By.xpath("//h1[contains(.,'Designate Trainer')] | //header//h1")).isEmpty();
	}

	@StepName("Click Save Designation and Capture Toast")
	public ToastResponse clickSaveDesignation() {
		By saveBtnBy = By.xpath("//button[contains(normalize-space(),'Save') or contains(normalize-space(),'Designate')]");
		WebElement saveBtn = waitUtils.waitForVisibility(saveBtnBy);
		waitUtils.waitForClickable(saveBtn);
		try {
			saveBtn.click();
		} catch (Exception e) {
			clickUsingJS(saveBtn);
		}
		ToastResponse toast = toastUtils.captureToast();
		toastUtils.waitForToastToDisappear();
		return toast;
	}
}