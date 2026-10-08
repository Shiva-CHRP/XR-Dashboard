package superadmin.reports;

import org.openqa.selenium.BuildInfo;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import superadmin.utils.ConfigReader;

public class ExtentReportNG {
	private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setReportName("⚡ CHRP Simulation Platform - Sanity Automation Dashboard");
            spark.config().setDocumentTitle("CHRP Automation Dashboard | Execution Report");
            spark.config().setTimelineEnabled(true);
            spark.config().setEncoding("UTF-8");
            spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
            spark.config().setOfflineMode(true);

            // Modern Enterprise Dark Theme CSS Design System
            String customCss = """
                @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

                :root {
                  --bg-primary: #0f172a;
                  --bg-surface: #0f172a;
                  --bg-card: #182234;
                  --bg-card-hover: #1f2d45;
                  --border-color: rgba(255, 255, 255, 0.08);
                  --border-glow: rgba(99, 102, 241, 0.4);
                  --text-main: #f8fafc;
                  --text-muted: #94a3b8;
                  --text-dim: #64748b;
                  --accent-indigo: #6366f1;
                  --accent-purple: #8b5cf6;
                  --status-pass: #10b981;
                  --status-fail: #ef4444;
                  --status-skip: #f59e0b;
                  --status-info: #3b82f6;
                }

                body, body.dark, .spa, .spa.-report, .spa.-report.dark,
                .app, .layout, .vcontainer, .main-content,
                .test-wrapper, .dark .test-wrapper,
                .test-list, .dark .test-list,
                .test-content, .dark .test-content,
                .test-content-detail, .dark .test-content-detail,
                .detail-body, .dark .detail-body,
                .dashboard-view, .dark .dashboard-view,
                .dashboard-view .container-fluid, .dark .dashboard-view .container-fluid {
                  background-color: var(--bg-primary) !important;
                  background: var(--bg-primary) !important;
                  color: var(--text-main) !important;
                  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif !important;
                  -webkit-font-smoothing: antialiased;
                }

                /* Header Navbar — Sticky */
                .header.navbar {
                  position: sticky !important;
                  top: 0 !important;
                  z-index: 1000 !important;
                  background: rgba(15, 23, 42, 0.96) !important;
                  backdrop-filter: blur(20px) !important;
                  -webkit-backdrop-filter: blur(20px) !important;
                  border-bottom: 1px solid var(--border-color) !important;
                  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.5) !important;
                  height: 64px !important;
                }

                .header.navbar .vheader {
                  height: 64px !important;
                  align-items: center;
                }

                .header.navbar .nav-logo {
                  border-right: 1px solid var(--border-color);
                  padding: 0 20px;
                }

                .header.navbar .search-box {
                  margin-left: 15px;
                }

                .header.navbar .search-input input {
                  background: rgba(30, 41, 59, 0.7) !important;
                  border: 1px solid rgba(255, 255, 255, 0.12) !important;
                  border-radius: 20px !important;
                  color: #ffffff !important;
                  padding: 6px 16px !important;
                  font-size: 0.85rem;
                  transition: all 0.3s ease;
                }

                .header.navbar .search-input input:focus {
                  border-color: var(--accent-indigo) !important;
                  box-shadow: 0 0 12px rgba(99, 102, 241, 0.4) !important;
                  background: rgba(30, 41, 59, 0.95) !important;
                }

                .header.navbar .nav-right .badge {
                  background: linear-gradient(135deg, var(--accent-indigo) 0%, var(--accent-purple) 100%) !important;
                  color: #ffffff !important;
                  border-radius: 20px !important;
                  font-weight: 600 !important;
                  padding: 8px 18px !important;
                  font-size: 0.82rem !important;
                  box-shadow: 0 4px 14px rgba(99, 102, 241, 0.35) !important;
                  letter-spacing: 0.3px;
                  border: none !important;
                }

                /* Sidebar */
                .side-nav {
                  background: #0f172a !important;
                  border-right: 1px solid var(--border-color) !important;
                  width: 72px;
                }

                .side-nav-inner {
                  padding-top: 15px;
                }

                .side-nav-menu li.nav-item a {
                  border-radius: 12px;
                  margin: 6px 10px;
                  padding: 12px 0;
                  transition: all 0.25s ease;
                  color: var(--text-dim) !important;
                }

                .side-nav-menu li.nav-item:hover a {
                  background: rgba(99, 102, 241, 0.15) !important;
                  color: var(--accent-indigo) !important;
                  transform: translateY(-1px);
                }

                .side-nav-menu li.nav-item.active a {
                  background: linear-gradient(135deg, rgba(99, 102, 241, 0.25), rgba(139, 92, 246, 0.25)) !important;
                  color: #ffffff !important;
                  box-shadow: 0 0 16px rgba(99, 102, 241, 0.3);
                }

                .side-nav-menu li.nav-item a .ico i {
                  font-size: 1.25rem !important;
                }

                /* Test List Column */
                .test-wrapper {
                  margin: 0 !important;
                }

                .test-wrapper .test-list, .test-list {
                  background: #0f172a !important;
                  border-right: 1px solid var(--border-color) !important;
                  width: 420px !important;
                  min-width: 420px !important;
                  max-width: 450px !important;
                  flex: 0 0 420px !important;
                }

                /* Sticky filter toolbar inside test list */
                .test-list-tools {
                  background: rgba(15, 23, 42, 0.98) !important;
                  border-bottom: 1px solid var(--border-color) !important;
                  padding: 12px 18px !important;
                  position: sticky !important;
                  top: 0 !important;
                  z-index: 10 !important;
                  backdrop-filter: blur(12px) !important;
                  -webkit-backdrop-filter: blur(12px) !important;
                }

                .test-list-item {
                  padding: 8px 6px !important;
                }

                .test-item {
                  background: var(--bg-card) !important;
                  border: 1px solid var(--border-color) !important;
                  border-radius: 12px !important;
                  margin: 8px 6px !important;
                  padding: 14px 16px !important;
                  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2) !important;
                  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1) !important;
                }

                .test-item:hover {
                  background: var(--bg-card-hover) !important;
                  border-color: var(--border-glow) !important;
                  transform: translateX(3px);
                  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.35) !important;
                }

                .test-item.active {
                  background: linear-gradient(135deg, rgba(99, 102, 241, 0.18), rgba(139, 92, 246, 0.12)) !important;
                  border: 1.5px solid var(--accent-indigo) !important;
                  box-shadow: 0 0 20px rgba(99, 102, 241, 0.25) !important;
                }

                .test-detail .name,
                .test-item .name,
                .test-item .test-detail {
                  font-size: 0.90rem !important;
                  font-weight: 600 !important;
                  color: var(--text-main) !important;
                  line-height: 1.45 !important;
                  margin-bottom: 8px !important;
                  word-break: normal !important;
                  word-wrap: break-word !important;
                  overflow-wrap: break-word !important;
                  hyphens: none !important;
                  white-space: normal !important;
                }

                .test-detail .text-sm {
                  color: var(--text-muted) !important;
                  font-size: 0.78rem !important;
                  display: flex;
                  align-items: center;
                  justify-content: space-between;
                }

                /* Status Badges & Glow Pills */
                .badge {
                  border-radius: 20px !important;
                  font-weight: 700 !important;
                  padding: 4px 12px !important;
                  font-size: 0.72rem !important;
                  letter-spacing: 0.6px;
                  text-transform: uppercase;
                  display: inline-flex;
                  align-items: center;
                  justify-content: center;
                }

                .badge.pass-bg, .badge.log.pass-bg, .badge.success, .status.success {
                  background: linear-gradient(135deg, #10b981 0%, #059669 100%) !important;
                  color: #ffffff !important;
                  box-shadow: 0 0 12px rgba(16, 185, 129, 0.45) !important;
                  border: 1px solid rgba(16, 185, 129, 0.5) !important;
                }

                .badge.fail-bg, .badge.log.fail-bg, .badge.danger, .status.fail {
                  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%) !important;
                  color: #ffffff !important;
                  box-shadow: 0 0 12px rgba(239, 68, 68, 0.55) !important;
                  border: 1px solid rgba(239, 68, 68, 0.6) !important;
                }

                .badge.skip-bg, .badge.log.skip-bg, .badge.warning, .status.skip {
                  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%) !important;
                  color: #ffffff !important;
                  box-shadow: 0 0 12px rgba(245, 158, 11, 0.45) !important;
                  border: 1px solid rgba(245, 158, 11, 0.5) !important;
                }

                .badge.info-bg, .badge.log.info-bg, .badge.info {
                  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%) !important;
                  color: #ffffff !important;
                  box-shadow: 0 0 10px rgba(59, 130, 246, 0.35) !important;
                  border: 1px solid rgba(59, 130, 246, 0.4) !important;
                }

                /* Detail Head & Step Log Tables */
                .test-content-detail {
                  background: var(--bg-primary) !important;
                  padding: 24px !important;
                  min-height: 100% !important;
                }

                .detail-head {
                  background: var(--bg-card) !important;
                  border: 1px solid var(--border-color) !important;
                  border-radius: 14px !important;
                  padding: 20px 24px !important;
                  margin-bottom: 20px !important;
                  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25) !important;
                }

                .detail-head h5 {
                  font-size: 1.25rem !important;
                  font-weight: 700 !important;
                  color: #ffffff !important;
                  margin-bottom: 10px;
                }

                table.table {
                  background: var(--bg-card) !important;
                  background-color: var(--bg-card) !important;
                  border-radius: 12px !important;
                  overflow: hidden;
                  border: 1px solid var(--border-color) !important;
                  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2) !important;
                }

                table.table thead th {
                  background: rgba(30, 41, 59, 0.95) !important;
                  color: var(--text-muted) !important;
                  font-size: 0.75rem !important;
                  font-weight: 700 !important;
                  letter-spacing: 0.6px;
                  text-transform: uppercase;
                  border-bottom: 1px solid var(--border-color) !important;
                  border-top: none !important;
                  padding: 12px 16px !important;
                }

                table.table tbody,
                table.table tbody tr,
                table.table tbody td {
                  background: var(--bg-card) !important;
                  background-color: var(--bg-card) !important;
                  border-top: 1px solid rgba(255, 255, 255, 0.05) !important;
                  color: #e2e8f0 !important;
                  font-size: 0.88rem !important;
                  padding: 12px 16px !important;
                  vertical-align: middle !important;
                }

                tr.event-row:hover,
                tr.event-row:hover td {
                  background: var(--bg-card-hover) !important;
                  background-color: var(--bg-card-hover) !important;
                }

                /* Cards, Dashboard & Charts */
                .card {
                  background: var(--bg-card) !important;
                  border: 1px solid var(--border-color) !important;
                  border-radius: 14px !important;
                  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25) !important;
                  transition: transform 0.2s ease, box-shadow 0.2s ease;
                  margin-bottom: 24px !important;
                }

                .card:hover {
                  box-shadow: 0 10px 28px rgba(0, 0, 0, 0.35) !important;
                  border-color: rgba(99, 102, 241, 0.3) !important;
                }

                .card-header {
                  background: rgba(30, 41, 59, 0.6) !important;
                  border-bottom: 1px solid var(--border-color) !important;
                  padding: 14px 20px !important;
                }

                .card-header p, .card-header h6 {
                  font-weight: 700 !important;
                  font-size: 0.95rem !important;
                  color: #ffffff !important;
                  margin: 0;
                  letter-spacing: 0.3px;
                }

                .card-body {
                  padding: 20px !important;
                }

                .card-footer {
                  background: rgba(15, 23, 42, 0.6) !important;
                  border-top: 1px solid var(--border-color) !important;
                  color: var(--text-muted) !important;
                  padding: 12px 20px !important;
                }

                .table.table-bordered {
                  border: 1px solid var(--border-color) !important;
                }

                .table.table-bordered th, .table.table-bordered td {
                  border: 1px solid rgba(255, 255, 255, 0.06) !important;
                }

                /* Screenshot Thumbnails (Base64) */
                img, .r-img {
                  border-radius: 10px !important;
                  border: 1px solid rgba(255, 255, 255, 0.15) !important;
                  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.35) !important;
                  transition: all 0.25s ease-in-out !important;
                  cursor: zoom-in;
                }

                img:hover, .r-img:hover {
                  transform: scale(1.05);
                  border-color: var(--accent-indigo) !important;
                  box-shadow: 0 8px 24px rgba(99, 102, 241, 0.45) !important;
                }

                .step-img-thumb {
                  max-width: 175px !important;
                  max-height: 100px !important;
                  border-radius: 8px !important;
                  border: 1px solid rgba(255, 255, 255, 0.2) !important;
                  box-shadow: 0 3px 12px rgba(0, 0, 0, 0.4) !important;
                  margin-top: 8px !important;
                  display: block !important;
                  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease !important;
                  cursor: zoom-in !important;
                }

                .step-img-thumb:hover {
                  transform: scale(1.05) !important;
                  border-color: var(--accent-indigo) !important;
                  box-shadow: 0 6px 20px rgba(99, 102, 241, 0.5) !important;
                }

                /* Sleek Dark Scrollbars */
                ::-webkit-scrollbar {
                  width: 8px;
                  height: 8px;
                }
                ::-webkit-scrollbar-track {
                  background: #0b0f19;
                }
                ::-webkit-scrollbar-thumb {
                  background: #334155;
                  border-radius: 4px;
                }
                ::-webkit-scrollbar-thumb:hover {
                  background: #475569;
                }

                /* Custom Summary & Dashboard Tables */
                table[border='1'] {
                  border: 1px solid var(--border-color) !important;
                  border-radius: 12px !important;
                  overflow: hidden !important;
                  width: 100% !important;
                  max-width: 850px !important;
                  margin: 15px 0 !important;
                  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.3) !important;
                  background: var(--bg-card) !important;
                }
                table[border='1'] th {
                  background: rgba(30, 41, 59, 0.95) !important;
                  color: var(--text-muted) !important;
                  padding: 12px 18px !important;
                  font-size: 0.78rem !important;
                  text-transform: uppercase !important;
                  letter-spacing: 0.6px !important;
                  border: 1px solid var(--border-color) !important;
                }
                table[border='1'] td {
                  padding: 12px 18px !important;
                  color: var(--text-main) !important;
                  border: 1px solid rgba(255, 255, 255, 0.05) !important;
                  font-size: 0.88rem !important;
                }
                table[border='1'] tr:hover td {
                  background: rgba(99, 102, 241, 0.08) !important;
                }
                /* === Lightbox Overlay for Screenshots === */
                #img-lightbox-overlay {
                  display: none;
                  position: fixed;
                  inset: 0;
                  background: rgba(0, 0, 0, 0.88);
                  z-index: 9999;
                  align-items: center;
                  justify-content: center;
                  cursor: zoom-out;
                  backdrop-filter: blur(8px);
                  -webkit-backdrop-filter: blur(8px);
                  animation: fadeIn 0.2s ease;
                }
                @keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
                #img-lightbox-overlay.active { display: flex !important; }
                #img-lightbox-overlay img {
                  max-width: 88vw !important;
                  max-height: 88vh !important;
                  border-radius: 12px !important;
                  border: 2px solid var(--accent-indigo) !important;
                  box-shadow: 0 20px 60px rgba(0,0,0,0.8) !important;
                  cursor: default;
                  transform: none !important;
                }
                #img-lightbox-close {
                  position: absolute;
                  top: 20px;
                  right: 28px;
                  color: #ffffff;
                  font-size: 2.2rem;
                  cursor: pointer;
                  opacity: 0.8;
                  line-height: 1;
                  transition: opacity 0.2s, transform 0.2s;
                  z-index: 10000;
                }
                #img-lightbox-close:hover { opacity: 1; transform: scale(1.15); }

                /* === Progress Bars === */
                .progress {
                  background-color: rgba(255,255,255,0.08) !important;
                  border-radius: 8px !important;
                  height: 8px !important;
                  overflow: hidden;
                }
                .progress-bar.pass-bg {
                  background: linear-gradient(90deg, #10b981, #059669) !important;
                  box-shadow: 0 0 8px rgba(16, 185, 129, 0.5);
                }
                .progress-bar.fail-bg {
                  background: linear-gradient(90deg, #ef4444, #dc2626) !important;
                }
                .progress-bar.skip-bg {
                  background: linear-gradient(90deg, #f59e0b, #d97706) !important;
                }
                """;
            spark.config().setCss(customCss);

