#!/usr/bin/env bash
# ==============================================================================
# Dynamic Test Suite Execution Runner (Bash / CI / Linux / macOS)
# CHRP Simulation Platform - Multi-Role Automation Framework
# ==============================================================================
# Usage:
#   ./scripts/run-tests.sh <suite> <environment> <portal> [--confirm-prod] [additional maven args]
#
# Examples:
#   ./scripts/run-tests.sh smoke stage all
#   ./scripts/run-tests.sh regression stage superadmin
#   ./scripts/run-tests.sh sanity prod clientportal --confirm-prod
#   ./scripts/run-tests.sh e2e stage all -Dheadless=true
# ==============================================================================

set -eo pipefail

SUITE_INPUT="${1:-}"
ENV_INPUT="${2:-}"
PORTAL_INPUT="${3:-}"
shift 3 2>/dev/null || true

# Normalize to lowercase
SUITE="$(echo "$SUITE_INPUT" | tr '[:upper:]' '[:lower:]' | xargs)"
ENV="$(echo "$ENV_INPUT" | tr '[:upper:]' '[:lower:]' | xargs)"
PORTAL="$(echo "$PORTAL_INPUT" | tr '[:upper:]' '[:lower:]' | xargs)"

# Usage helper
show_usage() {
    echo ""
    echo "=========================================================================="
    echo "  CHRP Test Suite Execution Runner"
    echo "=========================================================================="
    echo "Usage:"
    echo "  $0 <test_suite> <environment> <portal> [--confirm-prod] [maven options]"
    echo ""
    echo "Parameters:"
    echo "  <test_suite>    : Smoke | Sanity | E2E | Regression"
    echo "  <environment>   : Stage | Prod"
    echo "  <portal>        : All | Superadmin | Clientportal"
    echo "  --confirm-prod  : Required when environment is 'Prod' (safety gate)"
    echo "=========================================================================="
    echo ""
}

# 1. Validation: Suite
case "$SUITE" in
    regression|smoke|sanity|e2e)
        ;;
    *)
        echo "[ERROR] Invalid test suite: '$SUITE_INPUT'"
        echo "        Allowed values: regression, smoke, sanity, e2e"
        show_usage
        exit 1
        ;;
esac

# 2. Validation: Environment
case "$ENV" in
    stage|prod)
        ;;
    *)
        echo "[ERROR] Invalid target environment: '$ENV_INPUT'"
        echo "        Allowed values: stage, prod"
        show_usage
        exit 1
        ;;
esac

# 3. Validation: Portal
case "$PORTAL" in
    all|superadmin|clientportal)
        ;;
    *)
        echo "[ERROR] Invalid portal scope: '$PORTAL_INPUT'"
        echo "        Allowed values: all, superadmin, clientportal"
        show_usage
        exit 1
        ;;
esac

# 4. Production Safeguard Validation
EXTRA_MVN_ARGS=()
PROD_CONFIRMED=false

while [[ $# -gt 0 ]]; do
    case "$1" in
        --confirm-prod|-confirm-prod)
            PROD_CONFIRMED=true
            shift
            ;;
        *)
            EXTRA_MVN_ARGS+=("$1")
            shift
            ;;
    esac
done

if [ "$ENV" = "prod" ]; then
    if [ "$PROD_CONFIRMED" != "true" ] && [ "${PROD_CONFIRMATION:-false}" != "true" ]; then
        echo ""
        echo "**************************************************************************"
        echo "[SAFETY ABORT] Production execution requested without confirmation."
        echo "Production execution can affect live data. You must pass '--confirm-prod'"
        echo "or set PROD_CONFIRMATION=true to proceed."
        echo "**************************************************************************"
        echo ""
        exit 2
    fi
fi

# 5. Resolve TestNG Suite XML
SUITE_XML="src/test/resources/suites/${SUITE}-${PORTAL}.xml"

if [ ! -f "$SUITE_XML" ]; then
    echo "[ERROR] Resolved TestNG suite file does not exist: $SUITE_XML"
    exit 3
fi

# 6. Display Configuration Banner
echo ""
echo "=========================================================================="
echo "  CHRP Test Execution Configuration"
echo "=========================================================================="
echo "  Test Suite         : $SUITE"
echo "  Target Environment : $ENV"
echo "  Portal Scope       : $PORTAL"
echo "  Resolved Suite XML : $SUITE_XML"
echo "  Extra Maven Args   : ${EXTRA_MVN_ARGS[*]:-None}"
echo "=========================================================================="
echo ""

# 7. Execute Maven
CMD=("mvn" "test" "-DsuiteXmlFile=$SUITE_XML" "-Denv=$ENV")
if [ ${#EXTRA_MVN_ARGS[@]} -gt 0 ]; then
    CMD+=("${EXTRA_MVN_ARGS[@]}")
fi

echo "Executing: ${CMD[*]}"
"${CMD[@]}"
