package dibimbing.pages;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement shoppingCartButton;

    public void assertProductPage() {
        Assertions.assertThat(shoppingCartButton.isDisplayed())
                .as("Product page should have shopping cart button")
                .isTrue();
    }
}
