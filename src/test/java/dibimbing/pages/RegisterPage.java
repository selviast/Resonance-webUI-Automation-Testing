package dibimbing.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
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

    public void register(String name, String email) {
        buatAkunLink.click();
        log.info("Registering email: {}", email);

        nameInput.sendKeys(name);
        emailInput.sendKeys(email);
        registerButton.click();
    }

    public void assertRegisterSuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Pastikan halaman selesai dimuat
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        try {
            WebElement toast = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@data-status='success' and contains(.,'Berhasil')]")
            ));

            // Scroll agar toast benar-benar terlihat di viewport (kadang hidden)
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", toast);

            wait.until(ExpectedConditions.visibilityOf(toast));
            Assert.assertTrue(toast.isDisplayed(), "Toast 'Registration Successful' muncul");
            log.info("Register berhasil, toast muncul di layar");
        } catch (TimeoutException e) {
            log.error("Toast sukses tidak muncul dalam waktu yang ditentukan");
            Assert.fail("Toast success tidak muncul — kemungkinan toast terlalu cepat atau animasi terpotong di headless mode");
        }
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
//        WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
//                .until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//div[@data-status='error' and contains(text(),'Exists')]")
//                ));
//        Assert.assertTrue(toast.isDisplayed(), "Toast 'Registration Failed' muncul");
//        log.info("Register gagal, toast tampil User Already Exists");

        try {
            WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[@data-status='error' and contains(text(),'Exists')]")
                    ));
            Assert.assertTrue(toast.isDisplayed(), "Toast 'Registration Failed' muncul");
            log.info("Register gagal, toast tampil User Already Exists");
        } catch (Exception e) {
            log.error("Register menggunakan email terpakai masih lolos");
            Assert.fail("Register berhasil, harusnya gagal");
        }
    }
}
