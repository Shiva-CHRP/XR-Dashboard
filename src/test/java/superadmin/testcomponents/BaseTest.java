package superadmin.testcomponents;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import clientportaladmin.pageobjects.AreaMaster;
import clientportaladmin.pageobjects.AssignCurriculums;
import clientportaladmin.pageobjects.AssignModules;
import clientportaladmin.pageobjects.CategoryMaster;
import clientportaladmin.pageobjects.CertificateTemplates;
import clientportaladmin.pageobjects.ClientLogin;
import clientportaladmin.pageobjects.Certificates;
import clientportaladmin.pageobjects.ContentHub;
import clientportaladmin.pageobjects.ContractorMaster;
import clientportaladmin.pageobjects.Curriculum;
import clientportaladmin.pageobjects.DepartmentMaster;
import clientportaladmin.pageobjects.DesignationMaster;
import clientportaladmin.pageobjects.DivisionMaster;
import clientportaladmin.pageobjects.DuplicateConflicts;
import clientportaladmin.pageobjects.Events;
import clientportaladmin.pageobjects.Overview;
import clientportaladmin.pageobjects.PlantMaster;
import clientportaladmin.pageobjects.RecoveryCenter;
import clientportaladmin.pageobjects.ReportsAnalytics;
import clientportaladmin.pageobjects.RoleAssignment;
import clientportaladmin.pageobjects.Settings;
import clientportaladmin.pageobjects.SetupMaster;
import clientportaladmin.pageobjects.SupportTickets;
import clientportaladmin.pageobjects.Synchronization;
import clientportaladmin.pageobjects.Trainers;
import clientportaladmin.pageobjects.Users;
import clientportaladmin.pageobjects.VRModules;
import clientportalmanager.pageobjects.AssignEmployees;
import clientportalmanager.pageobjects.DesignateTrainer;
import clientportalmanager.pageobjects.ManagerAssignCurriculums;
import clientportalmanager.pageobjects.ManagerAssignModules;
import clientportalmanager.pageobjects.ManagerCertificates;
import clientportalmanager.pageobjects.ManagerContentHub;
import clientportalmanager.pageobjects.ManagerOverview;
import clientportalmanager.pageobjects.ManagerRecoveryCenter;
import clientportalmanager.pageobjects.ManagerReportsAnalytics;
import clientportalmanager.pageobjects.ManagerSettings;
import clientportalmanager.pageobjects.ManagerSupportTickets;
import clientportalmanager.pageobjects.ManagerTrainers;
import clientportalmanager.pageobjects.ManagerUsers;
import clientportalmanager.pageobjects.MyEvents;
import clientportaltrainer.pageobjects.TrainerMyEvents;
import clientportaltrainer.pageobjects.TrainerOverview;
import clientportaltrainer.pageobjects.TrainerSchedule;
import clientportaltrainer.pageobjects.TrainerSettings;
import superadmin.pageobjects.Assessments;
import superadmin.pageobjects.AuditLog;
import superadmin.pageobjects.CurriculumCatalogue;
import superadmin.pageobjects.DeveloperDashboard;
import superadmin.pageobjects.DeveloperOrganisations;
import superadmin.pageobjects.DeveloperProfile;
import superadmin.pageobjects.Developers;
import superadmin.pageobjects.License;
import superadmin.pageobjects.LoginPage;
import superadmin.pageobjects.MDMDevices;
import superadmin.pageobjects.ModuleCatalogue;
import superadmin.pageobjects.OfflinePortalRelease;
import superadmin.pageobjects.OrgApplications;
import superadmin.pageobjects.OrganizationPage;
import superadmin.pageobjects.OrganizationSync;
import superadmin.pageobjects.QuestionBank;
import superadmin.pageobjects.ReviewQueue;
import superadmin.pageobjects.SubmissionTracker;
import superadmin.pageobjects.Support;
import superadmin.pageobjects.SystemHealth;
import superadmin.reports.ExtentReportNG;
import superadmin.reports.ExtentTestManager;
import superadmin.utils.ConfigReader;
import superadmin.utils.ToastResponse;
import superadmin.utils.ToastUtils;
import superadmin.utils.WebDriverFactory;

