package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.pages.CreateTicketPage;
import dibimbing.pages.DashboardPage;
import dibimbing.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateTicketTest extends BaseTest {
    @Test(description = "TC003: Verifikasi berhasil akses Create Ticket Page",
            groups = {"regression", "test-log"})
    public void tc003_navigateToCreateTicketPage() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        CreateTicketPage createTicketPage = new CreateTicketPage(DriverManager.getDriver());
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
        dashboardPage.navigateToCreateTicketPage();
        createTicketPage.assertNavigateCreateTicketSuccess();
    }

    @Test(description = "TC004: Verifikasi berhasil Create Ticket Page Public",
            groups = {"regression", "test-log"})
    public void tc004_createTicketPublic() {

        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        CreateTicketPage createTicketPage = new CreateTicketPage(DriverManager.getDriver());
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
        dashboardPage.navigateToCreateTicketPage();
        createTicketPage.assertNavigateCreateTicketSuccess();
        createTicketPage.setTitleCreateTicket();
        createTicketPage.setInputTicketDescription();
        createTicketPage.setUploadFileInput();
        createTicketPage.setCheckboxPublic();
        createTicketPage.setSubmitTicket();
        createTicketPage.assertCreateTicketSuccess();

        softAssert.assertAll();

    }

    @Test(description = "TC005: Verifikasi berhasil Create Ticket Page Private",
            groups = {"regression", "test-log"})
    public void tc005_createTicketPrivate() {
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        CreateTicketPage createTicketPage = new CreateTicketPage(DriverManager.getDriver());
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());

        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
        dashboardPage.navigateToCreateTicketPage();
        createTicketPage.assertNavigateCreateTicketSuccess();
        createTicketPage.setTitleCreateTicket();
        createTicketPage.setInputTicketDescription();
        createTicketPage.setUploadFileInput();
        createTicketPage.setCheckboxPrivate();
        createTicketPage.setSubmitTicket();
        createTicketPage.assertCreateTicketSuccess();
        softAssert.assertAll();

    }
}
