package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.pages.DashboardPage;
import dibimbing.pages.LoginPage;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {
    @Test(description = "TC024: Verifikasi berhasil Logout",
            groups = {"regression", "test-log"})
    public void tc024_testLogout() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        DashboardPage dashboardPage= new DashboardPage(DriverManager.getDriver());

        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
        dashboardPage.logout();
        dashboardPage.assertIsLoggedOut();
    }

}