public class BaseTest {
	protected static ExtentReports extent = ExtentReportNG.getInstance();
	protected SoftAssert softAssert;
	public WebDriver driver;
	public ToastUtils toastUtils;
	public superadmin.utils.WaitUtils waitUtils;
	protected ExtentTest test;
	WebDriverFactory factory;

	public LoginPage loginPage;
	public Assessments assessments;
	public AuditLog auditLog;
	public CurriculumCatalogue curriculumCatalogue;
	public DeveloperDashboard developerDashboard;
	public DeveloperOrganisations developerOrganisations;
	public DeveloperProfile developerProfile;
	public Developers developers;
	public License license;
	public MDMDevices mdmDevices;
	public ModuleCatalogue moduleCatalogue;
	public OfflinePortalRelease offlinePortalRelease;
	public OrgApplications orgApplications;
	public OrganizationPage organizationPage;
	public OrganizationSync organizationSync;
	public QuestionBank questionBank;
	public ReviewQueue reviewQueue;
	public SubmissionTracker submissionTracker;
	public Support support;
	public SystemHealth systemHealth;
	public superadmin.pageobjects.Settings superAdminSettings;
	public superadmin.pageobjects.AdminNotifications adminNotifications;

	public AreaMaster areaMaster;
	public AssignCurriculums assignCurriculums;
	public AssignModules assignModules;
	public CategoryMaster categoryMaster;
	public Certificates certificates;
	public CertificateTemplates certificateTemplates;
	public ClientLogin clientLogin;
	public ContentHub contentHub;
	public ContractorMaster contractorMaster;
	public Curriculum curriculum;
	public DepartmentMaster departmentMaster;
	public DesignationMaster designationMaster;
	public DivisionMaster divisionMaster;
	public DuplicateConflicts duplicateConflicts;
	public Events events;
	public clientportaladmin.pageobjects.MDMDevices clientMdmDevices;
	public Overview overview;
	public PlantMaster plantMaster;
	public RecoveryCenter recoveryCenter;
	public ReportsAnalytics reportsAnalytics;
	public RoleAssignment roleAssignment;
	public Settings settings;
	public SetupMaster setupMaster;
	public SupportTickets supportTickets;
	public Synchronization synchronization;
	public Trainers trainers;
	public Users users;
	public VRModules vrModules;
	
	public AssignEmployees assignEmployees;
	public DesignateTrainer designateTrainer;
	public ManagerAssignCurriculums managerAssignCurriculums;
	public ManagerAssignModules managerAssignModules;
	public ManagerCertificates managerCertificates;
	public ManagerContentHub managerContentHub;
	public ManagerOverview managerOverview;
	public ManagerRecoveryCenter managerRecoveryCenter;
	public ManagerSettings managerSettings;
	public ManagerSupportTickets managerSupportTickets;
	public ManagerTrainers managerTrainers;
	public ManagerUsers managerUsers;
	public ManagerReportsAnalytics managerReportsAnalytics;
	public MyEvents myEvents;
	
	public TrainerMyEvents trainerMyEvents;
	public TrainerOverview trainerOverview;
	public TrainerSchedule trainerSchedule;
	public TrainerSettings trainerSettings;

	protected void initializeEnvironment() throws IOException {
		factory = new WebDriverFactory();
		extent = ExtentReportNG.getInstance();
		driver = factory.initializeDriver();
		waitUtils = new superadmin.utils.WaitUtils(driver);
		initializePageObjects();
		initializeSuperAdminPageObjects();
		initializeClientAdminPageObjects();
		initializeClientManagerPageObjects();
		initializeClientTrainerPageObjects();
	}

	@BeforeClass(alwaysRun = true)
	public void setup() throws IOException, InterruptedException {
		initializeEnvironment();
		launchSuperAdmin();
	}

	public void launchClientPortal() {
		driver.get(ConfigReader.getClientUrl());
	}

	public void launchSuperAdmin() {
		driver.get(ConfigReader.getUrl());
	}

	public void initializePageObjects() {
		loginPage = new LoginPage(driver);
	}