            String customJs = """
                /* === Lightbox helpers === */
                function createLightbox() {
                    if (document.getElementById('img-lightbox-overlay')) return;
                    var overlay = document.createElement('div');
                    overlay.id = 'img-lightbox-overlay';
                    overlay.innerHTML = '<span id="img-lightbox-close" title="Close">&times;</span><img id="img-lightbox-img" src="" alt="Screenshot" />';
                    document.body.appendChild(overlay);
                    overlay.addEventListener('click', function(e) {
                        if (e.target === overlay || e.target.id === 'img-lightbox-close') {
                            overlay.classList.remove('active');
                            document.getElementById('img-lightbox-img').src = '';
                        }
                    });
                    document.addEventListener('keydown', function(e) {
                        if (e.key === 'Escape') {
                            overlay.classList.remove('active');
                            document.getElementById('img-lightbox-img').src = '';
                        }
                    });
                }

                function openLightbox(src) {
                    createLightbox();
                    document.getElementById('img-lightbox-img').src = src;
                    document.getElementById('img-lightbox-overlay').classList.add('active');
                }

                /* === Convert BASE64 badge links into visible thumbnails === */
                var _convThrottle = null;
                function convertBase64Badges() {
                    if (_convThrottle) return;
                    _convThrottle = setTimeout(function() { _convThrottle = null; }, 120);
                    document.querySelectorAll("a[data-featherlight='image']:not([data-converted='true'])").forEach(function(a) {
                        var base64Src = a.getAttribute('href');
                        if (base64Src && base64Src.startsWith('data:image')) {
                            a.setAttribute('data-converted', 'true');
                            a.style.cursor = 'zoom-in';
                            a.style.display = 'inline-block';
                            var img = document.createElement('img');
                            img.src = base64Src;
                            img.className = 'r-img step-img-thumb';
                            img.alt = 'Screenshot';
                            img.title = 'Click to enlarge';
                            a.innerHTML = '';
                            a.appendChild(img);
                            a.addEventListener('click', function(e) {
                                e.preventDefault();
                                openLightbox(base64Src);
                            });
                        }
                    });
                }

                document.addEventListener('DOMContentLoaded', function() {
                    createLightbox();
                    convertBase64Badges();
                    var observer = new MutationObserver(function() { convertBase64Badges(); });
                    observer.observe(document.body, { childList: true, subtree: true });
                });
                """;
            spark.config().setJs(customJs);

