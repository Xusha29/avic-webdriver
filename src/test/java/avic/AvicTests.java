package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertTrue;

public class AvicTests {

    private static final String NEXT_BUTTON = "//button[@id='nextBtn']";
    private static final String FORM_DIRECTOR = "//form[@data-recipient-name='Директору магазина']";
    private static final String FORM_SERVICE = "//form[@data-recipient-name='Служба поддержки']";

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkThatUrlContainsDiscount() {
        driver.findElement(xpath("//div[@class='top-links__left flex-wrap']//a[@href='/discount']")).click();

        assertTrue(driver.getCurrentUrl().contains("discount"));
    }

    @Test(priority = 2)
    public void checkTheFunctionalityOfTheLetterToTheDirector() {
        driver.findElement(By.xpath("//a[contains(text(),'Письмо директору')]")).click();
        driver.findElement(By.xpath(FORM_DIRECTOR + "//input[@class='validate'][@name='user_name']"))
                .sendKeys("Oksana", Keys.ENTER);
        driver.findElement(By.xpath(FORM_DIRECTOR + "//input[@class='validate'][@name='user_email']"))
                .sendKeys("Oksana@test.com", Keys.ENTER);
        driver.findElement(By.xpath(FORM_DIRECTOR + "//textarea[@class='validate'][@name='content']"))
                .sendKeys("Hi!", Keys.ENTER);
        driver.findElement(By.xpath(FORM_DIRECTOR + "//button[@type='submit']")).click();

        String conformationMessage = driver.findElement(By.xpath("//div[@class='ttl color-green']")).getText();
        Assert.assertEquals(conformationMessage, "Сообщение успешно отправлено");
    }

    @Test(priority = 3)
    public void checkTheFunctionalitySendMessage() {
        driver.findElement(By.xpath("//div[@class='footer-contact__left']//button[contains(@class, 'addMessage_btn')]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Служба поддержки')]")).click();
        driver.findElement(By.xpath(FORM_SERVICE + "//input[@class='validate'][@name='user_name']"))
                .sendKeys("Oksana", Keys.ENTER);
        driver.findElement(By.xpath(FORM_SERVICE + "//input[@class='validate'][@name='user_email']"))
                .sendKeys("Oksana@test.com", Keys.ENTER);
        driver.findElement(By.xpath(FORM_SERVICE + "//textarea[@class='validate'][@name='content']"))
                .sendKeys("Hello", Keys.ENTER);
        driver.findElement(By.xpath(FORM_SERVICE + "//button[@type='submit']")).click();

        String conformationMessage = driver.findElement(By.xpath("//div[@class='ttl color-green']")).getText();
        Assert.assertEquals(conformationMessage, "Сообщение успешно отправлено");
    }

    @Test(priority = 4)
    public void checkTheFunctionalityTradeInScore() {
        driver.findElement(By.xpath("//a[@class='header-top__item'][contains(text(),'Trade-in оценка')]")).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath("//label[contains(text(),'Устройство включается?')]/following-sibling::div/button[contains(text(),'Да')]")).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath("//label[contains(text(),'Все функции устройства работают?')]/following-sibling::div/button[contains(text(),'Да')]")).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath("//button[contains(text(),'Дисплей и корпус не имеет царапин и сколов')]")).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath("//button[contains(text(),'Только устройство')]")).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath("//input[@class='validate'][@name='name']")).sendKeys("Oksana", Keys.ENTER);
        driver.findElement(By.xpath("//input[@class='validate'][@name='phone']")).sendKeys("0630000000", Keys.ENTER);
        driver.findElement(By.xpath("//div/button[contains(text(),'Оформить заявку')]")).click();

        String conformationMessage = driver.findElement(By.xpath("//div[@class='right-h']//span[@class='ttl']")).getText();
        Assert.assertEquals(conformationMessage, "Благодарим за заказ");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
