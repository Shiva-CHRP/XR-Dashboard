package superadmin.reports;

import org.openqa.selenium.BuildInfo;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import superadmin.utils.ConfigReader;

/**
 * ExtentReportNG - Centralized ExtentReports Manager.
 * 
 * Provides dedicated methods for both Dark and Light theme report generation:
 * - {@link #getDarkInstance()}: Generates the report in Modern Dark theme.
 * - {@link #getLightInstance()}: Generates the report in Modern Light theme.
 * - {@link #getInstance()}: Reads 'report.theme' from GlobalData.properties / System properties (defaults to light).
 * - {@link #getInstance(String)}: Takes "dark" or "light" dynamically.
 */
public class ExtentReportNG {
    private static ExtentReports extent;
    private static String activeTheme = null;

    /**
     * Get or initialize the ExtentReports instance using Dark Theme.
     * @return ExtentReports configured with modern dark enterprise styling.
     */
    public static synchronized ExtentReports getDarkInstance() {
        if (extent == null || !"dark".equalsIgnoreCase(activeTheme)) {
            extent = buildReport("dark");
        }
        return extent;
    }

    /**
     * Get or initialize the ExtentReports instance using Light Theme.
     * @return ExtentReports configured with modern light enterprise styling.
     */
    public static synchronized ExtentReports getLightInstance() {
        if (extent == null || !"light".equalsIgnoreCase(activeTheme)) {
            extent = buildReport("light");
        }
        return extent;
    }

    /**
     * Get the ExtentReports instance based on configuration.
     * Reads 'report.theme' from GlobalData.properties or System properties (-Dreport.theme=dark/light).
     * Defaults to Light Theme if not specified.
     * @return Configured ExtentReports instance.
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String themeConfig = ConfigReader.get("report.theme");
            if (themeConfig != null && themeConfig.trim().equalsIgnoreCase("dark")) {
                return getDarkInstance();
            } else {
                return getLightInstance();
            }
        }
        return extent;
    }

    /**
     * Get or initialize the ExtentReports instance by specifying the theme explicitly.
     * @param theme "dark" or "light" (case-insensitive)
     * @return ExtentReports instance.
     */
    public static synchronized ExtentReports getInstance(String theme) {
        if (theme != null && theme.trim().equalsIgnoreCase("dark")) {
            return getDarkInstance();
        }
        return getLightInstance();
    }

    /**
     * Set the active theme for future getInstance() calls.
     * @param theme "dark" or "light"
     */
    public static synchronized void setTheme(String theme) {
        if (theme != null && theme.trim().equalsIgnoreCase("dark")) {
            getDarkInstance();
        } else {
            getLightInstance();
        }
    }

    /**
     * Returns the name of the currently active theme ("dark" or "light").
     * @return Active theme name.
     */
    public static String getActiveTheme() {
        return activeTheme != null ? activeTheme : "light";
    }

