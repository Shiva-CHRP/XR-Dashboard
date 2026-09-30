# CHRP Simulation Platform - Test Automation Framework

An enterprise-grade, Page Object Model (POM) based automated testing framework for the **CHRP Simulation Platform**, covering both the **Super Admin Governance Application** and the **Client Portal Application** across all supported roles (Super Admin, VR Developer, Client Admin, Client Manager, and Client Trainer).

---

## 🏗️ Architecture & Technology Stack

* **Core Framework:** Java 17, Selenium WebDriver `4.43.0`, TestNG `7.12.0`, Maven.
* **Design Pattern:** Page Object Model (POM) with PageFactory, centralized [`AbstractComponent`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/java/superadmin/abstractcomponent/AbstractComponent.java) base, and role-segregated page packages.
* **Driver Management:** [`WebDriverFactory`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/java/superadmin/utils/WebDriverFactory.java) leveraging `ThreadLocal<WebDriver>` and `WebDriverManager` with full cross-browser support (**Chrome**, **Edge**, **Firefox**) and **Headless execution**.
* **Automatic Step Logging & Screenshots:** Powered by Selenium 4's `WebDriverListener` ([`SeleniumListener`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/java/superadmin/selenium/listeners/SeleniumListener.java)), capturing screenshots on every click/input without cluttering test logic.
* **Reporting:** ExtentReports 5 (`5.0.9`) with customized executive dashboards and module breakdown charts.
* **Flaky Test Resilience:** Integrated TestNG [`RetryAnalyzer`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/test/java/superadmin/listener/RetryAnalyzer.java) hooked dynamically via [`Listener`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/test/java/superadmin/listener/Listener.java) (`IAnnotationTransformer`).
* **Dynamic Data Generation:** [`TestDataUtil`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/java/superadmin/utils/TestDataUtil.java) generating unique names, emails, and codes to eliminate collision failures across repeated runs.

---

## 📁 Repository Structure

```
D:\eclipseworkspace\CHRP_Simulation_Platform\
├── .github\workflows\regression-test.yml   # GitHub Actions CI/CD headless test workflow
├── pom.xml                               # Maven build configuration with Surefire plugin
├── testng.xml                            # Master TestNG test suite runner (8 suites)
├── README.md                             # Framework documentation & run guide
├── src\main\resources\
│   ├── GlobalData.properties             # Environment URLs and staging credentials
│   └── testdata\                         # config.properties and organizationData.json
├── src\main\java\
│   ├── superadmin\
│   │   ├── abstractcomponent\            # AbstractComponent (waits, dropdowns, alerts)
│   │   ├── annotations\                  # Custom annotations: @TestInfo, @StepName
│   │   ├── pageobjects\                  # Superadmin & Developer page objects (20+ modules)
│   │   ├── reports\                      # ExtentReportNG, DashboardBuilder, ModuleDashboard
│   │   ├── selenium\listeners\           # SeleniumListener (WebDriverListener)
│   │   └── utils\                        # ConfigReader, WebDriverFactory, TestDataUtil, JsonReader, ToastUtils
│   ├── clientportaladmin\pageobjects\    # Client Admin page objects (Overview, Users, Curriculum, etc.)
│   ├── clientportalmanager\pageobjects\  # Client Manager page objects (Assign Modules, Certificates, etc.)
│   └── clientportaltrainer\pageobjects\  # Client Trainer page objects (My Events, Overview, Settings)
└── src\test\java\
    ├── superadmin\
    │   ├── assertions\                   # ToastAssertions helper
    │   ├── listener\                     # Listener (ITestListener & IAnnotationTransformer), RetryAnalyzer
    │   ├── testcomponents\               # BaseTest (Superadmin setup & teardown)
    │   └── tests\                        # SmokeTest, OrganizationTest, CatalogueAndGovernanceTest, RoleSwitchTest, DeveloperPortalTest
    ├── clientportaladmin\
    │   ├── testcomponents\               # ClientBaseTest (Client Portal direct launch)
    │   └── tests\                        # ClientSmokeTest, ClientUserManagementTest, ClientCurriculumAndContentTest, ClientReportsAndSyncTest
    ├── clientportalmanager\tests\        # ManagerSmokeTest
    └── clientportaltrainer\tests\         # TrainerSmokeTest
```

---

## ⚙️ Configuration Setup

Configure staging environment URLs and credentials in [`src/main/resources/GlobalData.properties`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/resources/GlobalData.properties):

```properties
browser=chrome
url=https://superadmin-ui-staging.xrdashboard.com/login
clientUrl=https://clientportal.xrdashboard.com/login
username=superadmin@yopmail.com
password=Admin@1234
clientOrgCode=CHRP
clientUsername=clientadmin@yopmail.com
clientPassword=Client@1234
clientManagerUsername=clientmanager@yopmail.com
clientManagerPassword=Manager@1234
clientTrainerUsername=clienttrainer@yopmail.com
clientTrainerPassword=Trainer@1234
```

---

## 🚀 Test Execution Commands

### 1. Run Complete Test Suite (All 7 Suites in `testng.xml`)
```powershell
mvn test
```

### 2. Run in Headless Mode (For CI/CD Pipelines or Background Runs)
```powershell
mvn test -Dheadless=true
```

### 3. Cross-Browser Execution
Override the default browser dynamically from the command line:
```powershell
# Run in Edge
mvn test -Dbrowser=edge

# Run in Firefox
mvn test -Dbrowser=firefox

# Run in Chrome Headless
mvn test -Dbrowser=chrome -Dheadless=true
```

### 4. Run Role-Specific Test Suites
```powershell
# Superadmin Smoke & Role Switching
mvn test -Dtest=SmokeTest,RoleSwitchTest

# Superadmin Organization & Governance
mvn test -Dtest=OrganizationTest,CatalogueAndGovernanceTest

# Client Portal Admin Core & Curriculums
mvn test -Dtest=ClientSmokeTest,ClientUserManagementTest,ClientCurriculumAndContentTest

# Client Portal Manager & Trainer
mvn test -Dtest=ManagerSmokeTest,TrainerSmokeTest
```

---

## 📊 Reports & Dashboards

* After execution, HTML Extent reports with interactive dashboards are saved under:
  ```
  reports/index.html
  ```
* Screenshots of each automated step are automatically stored in:
  ```
  reports/screenshots/
  ```

---

## 💡 Best Practices for Adding New Tests

1. **Page Objects:**
   * Extend [`AbstractComponent`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/java/superadmin/abstractcomponent/AbstractComponent.java).
   * Annotate user action methods with `@StepName("Description of Action")` for automatic report logging.
2. **Super Admin Tests:**
   * Extend [`BaseTest`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/test/java/superadmin/testcomponents/BaseTest.java).
   * Annotate test methods with `@TestInfo(module = "ModuleName", description = "Test Summary", priority = "High|Medium|Low")`.
3. **Client Portal Tests:**
   * Extend [`ClientBaseTest`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/test/java/clientportaladmin/testcomponents/ClientBaseTest.java) to launch the Client Portal directly.
4. **Dynamic Data:**
   * Use [`TestDataUtil`](file:///D:/eclipseworkspace/CHRP_Simulation_Platform/src/main/java/superadmin/utils/TestDataUtil.java) methods (`getRandomOrgName()`, `getRandomEmail()`, etc.) whenever creating new entities.
