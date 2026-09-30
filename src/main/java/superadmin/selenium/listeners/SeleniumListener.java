package superadmin.selenium.listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import com.aventstack.extentreports.MediaEntityBuilder;

import superadmin.reports.ExtentTestManager;
import superadmin.utils.ScreenshotUtils;
import superadmin.utils.StepNameUtil;
import superadmin.utils.WaitUtils;

public class SeleniumListener implements WebDriverListener{
	WebDriver driver;

	public SeleniumListener() {
		//this.driver = driver;
	}

	private void log(String stepName) {
		try {
			WaitUtils.waitForPageLoad(driver);
			// String stepName = StepNameUtil.getStepName();
			ExtentTestManager.getTest().pass(stepName, MediaEntityBuilder
					.createScreenCaptureFromPath(ScreenshotUtils.getScreenshot(driver, stepName)).build());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void afterClick(WebElement element) {
		log(StepNameUtil.getStepName());
	}

	@Override
	public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
		log(StepNameUtil.getStepName());
	}

	@Override
	public void afterGet(WebDriver driver, String url) {
		this.driver = driver;
//		WaitUtils.waitForPageLoad(driver);
//		log(StepNameUtil.getStepName());
	}
}