    /**
     * Internal factory method to build and configure ExtentReports with chosen theme.
     */
    private static ExtentReports buildReport(String theme) {
        activeTheme = theme.toLowerCase();
        boolean isDark = "dark".equalsIgnoreCase(theme);

        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setTheme(isDark ? Theme.DARK : Theme.STANDARD);
        spark.config().setReportName("⚡ CHRP Simulation Platform - Sanity Automation Dashboard");
        spark.config().setDocumentTitle("CHRP Automation Dashboard | Execution Report");
        spark.config().setTimelineEnabled(true);
        spark.config().setEncoding("UTF-8");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        spark.config().setOfflineMode(true);

        spark.config().setCss(isDark ? getDarkCss() : getLightCss());
        spark.config().setJs(getCustomJs());

        ExtentReports report = new ExtentReports();
        report.setReportUsesManualConfiguration(false);
        report.setAnalysisStrategy(AnalysisStrategy.TEST);
        report.attachReporter(spark);

        String env = ConfigReader.getEnv();
        report.setSystemInfo("Project", ConfigReader.get("project") != null ? ConfigReader.get("project") : "CHRP Simulation Platform");
        report.setSystemInfo("Release", ConfigReader.get("release") != null ? ConfigReader.get("release") : "2.3.1");
        report.setSystemInfo("Target Environment", env != null ? env.toUpperCase() : "STAGE");
        report.setSystemInfo("Report Theme", isDark ? "Dark Theme" : "Light Theme");
        report.setSystemInfo("Base URL", ConfigReader.getUrl());
        report.setSystemInfo("Browser", ConfigReader.getBrowser() != null ? ConfigReader.getBrowser().toUpperCase() : "CHROME");
        report.setSystemInfo("Platform OS", System.getProperty("os.name"));
        report.setSystemInfo("Java Runtime", System.getProperty("java.version"));
        BuildInfo buildInfo = new BuildInfo();
        report.setSystemInfo("Selenium Core", buildInfo.getReleaseLabel());
        report.setSystemInfo("Automation Framework", ConfigReader.get("framework") != null ? ConfigReader.get("framework") : "Selenium + TestNG");
        report.setSystemInfo("Executed By", ConfigReader.get("tester") != null ? ConfigReader.get("tester") : "QC Shiva");

        return report;
    }

