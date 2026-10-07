package clientportaladmin.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class Certificates extends AbstractComponent {

	public Certificates(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Certificates']]")
	private WebElement certificatesNavButton;

	@FindBy(xpath = "//h1[normalize-space()='Certificates'] | //header//h1[contains(.,'Certificate')]")
	private WebElement pageHeaderTitle;

	@FindBy(xpath = "//input[contains(@placeholder,'Search') or contains(@placeholder,'search')]")
	private WebElement searchInput;

	@FindBy(xpath = "//button[contains(normalize-space(),'Refresh')] | //button[.//svg[contains(@class,'lucide-refresh')]]")
	private WebElement refreshButton;

	@FindBy(xpath = "//table//tbody//tr")
	private List<WebElement> certificateRows;

	@FindBy(xpath = "//table//tbody//tr[1]//button[.//svg or contains(.,'View')]")
	private WebElement firstRowViewButton;

	@FindBy(xpath = "//*[@role='dialog']//*[contains(text(),'Certificate')]")
	private WebElement certificateModalTitle;

	@FindBy(xpath = "//*[@role='dialog']//button[.//svg or @aria-label='Close']")
	private WebElement closeCertificateModalButton;

	@StepName("Click Certificates from Sidebar")
	public void clickCertificates() {
		waitUtils.waitForClickable(certificatesNavButton);
		certificatesNavButton.click();
	}

	@StepName("Verify Certificates Page is Loaded")
	public boolean isCertificatesPageLoaded() {
		waitUtils.waitForVisibility(pageHeaderTitle);
		return pageHeaderTitle.isDisplayed();
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

	@StepName("View First Certificate Modal")
	public void viewFirstCertificate() {
		waitUtils.waitForClickable(firstRowViewButton);
		firstRowViewButton.click();
	}

	@StepName("Verify Certificate Modal is Displayed")
	public boolean isCertificateModalDisplayed() {
		waitUtils.waitForVisibility(certificateModalTitle);
		return certificateModalTitle.isDisplayed();
	}

	@StepName("Close Certificate Modal")
	public void closeCertificateModal() {
		waitUtils.waitForClickable(closeCertificateModalButton);
		closeCertificateModalButton.click();
	}
}
