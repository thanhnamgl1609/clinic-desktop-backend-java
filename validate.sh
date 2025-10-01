#!/bin/bash
# Validation script to verify the project setup

echo "========================================="
echo "Clinic Backend Project Validation Script"
echo "========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print success
success() {
    echo -e "${GREEN}✓${NC} $1"
}

# Function to print error
error() {
    echo -e "${RED}✗${NC} $1"
}

# Function to print info
info() {
    echo -e "${YELLOW}→${NC} $1"
}

echo "1. Checking Prerequisites..."
echo "----------------------------"

# Check Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    success "Java is installed (version: $JAVA_VERSION)"
else
    error "Java is not installed"
    exit 1
fi

# Check Maven
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version | head -n 1 | cut -d' ' -f3)
    success "Maven is installed (version: $MVN_VERSION)"
else
    error "Maven is not installed"
    exit 1
fi

# Check Docker (optional)
if command -v docker &> /dev/null; then
    DOCKER_VERSION=$(docker --version | cut -d' ' -f3 | sed 's/,$//')
    success "Docker is installed (version: $DOCKER_VERSION)"
else
    info "Docker is not installed (optional for development)"
fi

echo ""
echo "2. Validating Project Structure..."
echo "----------------------------------"

# Check key files and directories
if [ -f "pom.xml" ]; then
    success "pom.xml exists"
else
    error "pom.xml not found"
    exit 1
fi

if [ -f "README.md" ]; then
    success "README.md exists"
else
    error "README.md not found"
fi

if [ -d "src/main/java" ]; then
    success "Source directory exists"
else
    error "Source directory not found"
    exit 1
fi

if [ -d "build-config/dev" ]; then
    success "Docker dev configuration exists"
else
    error "Docker dev configuration not found"
fi

if [ -f "docs/QUICKSTART.md" ]; then
    success "Quick Start Guide exists"
else
    error "Quick Start Guide not found"
fi

echo ""
echo "3. Building Project..."
echo "---------------------"
info "Running: mvn clean compile"
if mvn clean compile -q; then
    success "Project compiles successfully"
else
    error "Project compilation failed"
    exit 1
fi

echo ""
echo "4. Running Tests..."
echo "------------------"
info "Running: mvn test"
if mvn test -q; then
    success "All tests pass"
else
    error "Tests failed"
    exit 1
fi

echo ""
echo "5. Creating Package..."
echo "---------------------"
info "Running: mvn package -DskipTests"
if mvn package -DskipTests -q; then
    success "Package created successfully"
    if [ -f "target/backend-0.0.1-SNAPSHOT.jar" ]; then
        JAR_SIZE=$(du -h target/backend-0.0.1-SNAPSHOT.jar | cut -f1)
        success "JAR file created (size: $JAR_SIZE)"
    fi
else
    error "Package creation failed"
    exit 1
fi

echo ""
echo "6. Validation Summary"
echo "--------------------"
echo -e "${GREEN}All validations passed!${NC}"
echo ""
echo "Project Statistics:"
echo "  - Java Files: $(find src/main/java -name '*.java' | wc -l)"
echo "  - Test Files: $(find src/test/java -name '*.java' | wc -l)"
echo "  - Lines of Java Code: $(find src -name '*.java' -exec cat {} \; | wc -l)"
echo ""
echo "Next Steps:"
echo "  1. Review the Quick Start Guide: docs/QUICKSTART.md"
echo "  2. Check the API Documentation: docs/API.md"
echo "  3. Run the application: mvn spring-boot:run"
echo "  4. Or use Docker: cd build-config/dev && docker-compose up"
echo ""
echo "Default Test Accounts:"
echo "  - Admin: admin / admin123"
echo "  - Employee: employee / employee123"
echo ""
echo "========================================="
