package superadmin.selenium.listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import com.aventstack.extentreports.MediaEntityBuilder;

import superadmin.reports.ExtentTestManager;
import superadmin.utils.ScreenshotUtils;
import superadmin.utils.StepNameUtil;
import superadmin.utils.WaitUtils;
import superadmin.utils.WebDriverFactory;

public class SeleniumListener implements WebDriverListener{
	WebDriver driver;

	public SeleniumListener() {
		//this.driver = driver;
	}

	private void log(String stepName) {
		try {
			WebDriver d = (this.driver != null) ? this.driver : WebDriverFactory.getDriver();
			if (d != null) {
				WaitUtils.waitForPageLoad(d);
				ScreenshotUtils.getScreenshot(d, stepName);
				String base64 = ScreenshotUtils.getBase64Screenshot(d);
				if (base64 != null && !base64.isEmpty() && ExtentTestManager.getTest() != null) {
					ExtentTestManager.getTest().pass(stepName,
							MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
				} else if (ExtentTestManager.getTest() != null) {
					ExtentTestManager.getTest().pass(stepName);
				}
			} else if (ExtentTestManager.getTest() != null) {
				ExtentTestManager.getTest().pass(stepName);
			}
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