            extent = new ExtentReports();
            extent.setReportUsesManualConfiguration(false);
            extent.setAnalysisStrategy(AnalysisStrategy.TEST);
            extent.attachReporter(spark);

            String env = ConfigReader.getEnv();
            extent.setSystemInfo("Project", ConfigReader.get("project") != null ? ConfigReader.get("project") : "CHRP Simulation Platform");
            extent.setSystemInfo("Release", ConfigReader.get("release") != null ? ConfigReader.get("release") : "2.3.1");
            extent.setSystemInfo("Target Environment", env != null ? env.toUpperCase() : "STAGE");
            extent.setSystemInfo("Base URL", ConfigReader.getUrl());
            extent.setSystemInfo("Browser", ConfigReader.getBrowser() != null ? ConfigReader.getBrowser().toUpperCase() : "CHROME");
            extent.setSystemInfo("Platform OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Runtime", System.getProperty("java.version"));
            BuildInfo buildInfo = new BuildInfo();
            extent.setSystemInfo("Selenium Core", buildInfo.getReleaseLabel());
            extent.setSystemInfo("Automation Framework", ConfigReader.get("framework") != null ? ConfigReader.get("framework") : "Selenium + TestNG");
            extent.setSystemInfo("Executed By", ConfigReader.get("tester") != null ? ConfigReader.get("tester") : "QC Shiva");
        }
        return extent;
    }

    public static void main(String[] args) {
        ExtentReports reports = getInstance();
        var test1 = reports.createTest("Verify Login Screen Fields and Authenticate");
        test1.info("Navigated to: " + ConfigReader.getUrl());
        test1.pass("Username entered successfully");
        test1.pass("Password entered successfully");
        test1.pass("Authentication Successful");

        var test2 = reports.createTest("Verify Governance Dashboard Screen & Layout");
        test2.info("Inspecting governance overview cards and metrics");
        test2.pass("Header and layout rendered as expected");

        var test3 = reports.createTest("Verify Organisation Applications Screen, Public Link, Tabs, Search & Review Modal");
        test3.info("Navigated to /super-admin/org-applications");
        test3.pass("Copy Public Link copied URL to clipboard");
        test3.pass("Tabs: All, Pending, Approved, Rejected validated");
        test3.pass("Application search returned correct results");

        reports.flush();
        System.out.println("ExtentReport.html generated successfully!");
    }
}
