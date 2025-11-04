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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DashboardPage extends BasePage{
    private static final Logger log = LogManager.getLogger(CreateTicketPage.class);

    @FindBy (xpath = "//p[@class='chakra-text css-oqyjp1' and contains(text(),'Active Issue')]")
    private WebElement titleDashboard;

    @FindBy(id = "btn-create-ticket")
    private WebElement createTicketButton;

    @FindBy (id= "input-search-ticket")
    private WebElement inputSearchTicket;

    @FindBy (xpath = "//div[@class='chakra-stack css-1612603']//div[2]//a[1]//span[1]")
    private WebElement searchResult;

    @FindBy (xpath = "//div[contains(@class,'css-n0wfye')]//div[contains(@class,'css-19yw4if')]/p")
    private List<WebElement> ticketDates;

    @FindBy (id = "btn-filter-order-newest")
    private WebElement filterOrderNewest;

    @FindBy (id = "btn-open-navbar")
    private WebElement sideMenu;

    @FindBy (id = "btn-logout")
    private WebElement logoutButton;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCreateTicketPage() {
        createTicketButton.click();
        log.info("Click Button Create Ticket");
    }

//    String keywordSearch = "upload";
    public void setInputSearchTicket(String keyword){
        inputSearchTicket.sendKeys(keyword);
        log.info("Searching for keyword: '" + keyword+ "'");
    }

    public void assertSearchTicketSuccess() {
        try {
            WebElement dashboardHeader = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(searchResult) // ganti dengan element unik homepage
                    );

            // Assert element muncul → berarti hasil pencarian tampil
            Assert.assertTrue(dashboardHeader.isDisplayed(), "Hasil pencarian tampil");
            log.info("Hasil pencarian paling atas, tittle: '" + searchResult.getText() + "'");
        } catch (AssertionError e) {
            log.error("Login gagal: user tidak diarahkan ke dashboard");
            Assert.fail("Login gagal: user tidak diarahkan ke dashboard");
        }
    }

    public void setSortingByNewest(){
        filterOrderNewest.click();
        log.info("Click button order by Newest");

    }

    public List<Instant> getAllTicketDatesFlexible() {
        List<Instant> dates = new ArrayList<>();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class,'css-n0wfye')]//div[contains(@class,'css-19yw4if')]/p")
        ));
        log.info("Total ticket dates ditemukan: {}", elements.size());

        DateTimeFormatter formatterAbsolute = DateTimeFormatter.ofPattern("H:mm, dd/MM/yyyy");
//        LocalDateTime now = LocalDateTime.now();

        for (WebElement el : elements) {
            String text = el.getText().trim();

            // hapus prefix "Created " kalo ada
            if (text.startsWith("Created ")) {
                text = text.substring("Created ".length());
            }

            try {
                LocalDateTime ldt;

                // format absolute
                if (text.matches("\\d{1,2}:\\d{2}, \\d{2}/\\d{2}/\\d{4}")) {
                    ldt = LocalDateTime.parse(text, formatterAbsolute);

                } else if (text.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                    DateTimeFormatter formatterDateOnly = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    ldt = LocalDate.parse(text, formatterDateOnly).atStartOfDay();

                } else if (text.startsWith("Yesterday at")) {
                    String timePart = text.replace("Yesterday at", "").trim();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                    ldt = LocalDateTime.of(LocalDate.now().minusDays(1),
                            LocalTime.parse(timePart, timeFormatter));

                } else if (text.startsWith("Last")) {
                    String[] parts = text.split(" at ");
                    String dayOfWeekStr = parts[0].replace("Last", "").trim().toUpperCase();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                    LocalTime time = LocalTime.parse(parts[1], timeFormatter);

                    DayOfWeek targetDay = DayOfWeek.valueOf(dayOfWeekStr);
                    LocalDate today = LocalDate.now();

                    int diff = (today.getDayOfWeek().getValue() - targetDay.getValue() + 7) % 7;
                    if (diff == 0) diff = 7; // “Last Monday” saat ini Senin → mundur seminggu

                    LocalDate correctDate = today.minusDays(diff);
                    ldt = LocalDateTime.of(correctDate, time);
                }
                else {
                    log.warn("Unknown ticket date format: '{}', skipping", text);
                    continue;
                }

                dates.add(ldt.atZone(ZoneId.systemDefault()).toInstant());

            } catch (Exception e) {
                log.warn("Failed to parse ticket date '{}': {}", text, e.getMessage());
            }
        }


        return dates;
    }


    public boolean areTicketsSortedByNewest() {
        try {
            List<Instant> dates = getAllTicketDatesFlexible();
            log.info("Total ticket dates ditemukan: {}", dates.size());

            for (int i = 0; i < dates.size() - 1; i++) {
                Instant current = dates.get(i);
                Instant next = dates.get(i + 1);

                // Konversi ke WIB biar terbaca seperti di UI
                ZoneId wib = ZoneId.of("Asia/Jakarta");
                log.info("Comparing ticket {}: {} vs {}",
                        i,
                        current.atZone(wib),
                        next.atZone(wib)
                );

                if (current.isBefore(next)) {
                    log.warn("Tickets not sorted: ticket at index {} is before ticket at index {}", i, i + 1);
                    return false;
                }


            }

            log.info("All tickets are sorted from newest to oldest");
            return true;

        } catch (Exception e) {
            log.error("Error while checking ticket order", e);
            return false;
        }
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

    public void logout(){
        sideMenu.click();
        log.info("Akses Side Menu");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(logoutButton));
        logoutButton.click();
        log.info("Klik Logout");
    }

    public void assertIsLoggedOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Tunggu sampai URL mengandung 'login' (atau sesuai path halaman login)
        boolean isAtLoginPage = wait.until(ExpectedConditions.urlContains("/login"));

        // Assert bahwa user benar-benar diarahkan ke halaman login
        Assert.assertTrue(isAtLoginPage, "User tidak berada di halaman login!");

        log.info("User berhasil logout dan berada di halaman login. URL: " + driver.getCurrentUrl());
    }



}