    /**
     * Modern Enterprise Dark Theme CSS
     */
    private static String getDarkCss() {
        return """
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

            /* Prevent outer window/body scrollbar — only inner panes scroll independently */
            html, body, .app, .layout, .vcontainer, .main-content {
              height: 100% !important;
              max-height: 100vh !important;
              overflow: hidden !important;
            }

            .main-content > .view.d-none {
              display: none !important;
            }

            /* Dashboard View: vertically scrollable for charts, timeline, and system info */
            .dashboard-view {
              height: calc(100vh - 65px) !important;
              max-height: calc(100vh - 65px) !important;
              overflow-y: auto !important;
              overflow-x: hidden !important;
              padding-bottom: 50px !important;
              background: var(--bg-primary) !important;
            }

            .sysenv-container table td {
              white-space: normal !important;
              word-break: break-all !important;
            }

            /* Header Navbar — Fixed */
            .header.navbar {
              position: fixed !important;
              top: 0 !important;
              left: 0 !important;
              right: 0 !important;
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

            /* Two-Column Responsive Flexbox Layout (Always Side-by-Side) */
            .test-wrapper {
              display: flex !important;
              flex-direction: row !important;
              flex-wrap: nowrap !important;
              width: 100% !important;
              position: relative !important;
              left: 0 !important;
              top: 0 !important;
              margin: 0 !important;
              overflow: hidden !important;
            }

            .test-wrapper .test-list, .test-list {
              background: #0f172a !important;
              border-right: 1px solid var(--border-color) !important;
              width: 380px !important;
              min-width: 340px !important;
              max-width: 420px !important;
              flex: 0 0 380px !important;
              float: none !important;
              height: calc(100vh - 65px) !important;
              display: flex !important;
              flex-direction: column !important;
              overflow: visible !important;
            }

            .test-wrapper .test-list .test-list-wrapper {
              flex: 1 1 auto !important;
              height: calc(100vh - 65px - 45px) !important;
              overflow-y: auto !important;
            }

            /* Right Test Detail Content (Steps & Screenshots) — ALWAYS VISIBLE */
            .test-wrapper .test-content, .test-content, .test-content.scrollable {
              flex: 1 1 auto !important;
              width: calc(100% - 380px) !important;
              min-width: 0 !important;
              float: none !important;
              position: relative !important;
              left: 0 !important;
              top: 0 !important;
              height: calc(100vh - 65px) !important;
              overflow-y: auto !important;
              display: block !important;
              background: #0f172a !important;
            }

            .test-wrapper .test-content-tools {
              display: none !important;
            }

            /* Sticky filter toolbar inside test list */
            .test-list-tools {
              background: rgba(15, 23, 42, 0.98) !important;
              border-bottom: 1px solid var(--border-color) !important;
              padding: 12px 18px !important;
              position: sticky !important;
              top: 0 !important;
              z-index: 20 !important;
              backdrop-filter: blur(12px) !important;
              -webkit-backdrop-filter: blur(12px) !important;
              overflow: visible !important;
            }

            .test-list-tools .dropdown {
              position: relative !important;
            }

            .test-list-tools .dropdown-menu {
              right: 0 !important;
              left: auto !important;
              min-width: 150px !important;
              background: #182234 !important;
              border: 1px solid var(--border-color) !important;
              box-shadow: 0 8px 24px rgba(0, 0, 0, 0.5) !important;
              border-radius: 10px !important;
              z-index: 1050 !important;
            }

            .test-list-tools .dropdown-menu .dropdown-item {
              color: var(--text-main) !important;
              padding: 8px 16px !important;
            }

            .test-list-tools .dropdown-menu .dropdown-item:hover {
              background: var(--bg-card-hover) !important;
              color: var(--accent-indigo) !important;
            }

            .test-list-item {
              padding: 8px 0 !important;
            }

            /* Ensure filtered tests hide when status/tag filters are selected */
            .test-item.d-none, .d-none {
              display: none !important;
            }

            .test-item:not(.d-none) {
              display: block !important;
            }

            .test-item {
              background: var(--bg-card) !important;
              border: 1px solid var(--border-color) !important;
              border-radius: 12px !important;
              width: auto !important;
              box-sizing: border-box !important;
              margin: 10px 14px 10px 10px !important;
              padding: 14px 16px !important;
              box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2) !important;
              transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1) !important;
            }

            .test-item:hover {
              background: var(--bg-card-hover) !important;
              border-color: var(--border-glow) !important;
              transform: translateX(2px);
              box-shadow: 0 6px 18px rgba(0, 0, 0, 0.35) !important;
            }

            .test-item.active {
              background: linear-gradient(135deg, rgba(99, 102, 241, 0.18), rgba(139, 92, 246, 0.12)) !important;
              border: 1.5px solid var(--accent-indigo) !important;
              box-shadow: 0 0 20px rgba(99, 102, 241, 0.25) !important;
              transform: none !important;
              margin: 10px 14px 10px 10px !important;
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

            .detail-head .info {
              height: auto !important;
              min-height: 50px !important;
            }

            .detail-head .info .badge,
            .detail-head .badge {
              margin-right: 6px !important;
              margin-bottom: 4px !important;
              display: inline-flex !important;
              vertical-align: middle !important;
            }

            .detail-head h5 {
              font-size: 1.25rem !important;
              font-weight: 700 !important;
              color: #ffffff !important;
              margin-bottom: 10px;
              word-break: normal !important;
              word-wrap: break-word !important;
              overflow-wrap: break-word !important;
              hyphens: none !important;
              line-height: 1.4 !important;
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

            .test-content-detail table.table th:nth-child(2),
            .test-content-detail table.table td:nth-child(2) {
              white-space: nowrap !important;
              width: 110px !important;
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

            .card-body h3 {
              color: #ffffff !important;
              font-weight: 700 !important;
            }

            .card-body p {
              color: var(--text-muted) !important;
            }

            .card-body p.text-pass {
              color: var(--status-pass) !important;
            }

            .card-body p.text-fail {
              color: var(--status-fail) !important;
            }

            .card-footer {
              background: rgba(15, 23, 42, 0.6) !important;
              border-top: 1px solid var(--border-color) !important;
              color: var(--text-muted) !important;
              padding: 12px 20px !important;
            }

            .card-footer b {
              color: #ffffff !important;
            }

            .table.table-bordered {
              border: 1px solid var(--border-color) !important;
            }

            .table.table-bordered th, .table.table-bordered td {
              border: 1px solid rgba(255, 255, 255, 0.06) !important;
              color: #e2e8f0 !important;
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

            /* Disable duplicate PerfectScrollbar overlay rails */
            .ps-scrollbar-y-rail, .ps-scrollbar-x-rail, .ps__rail-y, .ps__rail-x {
              display: none !important;
              opacity: 0 !important;
              visibility: hidden !important;
              pointer-events: none !important;
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

            /* Responsive side-by-side override for screens <= 1200px */
            @media only screen and (max-width: 1200px) {
              .test-wrapper .test-content {
                position: relative !important;
                left: 0 !important;
                top: 0 !important;
                width: calc(100% - 340px) !important;
                display: block !important;
              }
              .test-wrapper .test-list {
                width: 340px !important;
                flex: 0 0 340px !important;
              }
            }
            """;
    }

