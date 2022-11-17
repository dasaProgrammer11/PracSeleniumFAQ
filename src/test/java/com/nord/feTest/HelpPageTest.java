package com.nord.feTest;

import com.nord.baseConfig.Configuration;
import com.nord.pages.CookiesDialog;
import com.nord.pages.FAQPage;
import com.nord.pages.FAQPage.LatestNewsCard;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;


public class HelpPageTest {
    WebDriver driver;
    WebDriverWait wait;
    Logger logger;
    CookiesDialog cookiesDialog;
    FAQPage faqPage;
    LatestNewsCard latestNewsCard;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    private void setUp() {
        logger=LoggerFactory.getLogger(this.getClass());
        WebDriverManager.chromedriver().setup();
    }

    @BeforeTest
    private void initiateDriverLoadURL() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(Configuration.BASE_URL);
        driver.manage().window().maximize();
        cookiesDialog = new CookiesDialog(driver, wait);
    }

    @Test
    private void FAQTest() {
        faqPage = cookiesDialog.acceptAllCookies();
        latestNewsCard = faqPage.getLatestNewsWidget();
        latestNewsCard.getAllLatestNewsLink().printLatestNewsLinkText();

        //Print out count of card links
        int numberOfLink = latestNewsCard.countLatestNewsLink();
        logger.info("number of link::::" + numberOfLink);
        softAssert.assertTrue(numberOfLink > 0, "Latest news count should not be 0");

        //Print out link nr 2 href
        String hrefLink = latestNewsCard.getAttribute("href", 2);
        logger.info(hrefLink);
        softAssert.assertNotNull(hrefLink, "href link should not be null");

        //Print out header of the card
        String cardHeaderText = latestNewsCard.getCardHeaderText();
        softAssert.assertEquals(cardHeaderText, "Latest news", "card header should be Latest News");

        //Click on link nr 2
        latestNewsCard.clickNthLatestNews(2);

        // Add assertion that you were redirected correctly
        softAssert.assertEquals(hrefLink, latestNewsCard.getRedirectedUrl(), "Redirect url should match");
        softAssert.assertAll();

    }

    @AfterTest
    private void afterTest() {
        driver.quit();
    }
}
