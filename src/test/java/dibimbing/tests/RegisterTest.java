package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.core.TestUtils;
import dibimbing.pages.LoginPage;
import dibimbing.pages.ProductPage;
import dibimbing.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.UUID;
import java.util.UUID;
import java.util.Random;
public class RegisterTest extends BaseTest {

    public class DataGenerator {

        public static String randomEmail() {
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            return "user" + uuid + "@mail.com";
        }

        public static String randomName() {
            return "Name" + new Random().nextInt(10000); // contoh: Pass@1234
        }
    }

//
//    @Test(groups = {"regression", "negative"}, dataProvider = "wrongLoginData")
//    public void testFailedLogin(String username, String password) {
//        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
//
//        loginPage.navigateToSauceDemo();
//        loginPage.login(username, password);
//        loginPage.assertLoginAlert();
//    }

    //    @DataProvider(name = "wrongLoginData")
//    public Object[][] wrongLoginData() {
//        return TestUtils.getTestData("src/test/resources/testdata/testdata.xlsx", "wrong-login-data");
//    }
    @Test(groups = {"regression", "positive"})
    public void testRegisterRandomUser() {
        String email = DataGenerator.randomEmail();
        String name = DataGenerator.randomName();

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(name, email);

        // validasi berhasil register, misal toast atau redirect
        registerPage.assertRegisterSuccess();
    }

    @Test(groups = {"regression", "negative"})
    public void testRegisterRandomUserFailed() {
        String email = DataGenerator.randomEmail();
        String name = DataGenerator.randomName();

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(email, name);

        // validasi berhasil register, misal toast atau redirect
        registerPage.assertRegisterFailed();
    }
    @Test(groups = {"regression", "negative"})
    public void testRegisterUserAlreadyExists() {
        String email = config.getProperty("test.email");
        String name = config.getProperty("test.name");

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(name, email);

        // validasi berhasil register, misal toast atau redirect
        registerPage.assertRegisterUserAlreadyExisted();
    }
}


