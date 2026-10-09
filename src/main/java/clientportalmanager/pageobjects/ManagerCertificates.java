package clientportalmanager.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ManagerCertificates extends AbstractComponent {

	public ManagerCertificates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//aside//button[.//span[normalize-space()='Certificates']] | //button[.//span[normalize-space()='Certificates']]")
	private WebElement certificatesButton;

	@FindBy(xpath = "//h1[contains(.,'Certificates')] | //header//h1")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> certificateRows;

	@FindBy(xpath = "//button[contains(normalize-space(),'Export')]")
	private WebElement exportButton;

	@StepName("Click Manager Certificates")
	public void clickManagerCertificates() {
		navigateToClientRoute(certificatesButton, "certificates");
	}

	@StepName("Verify Manager Certificates Page is Loaded")
	public boolean isManagerCertificatesPageLoaded() {
		return waitUtils.waitForUrlContains("certificates", 2)
				|| !driver.findElements(org.openqa.selenium.By.xpath("//h1[contains(.,'Certificates')] | //header//h1")).isEmpty();
	}

	@StepName("Search Certificates")
	public void searchCertificates(String query) {
		waitUtils.waitForVisibility(searchInput);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	@StepName("Get Certificate Rows Count")
	public int getCertificateRowsCount() {
		return certificateRows.size();
	}
}
