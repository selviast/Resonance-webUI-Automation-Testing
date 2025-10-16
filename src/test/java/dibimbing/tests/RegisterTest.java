package dibimbing.tests;

import dibimbing.core.BaseTest;
import dibimbing.core.DriverManager;
import dibimbing.pages.RegisterPage;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.UUID;
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

    @Test(description = "TC017: Verifikasi register berhasil dengan email baru",
            groups = {"regression", "positive"})
    public void TC017_testRegisterRandomUser() {
        String email = DataGenerator.randomEmail();
        String name = DataGenerator.randomName();

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(name, email);

        registerPage.assertRegisterSuccess();
    }

    @Test(description = "TC022: Verifikasi register gagal dengan email dan nama yang tidak valid",
            groups = {"regression", "negative"})
    public void TC022_testRegisterRandomUserFailed() {
        String email = DataGenerator.randomEmail();
        String name = DataGenerator.randomName();

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(email, name);

        registerPage.assertRegisterFailed();
    }

    @Test(description = "TC023: Verifikasi register gagal dengan email yang sudah terdaftar",
            groups = {"regression", "negative"})
    public void TC023_testRegisterUserAlreadyExists() {
        String email = config.getProperty("test.email");
        String name = config.getProperty("test.name");

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(name, email);

        registerPage.assertRegisterUserAlreadyExisted();


    }
}