	public void initializeSuperAdminPageObjects() {

		assessments = new Assessments(driver);
		auditLog = new AuditLog(driver);
		curriculumCatalogue = new CurriculumCatalogue(driver);
		developerDashboard = new DeveloperDashboard(driver);
		developerOrganisations = new DeveloperOrganisations(driver);
		developerProfile = new DeveloperProfile(driver);
		developers = new Developers(driver);
		license = new License(driver);
		mdmDevices = new MDMDevices(driver);
		moduleCatalogue = new ModuleCatalogue(driver);
		offlinePortalRelease = new OfflinePortalRelease(driver);
		orgApplications = new OrgApplications(driver);
		organizationPage = new OrganizationPage(driver);
		organizationSync = new OrganizationSync(driver);
		questionBank = new QuestionBank(driver);
		reviewQueue = new ReviewQueue(driver);
		submissionTracker = new SubmissionTracker(driver);
		support = new Support(driver);
		systemHealth = new SystemHealth(driver);
		superAdminSettings = new superadmin.pageobjects.Settings(driver);
		adminNotifications = new superadmin.pageobjects.AdminNotifications(driver);
	}

	public void initializeClientAdminPageObjects() {
		areaMaster = new AreaMaster(driver);
		assignCurriculums = new AssignCurriculums(driver);
		assignModules = new AssignModules(driver);
		categoryMaster = new CategoryMaster(driver);
		certificates = new Certificates(driver);
		certificateTemplates = new CertificateTemplates(driver);
		clientLogin = new ClientLogin(driver);
		clientMdmDevices = new clientportaladmin.pageobjects.MDMDevices(driver);
		contentHub = new ContentHub(driver);
		contractorMaster = new ContractorMaster(driver);
		curriculum = new Curriculum(driver);
		departmentMaster = new DepartmentMaster(driver);
		designationMaster = new DesignationMaster(driver);
		divisionMaster = new DivisionMaster(driver);
		duplicateConflicts = new DuplicateConflicts(driver);
		events = new Events(driver);
		overview = new Overview(driver);
		plantMaster = new PlantMaster(driver);
		recoveryCenter = new RecoveryCenter(driver);
		reportsAnalytics = new ReportsAnalytics(driver);
		roleAssignment = new RoleAssignment(driver);
		settings = new Settings(driver);
		setupMaster = new SetupMaster(driver);
		supportTickets = new SupportTickets(driver);
		synchronization = new Synchronization(driver);
		trainers = new Trainers(driver);
		users = new Users(driver);
		vrModules = new VRModules(driver);
	}

	public void initializeClientManagerPageObjects() {
		assignEmployees = new AssignEmployees(driver);
		designateTrainer = new DesignateTrainer(driver);
		managerAssignCurriculums = new ManagerAssignCurriculums(driver);
		managerAssignModules = new ManagerAssignModules(driver);
		managerCertificates = new ManagerCertificates(driver);
		managerContentHub = new ManagerContentHub(driver);
		managerOverview = new ManagerOverview(driver);
		managerRecoveryCenter = new ManagerRecoveryCenter(driver);
		managerSettings = new ManagerSettings(driver);
		managerSupportTickets = new ManagerSupportTickets(driver);
		managerTrainers = new ManagerTrainers(driver);
		managerUsers = new ManagerUsers(driver);
		managerReportsAnalytics = new ManagerReportsAnalytics(driver);
		myEvents = new MyEvents(driver);
	}

	public void initializeClientTrainerPageObjects() {
		trainerMyEvents = new TrainerMyEvents(driver);
		trainerOverview = new TrainerOverview(driver);
		trainerSchedule = new TrainerSchedule(driver);
		trainerSettings = new TrainerSettings(driver);
	}
	public void assertToast(ToastResponse toast, String expectedMessage, String expectedType) {

		Assert.assertEquals(toast.getMessage(), expectedMessage);
		Assert.assertEquals(toast.getType(), expectedType);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {

		toastUtils = new ToastUtils(driver);
		softAssert = new SoftAssert();
//		 test = extent.createTest(method.getName());
//		    ExtentTestManager.setTest(test);
	}

	@AfterClass(alwaysRun = true)
	public void tearDownSuite() {
		if (driver != null) {
			WebDriverFactory.quitDriver();
		}
		ExtentTestManager.unload();
		extent.flush();
	}
}
