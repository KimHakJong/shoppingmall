@echo off
echo ========================================
echo MySQL 설치 및 환경설정 스크립트
echo ========================================

echo.
echo 1. MySQL 서비스 상태 확인...
sc query MySQL80 > nul 2>&1
if %errorlevel% equ 0 (
    echo MySQL 서비스가 이미 설치되어 있습니다.
    echo MySQL 서비스를 시작합니다...
    net start MySQL80
) else (
    echo MySQL 서비스가 설치되어 있지 않습니다.
    echo MySQL을 먼저 설치해주세요.
    echo https://dev.mysql.com/downloads/installer/
    pause
    exit /b 1
)

echo.
echo 2. MySQL 연결 테스트...
mysql -u root -p1234 -e "SELECT 1;" > nul 2>&1
if %errorlevel% equ 0 (
    echo MySQL 연결 성공!
) else (
    echo MySQL 연결 실패. 비밀번호를 확인해주세요.
    echo application-mysql.yml의 비밀번호와 일치하는지 확인하세요.
    pause
    exit /b 1
)

echo.
echo 3. 데이터베이스 생성...
mysql -u root -p1234 -e "CREATE DATABASE IF NOT EXISTS shopping_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
if %errorlevel% equ 0 (
    echo shopping_mall 데이터베이스 생성 완료!
) else (
    echo 데이터베이스 생성 실패.
    pause
    exit /b 1
)

echo.
echo 4. 테이블 확인...
mysql -u root -p1234 shopping_mall -e "SHOW TABLES;" > nul 2>&1
if %errorlevel% equ 0 (
    echo 데이터베이스 연결 성공!
) else (
    echo 데이터베이스 연결 실패.
    pause
    exit /b 1
)

echo.
echo ========================================
echo MySQL 설정 완료!
echo ========================================
echo.
echo 이제 다음 명령어로 애플리케이션을 실행하세요:
echo mvn spring-boot:run -Dspring.profiles.active=mysql
echo.
pause 