    /**
     * Modern Enterprise Light Theme CSS
     */
    private static String getLightCss() {
        return """
            @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

            :root {
              --bg-primary: #f8fafc;
              --bg-surface: #ffffff;
              --bg-card: #ffffff;
              --bg-card-hover: #f1f5f9;
              --border-color: #e2e8f0;
              --border-glow: rgba(99, 102, 241, 0.35);
              --text-main: #0f172a;
              --text-muted: #64748b;
              --text-dim: #94a3b8;
              --accent-indigo: #4f46e5;
              --accent-purple: #7c3aed;
              --status-pass: #10b981;
              --status-fail: #ef4444;
              --status-skip: #f59e0b;
              --status-info: #3b82f6;
            }

            body, .spa, .spa.-report,
            .app, .layout, .vcontainer, .main-content,
            .test-wrapper,
            .test-list,
            .test-content,
            .test-content-detail,
            .detail-body,
            .dashboard-view,
            .dashboard-view .container-fluid {
              background-color: var(--bg-primary) !important;
              background: var(--bg-primary) !important;
              color: var(--text-main) !important;
              font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif !important;
              -webkit-font-smoothing: antialiased;
            }

            /* Prevent outer window/body scrollbar — only inner panes scroll independently */
            html, body, .app, .layout, .vcontainer, .main-content {
              height: 100% !important;
              max-height: 100vh !important;
              overflow: hidden !important;
            }

            .main-content > .view.d-none {
              display: none !important;
            }

            /* Dashboard View: vertically scrollable for charts, timeline, and system info */
            .dashboard-view {
              height: calc(100vh - 65px) !important;
              max-height: calc(100vh - 65px) !important;
              overflow-y: auto !important;
              overflow-x: hidden !important;
              padding-bottom: 50px !important;
              background: var(--bg-primary) !important;
            }

            .sysenv-container table td {
              white-space: normal !important;
              word-break: break-all !important;
            }

            /* Header Navbar — Fixed */
            .header.navbar {
              position: fixed !important;
              top: 0 !important;
              left: 0 !important;
              right: 0 !important;
              z-index: 1000 !important;
              background: rgba(255, 255, 255, 0.95) !important;
              backdrop-filter: blur(20px) !important;
              -webkit-backdrop-filter: blur(20px) !important;
              border-bottom: 1px solid var(--border-color) !important;
              box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05) !important;
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

            .header.navbar .nav-logo a, .header.navbar .nav-logo span {
              color: var(--text-main) !important;
            }

            .header.navbar .search-box {
              margin-left: 15px;
            }

            .header.navbar .search-input input {
              background: #f1f5f9 !important;
              border: 1px solid #cbd5e1 !important;
              border-radius: 20px !important;
              color: var(--text-main) !important;
              padding: 6px 16px !important;
              font-size: 0.85rem;
              transition: all 0.3s ease;
            }

            .header.navbar .search-input input:focus {
              border-color: var(--accent-indigo) !important;
              box-shadow: 0 0 12px rgba(79, 70, 229, 0.25) !important;
              background: #ffffff !important;
            }

            .header.navbar .nav-right i, .header.navbar .nav-left i {
              color: var(--text-muted) !important;
            }

            .header.navbar .nav-right .badge {
              background: linear-gradient(135deg, var(--accent-indigo) 0%, var(--accent-purple) 100%) !important;
              color: #ffffff !important;
              border-radius: 20px !important;
              font-weight: 600 !important;
              padding: 8px 18px !important;
              font-size: 0.82rem !important;
              box-shadow: 0 3px 12px rgba(79, 70, 229, 0.25) !important;
              letter-spacing: 0.3px;
              border: none !important;
            }

            /* Sidebar */
            .side-nav {
              background: #ffffff !important;
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
              color: var(--text-muted) !important;
            }

            .side-nav-menu li.nav-item:hover a {
              background: rgba(79, 70, 229, 0.08) !important;
              color: var(--accent-indigo) !important;
              transform: translateY(-1px);
            }

            .side-nav-menu li.nav-item.active a {
              background: linear-gradient(135deg, rgba(79, 70, 229, 0.15), rgba(124, 58, 237, 0.15)) !important;
              color: var(--accent-indigo) !important;
              box-shadow: 0 2px 10px rgba(79, 70, 229, 0.15);
            }

            .side-nav-menu li.nav-item a .ico i {
              font-size: 1.25rem !important;
            }

            /* Two-Column Responsive Flexbox Layout (Always Side-by-Side) */
            .test-wrapper {
              display: flex !important;
              flex-direction: row !important;
              flex-wrap: nowrap !important;
              width: 100% !important;
              position: relative !important;
              left: 0 !important;
              top: 0 !important;
              margin: 0 !important;
              overflow: hidden !important;
            }

            .test-wrapper .test-list, .test-list {
              background: #f8fafc !important;
              border-right: 1px solid var(--border-color) !important;
              width: 380px !important;
              min-width: 340px !important;
              max-width: 420px !important;
              flex: 0 0 380px !important;
              float: none !important;
              height: calc(100vh - 65px) !important;
              display: flex !important;
              flex-direction: column !important;
              overflow: visible !important;
            }

            .test-wrapper .test-list .test-list-wrapper {
              flex: 1 1 auto !important;
              height: calc(100vh - 65px - 45px) !important;
              overflow-y: auto !important;
            }

            /* Right Test Detail Content (Steps & Screenshots) — ALWAYS VISIBLE */
            .test-wrapper .test-content, .test-content, .test-content.scrollable {
              flex: 1 1 auto !important;
              width: calc(100% - 380px) !important;
              min-width: 0 !important;
              float: none !important;
              position: relative !important;
              left: 0 !important;
              top: 0 !important;
              height: calc(100vh - 65px) !important;
              overflow-y: auto !important;
              display: block !important;
              background: #f8fafc !important;
            }

            .test-wrapper .test-content-tools {
              display: none !important;
            }

            /* Sticky filter toolbar inside test list */
            .test-list-tools {
              background: rgba(248, 250, 252, 0.98) !important;
              border-bottom: 1px solid var(--border-color) !important;
              padding: 12px 18px !important;
              position: sticky !important;
              top: 0 !important;
              z-index: 20 !important;
              backdrop-filter: blur(12px) !important;
              -webkit-backdrop-filter: blur(12px) !important;
              overflow: visible !important;
            }

            .test-list-tools .dropdown {
              position: relative !important;
            }

            .test-list-tools .dropdown-menu {
              right: 0 !important;
              left: auto !important;
              min-width: 150px !important;
              background: #ffffff !important;
              border: 1px solid var(--border-color) !important;
              box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12) !important;
              border-radius: 10px !important;
              z-index: 1050 !important;
            }

            .test-list-tools .dropdown-menu .dropdown-item {
              color: var(--text-main) !important;
              padding: 8px 16px !important;
            }

            .test-list-tools .dropdown-menu .dropdown-item:hover {
              background: #f1f5f9 !important;
              color: var(--accent-indigo) !important;
            }

            .test-list-tools i, .test-list-tools a {
              color: var(--text-muted) !important;
            }

            .test-list-tools a:hover i {
              color: var(--accent-indigo) !important;
            }

            .test-list-item {
              padding: 8px 0 !important;
            }

            /* Ensure filtered tests hide when status/tag filters are selected */
            .test-item.d-none, .d-none {
              display: none !important;
            }

            .test-item:not(.d-none) {
              display: block !important;
            }

            .test-item {
              background: var(--bg-card) !important;
              border: 1px solid var(--border-color) !important;
              border-radius: 12px !important;
              width: auto !important;
              box-sizing: border-box !important;
              margin: 10px 14px 10px 10px !important;
              padding: 14px 16px !important;
              box-shadow: 0 2px 6px rgba(0, 0, 0, 0.04) !important;
              transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1) !important;
            }

            .test-item:hover {
              background: #f8fafc !important;
              border-color: #cbd5e1 !important;
              transform: translateX(2px);
              box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08) !important;
            }

            .test-item.active {
              background: linear-gradient(135deg, rgba(79, 70, 229, 0.08), rgba(124, 58, 237, 0.05)) !important;
              border: 1.5px solid var(--accent-indigo) !important;
              box-shadow: 0 2px 14px rgba(79, 70, 229, 0.18) !important;
              transform: none !important;
              margin: 10px 14px 10px 10px !important;
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
              box-shadow: 0 2px 8px rgba(16, 185, 129, 0.3) !important;
              border: 1px solid rgba(16, 185, 129, 0.5) !important;
            }

            .badge.fail-bg, .badge.log.fail-bg, .badge.danger, .status.fail {
              background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%) !important;
              color: #ffffff !important;
              box-shadow: 0 2px 8px rgba(239, 68, 68, 0.35) !important;
              border: 1px solid rgba(239, 68, 68, 0.6) !important;
            }

            .badge.skip-bg, .badge.log.skip-bg, .badge.warning, .status.skip {
              background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%) !important;
              color: #ffffff !important;
              box-shadow: 0 2px 8px rgba(245, 158, 11, 0.3) !important;
              border: 1px solid rgba(245, 158, 11, 0.5) !important;
            }

            .badge.info-bg, .badge.log.info-bg, .badge.info {
              background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%) !important;
              color: #ffffff !important;
              box-shadow: 0 2px 8px rgba(59, 130, 246, 0.25) !important;
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
              box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05) !important;
            }

            .detail-head .info {
              height: auto !important;
              min-height: 50px !important;
            }

            .detail-head .info .badge,
            .detail-head .badge {
              margin-right: 6px !important;
              margin-bottom: 4px !important;
              display: inline-flex !important;
              vertical-align: middle !important;
            }

            .detail-head h5 {
              font-size: 1.25rem !important;
              font-weight: 700 !important;
              color: var(--text-main) !important;
              margin-bottom: 10px;
              word-break: normal !important;
              word-wrap: break-word !important;
              overflow-wrap: break-word !important;
              hyphens: none !important;
              line-height: 1.4 !important;
            }

            table.table {
              background: var(--bg-card) !important;
              background-color: var(--bg-card) !important;
              border-radius: 12px !important;
              overflow: hidden;
              border: 1px solid var(--border-color) !important;
              box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04) !important;
            }

            table.table thead th {
              background: #f1f5f9 !important;
              color: var(--text-muted) !important;
              font-size: 0.75rem !important;
              font-weight: 700 !important;
              letter-spacing: 0.6px;
              text-transform: uppercase;
              border-bottom: 1px solid var(--border-color) !important;
              border-top: none !important;
              padding: 12px 16px !important;
            }

            .test-content-detail table.table th:nth-child(2),
            .test-content-detail table.table td:nth-child(2) {
              white-space: nowrap !important;
              width: 110px !important;
            }

            table.table tbody,
            table.table tbody tr,
            table.table tbody td {
              background: var(--bg-card) !important;
              background-color: var(--bg-card) !important;
              border-top: 1px solid #f1f5f9 !important;
              color: #1e293b !important;
              font-size: 0.88rem !important;
              padding: 12px 16px !important;
              vertical-align: middle !important;
            }

            tr.event-row:hover,
            tr.event-row:hover td {
              background: #f8fafc !important;
              background-color: #f8fafc !important;
            }

            /* Cards, Dashboard & Charts */
            .card {
              background: var(--bg-card) !important;
              border: 1px solid var(--border-color) !important;
              border-radius: 14px !important;
              box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05) !important;
              transition: transform 0.2s ease, box-shadow 0.2s ease;
              margin-bottom: 24px !important;
            }

            .card:hover {
              box-shadow: 0 6px 18px rgba(0, 0, 0, 0.09) !important;
              border-color: #cbd5e1 !important;
            }

            .card-header {
              background: #f8fafc !important;
              border-bottom: 1px solid var(--border-color) !important;
              padding: 14px 20px !important;
            }

            .card-header p, .card-header h6 {
              font-weight: 700 !important;
              font-size: 0.95rem !important;
              color: var(--text-main) !important;
              margin: 0;
              letter-spacing: 0.3px;
            }

            .card-body {
              padding: 20px !important;
              background: #ffffff !important;
            }

            .card-body h3 {
              color: var(--text-main) !important;
              font-weight: 700 !important;
            }

            .card-body p {
              color: var(--text-muted) !important;
            }

            .card-body p.text-pass {
              color: var(--status-pass) !important;
            }

            .card-body p.text-fail {
              color: var(--status-fail) !important;
            }

            .card-footer {
              background: #f8fafc !important;
              border-top: 1px solid var(--border-color) !important;
              color: var(--text-muted) !important;
              padding: 12px 20px !important;
            }

            .card-footer b {
              color: var(--text-main) !important;
            }

            .table.table-bordered {
              border: 1px solid var(--border-color) !important;
            }

            .table.table-bordered th, .table.table-bordered td {
              border: 1px solid var(--border-color) !important;
              color: #1e293b !important;
            }

            /* Screenshot Thumbnails (Base64) */
            img, .r-img {
              border-radius: 10px !important;
              border: 1px solid var(--border-color) !important;
              box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08) !important;
              transition: all 0.25s ease-in-out !important;
              cursor: zoom-in;
            }

            img:hover, .r-img:hover {
              transform: scale(1.05);
              border-color: var(--accent-indigo) !important;
              box-shadow: 0 6px 18px rgba(79, 70, 229, 0.25) !important;
            }

            .step-img-thumb {
              max-width: 175px !important;
              max-height: 100px !important;
              border-radius: 8px !important;
              border: 1px solid #cbd5e1 !important;
              box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08) !important;
              margin-top: 8px !important;
              display: block !important;
              background: #ffffff !important;
              transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease !important;
              cursor: zoom-in !important;
            }

            .step-img-thumb:hover {
              transform: scale(1.05) !important;
              border-color: var(--accent-indigo) !important;
              box-shadow: 0 6px 18px rgba(79, 70, 229, 0.3) !important;
            }

            /* Sleek Light Scrollbars */
            ::-webkit-scrollbar {
              width: 8px;
              height: 8px;
            }
            ::-webkit-scrollbar-track {
              background: #f1f5f9;
            }
            ::-webkit-scrollbar-thumb {
              background: #cbd5e1;
              border-radius: 4px;
            }
            ::-webkit-scrollbar-thumb:hover {
              background: #94a3b8;
            }

            /* Disable duplicate PerfectScrollbar overlay rails */
            .ps-scrollbar-y-rail, .ps-scrollbar-x-rail, .ps__rail-y, .ps__rail-x {
              display: none !important;
              opacity: 0 !important;
              visibility: hidden !important;
              pointer-events: none !important;
            }

            /* Custom Summary & Dashboard Tables */
            table[border='1'] {
              border: 1px solid var(--border-color) !important;
              border-radius: 12px !important;
              overflow: hidden !important;
              width: 100% !important;
              max-width: 850px !important;
              margin: 15px 0 !important;
              box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06) !important;
              background: var(--bg-card) !important;
            }
            table[border='1'] th {
              background: #f1f5f9 !important;
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
              border: 1px solid var(--border-color) !important;
              font-size: 0.88rem !important;
            }
            table[border='1'] tr:hover td {
              background: rgba(79, 70, 229, 0.04) !important;
            }

            /* === Lightbox Overlay for Screenshots === */
            #img-lightbox-overlay {
              display: none;
              position: fixed;
              inset: 0;
              background: rgba(15, 23, 42, 0.88);
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
              box-shadow: 0 20px 60px rgba(0,0,0,0.5) !important;
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
              background-color: #e2e8f0 !important;
              border-radius: 8px !important;
              height: 8px !important;
              overflow: hidden;
            }
            .progress-bar.pass-bg {
              background: linear-gradient(90deg, #10b981, #059669) !important;
              box-shadow: 0 0 8px rgba(16, 185, 129, 0.4);
            }
            .progress-bar.fail-bg {
              background: linear-gradient(90deg, #ef4444, #dc2626) !important;
            }
            .progress-bar.skip-bg {
              background: linear-gradient(90deg, #f59e0b, #d97706) !important;
            }

            /* Responsive side-by-side override for screens <= 1200px */
            @media only screen and (max-width: 1200px) {
              .test-wrapper .test-content {
                position: relative !important;
                left: 0 !important;
                top: 0 !important;
                width: calc(100% - 340px) !important;
                display: block !important;
              }
              .test-wrapper .test-list {
                width: 340px !important;
                flex: 0 0 340px !important;
              }
            }
            """;
    }

