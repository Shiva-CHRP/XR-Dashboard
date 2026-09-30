package superadmin.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {
	public static String getScreenshot(WebDriver driver, String testName) {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String cleanName = (testName != null) ? testName.replaceAll("[^a-zA-Z0-9_-]", "_") : "step";
		String relativePath = "../reports/screenshots/" + cleanName + "_" + timestamp + ".png";
		String fullPath = System.getProperty("user.dir") + "/reports/screenshots/" + cleanName + "_" + timestamp + ".png";

		try {
			WebDriver d = (driver != null) ? driver : WebDriverFactory.getDriver();
			if (d instanceof TakesScreenshot) {
				File src = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
				File dest = new File(fullPath);
				if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				FileUtils.copyFile(src, dest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return relativePath;
	}

	public static String getBase64Screenshot(WebDriver driver) {
		try {
			WebDriver d = (driver != null) ? driver : WebDriverFactory.getDriver();
			if (d instanceof TakesScreenshot) {
				return ((TakesScreenshot) d).getScreenshotAs(OutputType.BASE64);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
