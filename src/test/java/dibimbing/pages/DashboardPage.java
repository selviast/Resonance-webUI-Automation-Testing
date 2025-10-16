package dibimbing.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class DashboardPage extends BasePage{
    private static final Logger log = LogManager.getLogger(CreateTicketPage.class);

    @FindBy (xpath = "//p[@class='chakra-text css-oqyjp1' and contains(text(),'Active Issue')]")
    private WebElement titleDashboard;

    @FindBy(id = "btn-create-ticket")
    private WebElement createTicketButton;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCreateTicketPage() {
        createTicketButton.click();
        log.info("Click Button Create Ticket");
    }

    public void assertDashboardTitleVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement title = wait.until(ExpectedConditions.visibilityOf(titleDashboard));

        Assert.assertTrue(title.isDisplayed(), "Dashboard title tidak tampil");
        log.info("Dashboard title '" + titleDashboard + "' tampil di halaman");
    }

    public boolean isTicketTitleListed(String ticketTitle) {
        try {
            // tunggu sampai tiket dengan judul itu muncul //*[contains(text(),"error message")]
            By ticketLocator = By.xpath("//*[contains(text(), '" + ticketTitle +"')]");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement ticketElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ticketLocator));

            log.info("Ticket ditemukan di dashboard: {}", ticketTitle);
            return ticketElement.isDisplayed();
        } catch (TimeoutException e) {
            log.warn("Ticket tidak ditemukan di dashboard: {}", ticketTitle);
            return false;
        }
    }

}