    /**
     * Shared JavaScript for Lightbox, Base64 thumbnail cards, and hash navigation
     */
    private static String getCustomJs() {
        return """
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

            /* Hook into toggleView and sidebar clicks to redraw Chart.js on dashboard view */
            function triggerChartRedraw() {
                setTimeout(function() {
                    window.dispatchEvent(new Event('resize'));
                    if (window.Chart && window.Chart.instances) {
                        for (var id in window.Chart.instances) {
                            try {
                                window.Chart.instances[id].resize();
                                window.Chart.instances[id].update();
                            } catch(e) {}
                        }
                    }
                }, 100);
            }

            var _origToggle = window.toggleView;
            window.toggleView = function(v) {
                if (typeof _origToggle === 'function') {
                    _origToggle(v);
                }
                if (v === 'dashboard-view') {
                    triggerChartRedraw();
                }
            };

            function checkHashRoute() {
                if (window.location.hash === '#dashboard') {
                    if (typeof toggleView === 'function') {
                        toggleView('dashboard-view');
                    }
                    triggerChartRedraw();
                } else if (window.location.hash === '#test' || window.location.hash === '#tests') {
                    if (typeof toggleView === 'function') {
                        toggleView('test-view');
                    }
                }
            }

            document.addEventListener('DOMContentLoaded', function() {
                createLightbox();
                convertBase64Badges();
                var observer = new MutationObserver(function() { convertBase64Badges(); });
                observer.observe(document.body, { childList: true, subtree: true });
                setTimeout(checkHashRoute, 120);
                window.addEventListener('hashchange', checkHashRoute);

                document.addEventListener('click', function(e) {
                    var btn = e.target.closest('#nav-dashboard, [onclick*="dashboard-view"]');
                    if (btn) {
                        triggerChartRedraw();
                    }
                }, true);
            });
            """;
    }
}
