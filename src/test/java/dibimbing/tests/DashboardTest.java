package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.pages.DashboardPage;
import dibimbing.pages.LoginPage;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {
    @Test(description = "TC009: Verifikasi berhasil search ticket by keyword",
            groups = {"regression"})
    public void tc009_searchTicketByKeyword(){
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
        dashboardPage.setInputSearchTicket("upload");
        dashboardPage.assertSearchTicketSuccess();
    }
    @Test(description = "TC011: Verifikasi sorting order newest",
            groups = {"regression"})
    public void tc011_checkFilterByNewest(){
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
        dashboardPage.setSortingByNewest();
        dashboardPage.areTicketsSortedByNewest();
    }
}
