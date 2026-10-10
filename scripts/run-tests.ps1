<#
.SYNOPSIS
    Dynamic Test Suite Execution Runner for PowerShell (Windows / Local / CI)
    CHRP Simulation Platform - Multi-Role Automation Framework

.DESCRIPTION
    Validates input parameters (Test Suite, Environment, Portal), enforces production safeguards,
    resolves the corresponding TestNG XML suite file, and triggers Maven test execution.

.PARAMETER Suite
    Test suite to execute: regression | smoke | sanity | e2e

.PARAMETER Environment
    Target environment: stage | prod

.PARAMETER Portal
    Portal scope: all | superadmin | clientportal

.PARAMETER ConfirmProd
    Mandatory switch when targeting 'prod' environment to confirm live execution.

.PARAMETER Headless
    Run browser in headless mode.

.PARAMETER Browser
    Target browser (default: chrome).

.PARAMETER AdditionalArgs
    Additional Maven arguments passed directly to the mvn command line.

.EXAMPLE
    .\scripts\run-tests.ps1 -Suite smoke -Environment stage -Portal all
    .\scripts\run-tests.ps1 -Suite regression -Environment stage -Portal superadmin -Headless
    .\scripts\run-tests.ps1 -Suite sanity -Environment prod -Portal clientportal -ConfirmProd
#>

[CmdletBinding()]
param (
    [Parameter(Position = 0, Mandatory = $false)]
    [string]$Suite,

    [Parameter(Position = 1, Mandatory = $false)]
    [string]$Environment,

    [Parameter(Position = 2, Mandatory = $false)]
    [string]$Portal,

    [switch]$ConfirmProd,
    [switch]$Headless,
    [string]$Browser = "chrome",
    [string[]]$AdditionalArgs = @()
)

function Show-Usage {
    Write-Host ""
    Write-Host "==========================================================================" -ForegroundColor Cyan
    Write-Host "  CHRP Test Suite Execution Runner (PowerShell)" -ForegroundColor Cyan
    Write-Host "==========================================================================" -ForegroundColor Cyan
    Write-Host "Usage:"
    Write-Host "  .\scripts\run-tests.ps1 -Suite <suite> -Environment <env> -Portal <portal> [-ConfirmProd] [-Headless]"
    Write-Host ""
    Write-Host "Parameters:"
    Write-Host "  -Suite        : regression | smoke | sanity | e2e"
    Write-Host "  -Environment  : stage | prod"
    Write-Host "  -Portal       : all | superadmin | clientportal"
    Write-Host "  -ConfirmProd  : Mandatory when -Environment prod is selected"
    Write-Host "  -Headless     : Run browser in headless mode"
    Write-Host "  -Browser      : chrome (default) | firefox | edge"
    Write-Host "==========================================================================" -ForegroundColor Cyan
    Write-Host ""
}

# 1. Parameter Validation
if ([string]::IsNullOrWhiteSpace($Suite) -or [string]::IsNullOrWhiteSpace($Environment) -or [string]::IsNullOrWhiteSpace($Portal)) {
    Write-Host "[ERROR] Missing required parameters." -ForegroundColor Red
    Show-Usage
    exit 1
}

$cleanSuite = $Suite.Trim().ToLower()
$cleanEnv = $Environment.Trim().ToLower()
$cleanPortal = $Portal.Trim().ToLower()

$validSuites = @("regression", "smoke", "sanity", "e2e")
if ($validSuites -notcontains $cleanSuite) {
    Write-Host "[ERROR] Invalid suite: '$Suite'. Allowed values: $($validSuites -join ', ')" -ForegroundColor Red
    Show-Usage
    exit 1
}

$validEnvs = @("stage", "prod")
if ($validEnvs -notcontains $cleanEnv) {
    Write-Host "[ERROR] Invalid environment: '$Environment'. Allowed values: $($validEnvs -join ', ')" -ForegroundColor Red
    Show-Usage
    exit 1
}

$validPortals = @("all", "superadmin", "clientportal")
if ($validPortals -notcontains $cleanPortal) {
    Write-Host "[ERROR] Invalid portal: '$Portal'. Allowed values: $($validPortals -join ', ')" -ForegroundColor Red
    Show-Usage
    exit 1
}

# 2. Production Safety Guardrail
if ($cleanEnv -eq "prod") {
    $envConfirmed = $ConfirmProd -or ($env:PROD_CONFIRMATION -eq "true")
    if (-not $envConfirmed) {
        Write-Host ""
        Write-Host "**************************************************************************" -ForegroundColor Yellow
        Write-Host "[SAFETY ABORT] Production execution requested without confirmation." -ForegroundColor Red
        Write-Host "Production execution can affect live data. You must pass '-ConfirmProd'" -ForegroundColor Yellow
        Write-Host "or set PROD_CONFIRMATION=true environment variable to proceed." -ForegroundColor Yellow
        Write-Host "**************************************************************************" -ForegroundColor Yellow
        Write-Host ""
        exit 2
    }
}

# 3. Resolve TestNG Suite XML
$suiteXml = "src/test/resources/suites/$cleanSuite-$cleanPortal.xml"

if (-not (Test-Path $suiteXml)) {
    Write-Host "[ERROR] Resolved TestNG suite file does not exist: $suiteXml" -ForegroundColor Red
    exit 3
}

# 4. Display Execution Configuration Banner
Write-Host ""
Write-Host "==========================================================================" -ForegroundColor Green
Write-Host "  CHRP Test Execution Configuration" -ForegroundColor Green
Write-Host "==========================================================================" -ForegroundColor Green
Write-Host "  Test Suite         : $cleanSuite"
Write-Host "  Target Environment : $cleanEnv"
Write-Host "  Portal Scope       : $cleanPortal"
Write-Host "  Resolved Suite XML : $suiteXml"
Write-Host "  Browser            : $Browser"
Write-Host "  Headless Mode      : $(if ($Headless) { 'Yes' } else { 'No' })"
Write-Host "==========================================================================" -ForegroundColor Green
Write-Host ""

# 5. Build Maven Arguments
$mvnArgs = @(
    "test",
    "-DsuiteXmlFile=$suiteXml",
    "-Denv=$cleanEnv",
    "-Dbrowser=$Browser"
)

if ($Headless) {
    $mvnArgs += "-Dheadless=true"
}

if ($AdditionalArgs.Count -gt 0) {
    $mvnArgs += $AdditionalArgs
}

Write-Host "Executing: mvn $($mvnArgs -join ' ')" -ForegroundColor Cyan
& mvn $mvnArgs
$exitCode = $LASTEXITCODE

if ($exitCode -ne 0) {
    Write-Host "[WARNING] Test run finished with non-zero exit code: $exitCode" -ForegroundColor Yellow
} else {
    Write-Host "[SUCCESS] Test run completed successfully." -ForegroundColor Green
}

exit $exitCode
