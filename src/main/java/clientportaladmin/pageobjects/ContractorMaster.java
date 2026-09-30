package clientportaladmin.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import superadmin.abstractcomponent.AbstractComponent;
import superadmin.annotations.StepName;

public class ContractorMaster extends AbstractComponent {

	public ContractorMaster(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[.//span[normalize-space()='Contractor Master']]")
	private WebElement contractorMasterButton;

	@StepName("Click Contractor Master")
	public void clickContractorMaster() {
		contractorMasterButton.click();
	}
}
