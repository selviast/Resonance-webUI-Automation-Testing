package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.core.TestUtils;
import dibimbing.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "wrongLoginData")
    public Object[][] wrongLoginData() {
        return TestUtils.getTestData("src/test/resources/testdata/testdata.xlsx", "wrong-login-data");
    }

    @Test(description = "TC002: Verifikasi login berhasil dengan email dan password yang salah",
            groups = {"regression", "negative", "test-log"}, dataProvider = "wrongLoginData")
    public void TC002_testFailedLogin(String email, String password) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        loginPage.login(email, password);
        loginPage.assertLoginFailed();
    }

    @Test(description = "TC001: Verifikasi login berhasil dengan email dan password valid",
            groups = {"regression", "test-log"})

    public void TC001_testLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
    }
}