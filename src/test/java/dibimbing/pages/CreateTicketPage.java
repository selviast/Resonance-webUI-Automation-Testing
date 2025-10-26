package dibimbing.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class CreateTicketPage extends BasePage{

    private static final Logger log = LogManager.getLogger(CreateTicketPage.class);

    @FindBy(xpath = "//*[text()=\"Describe Your Issue\"]")
    private WebElement titleCreateTicket;

    @FindBy(id = "input-ticket-title")
    private WebElement inputTicketTitle;

    @FindBy(id = "textarea-ticket-description")
    private WebElement inputTicketDescription;

    @FindBy(id = "file-upload")
    private WebElement uploadFileInput;

    @FindBy (id = "checkbox-ticket-public")
    private WebElement checkboxPublic;

    @FindBy (id = "checkbox-ticket-private")
    private WebElement checkboxPrivate;

    @FindBy (id = "btn-submit-ticket")
    private WebElement submitTicketButton;

    @FindBy (id = "btn-dashboard")
    private WebElement backToDashboardButton;

    @FindBy(xpath = "//div[@data-status='success' and contains(text(),'Berhasil')]")
    private WebElement successToast;


    //url: https://resonance.dibimbing.id/new
    public CreateTicketPage(WebDriver driver) {
        super(driver);
    }

    public void assertNavigateCreateTicketSuccess() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/new"));

        WebElement title = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(titleCreateTicket));

        Assert.assertTrue(title.isDisplayed(), "Title Create Ticket muncul");
        log.info("Halaman Create Ticket berhasil diakses");
    }

    String titleTicket = "Test_Issue_Selvia_" + System.currentTimeMillis();

    public void setTitleCreateTicket(){
        inputTicketTitle.sendKeys(titleTicket);
    }

    public void setInputTicketDescription(){
        String desc = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        inputTicketDescription.sendKeys(desc);

    }

    public void setUploadFileInput() {
        SoftAssert softAssert = new SoftAssert();
        // lokasi
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample-image.jpg";

        try {
            uploadFileInput.sendKeys(filePath);
            log.info("Berhasil upload file: " + filePath);

            // Tambahkan validasi sederhana, misalnya pastikan elemen menunjukkan file sudah terupload
            WebElement uploadedFile = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'sample-image.jpg')]")));

            softAssert.assertTrue(uploadedFile.isDisplayed(), "File berhasil ditampilkan di UI setelah upload.");

        } catch (Exception e) {
            log.error("Gagal upload file: " + e.getMessage());
            softAssert.fail("Upload file gagal — " + e.getMessage());
        }

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            By.xpath("//*[@data-status='error']")
                    ));
            log.info("Ada Error Upload File tunggu hilang, lanjut submit.");
        } catch (Exception e) {
            log.warn("Toast error upload masih muncul setelah 10 detik, lanjut paksa ke submit.");
        }

    }

    public void setCheckboxPublic(){
        checkboxPublic.click();
        log.info("Klik checkbox Public");
    }
    public void setCheckboxPrivate(){
        checkboxPrivate.click();
        log.info("Klik checkbox Private");
    }

    public void setSubmitTicket(){
        submitTicketButton.click();
        log.info("Klik Submit Tiket");
    }

    public String getCreatedTicketTitle() {
        return titleTicket;
    }
    public void navigateToDashboardButton(){
        backToDashboardButton.click();
    }

    public void assertCreateTicketSuccess() {
        //toast berhasil create tiket
        WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(successToast));

        Assert.assertTrue(toast.isDisplayed(), "Toast 'Submit Ticket Berhasil' muncul");
        log.info("Create ticket berhasil, toast muncul");

        // navigasi ke tiket detail
        WebElement createdTicketDetail = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[contains(text(),'" + titleTicket + "')]")
                ));
        Assert.assertTrue(createdTicketDetail.isDisplayed(), "Tiket berhasil dibuat dengan judul" + titleTicket);
        log.info("Navigate to halaman detail tiket, judul " + titleTicket);

    }

}
