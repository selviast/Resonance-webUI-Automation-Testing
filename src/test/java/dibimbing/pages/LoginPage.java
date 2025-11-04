package dibimbing.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "input-email-login")
    private WebElement emailInput;

    @FindBy(id = "input-password-login")
    private WebElement passwordInput;

    @FindBy(id = "btn-login")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        log.info("Logging in with email: {}", email);

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public void assertLoginSuccess() {
        try {
            WebElement dashboardHeader = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[@class='chakra-text css-oqyjp1' and contains(text(),'Active Issue')]")
                    ));

            // Assert element muncul → berarti homepage tampil
            Assert.assertTrue(dashboardHeader.isDisplayed(), "Dashboard tampil setelah login");
            log.info("Login berhasil, dashboard tampil");
        } catch (AssertionError e) {
            log.error("Login gagal: user tidak diarahkan ke dashboard");
            Assert.fail("Login gagal: user tidak diarahkan ke dashboard");
        }
    }

    public void assertLoginFailed() {
        try {
            WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[text()='Invalid Credentials']")));
            Assert.assertTrue(toast.isDisplayed(), "Toast 'Invalid Credentials' muncul");
            log.info("Login gagal, pesan error tampil");
        } catch (Exception e) {
            log.error("Login sukses tapi seharusnya gagal");
            Assert.fail("Login gagal tapi toast tidak muncul");
        }
    }




}
