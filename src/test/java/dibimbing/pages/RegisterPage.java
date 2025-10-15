package dibimbing.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class RegisterPage extends BasePage{
    private static final Logger log = LogManager.getLogger(RegisterPage.class);

    @FindBy(id = "input-name-register")
    private WebElement nameInput;

    @FindBy(id = "input-email-register")
    private WebElement emailInput;

    @FindBy(id = "btn-register")
    private WebElement registerButton;

    @FindBy(id = "link-register")
    private WebElement buatAkunLink;

//    @FindBy(xpath = "//div[@data-status='success']")
//    private WebElement registerToastSuccess;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Method untuk register user baru
     */
    public void register(String name, String email) {
        buatAkunLink.click();
        log.info("Registering email: {}", email);

        nameInput.sendKeys(name);
        emailInput.sendKeys(email);
        registerButton.click();
    }

    /**
     * Assertion untuk memastikan register berhasil
     * Bisa cek toast message atau redirect ke halaman tertentu
     */
    public void assertRegisterSuccess() {

        WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@data-status='success' and contains(text(),'Berhasil')]")
                ));
        Assert.assertTrue(toast.isDisplayed(), "Toast 'Registration Successful' muncul");
        log.info("Register berhasil, toast muncul");

    }

    public void assertRegisterFailed() {
        WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@data-status='error' and contains(text(),'Wrong')]")
                ));
        Assert.assertTrue(toast.isDisplayed(), "Toast 'Registration Failed' muncul");
        log.info("Register gagal, toast tampil");
    }

    public void assertRegisterUserAlreadyExisted() {
        WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@data-status='error' and contains(text(),'Exists')]")
                ));
        Assert.assertTrue(toast.isDisplayed(), "Toast 'Registration Failed' muncul");
        log.info("Register gagal, toast tampil User Already Exists");
    }
}
