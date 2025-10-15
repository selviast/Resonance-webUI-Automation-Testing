package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.core.TestUtils;
import dibimbing.pages.LoginPage;
import dibimbing.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
//    Example for static define data provider
//    @DataProvider(name = "wrongLoginData")
//    public Object[][] wrongLoginData() {
//        return new Object[][]{
//                {"sdfgdsg@gmail.com", "sdfgdsfg"},
//                {"dfgdsgdgd@gmail.com", "bmvbmbvmbv"}
//        };
//    }
//
//    @Test(groups = {"regression", "negative"}, dataProvider = "wrongLoginData")
//    public void testFailedLogin(String username, String password) {
//        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
//
//        loginPage.navigateToSauceDemo();
//        loginPage.login(username, password);
//        loginPage.assertLoginAlert();
//    }

    @DataProvider(name = "wrongLoginData")
    public Object[][] wrongLoginData() {
        return TestUtils.getTestData("src/test/resources/testdata/testdata.xlsx", "wrong-login-data");
    }

    @Test(groups = {"regression", "negative", "test-log"}, dataProvider = "wrongLoginData")
    public void testFailedLogin(String email, String password) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        loginPage.login(email, password);
        loginPage.assertLoginFailed();
    }

    @Test(groups = {"regression", "test-log"})
    public void testLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(config.getProperty("test.email"), config.getProperty("test.password"));
        loginPage.assertLoginSuccess();
    }
}
//
//    @Test(groups = {"regression", "test-log"})
//    public void testLogin1() {
//        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
//        loginPage.login("standard_user", "secret_sauce");
//    }

//    @Test(groups = {"smoke", "p1"})
//    public void testLogin2() {
//        WebDriver driver = DriverManager.getDriver();
//        LoginPage loginPage = new LoginPage(driver);
//        ProductPage productPage = new ProductPage(driver);
//
//        loginPage.login("standard_user", "secret_sauce");
//        productPage.assertProductPage();
//    }
//
//    @Test(groups = {"smoke", "p2"})
//    public void testLogin3() {
//        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
//        loginPage.login("standard_user", "secret_sauce");
//    }
//}
