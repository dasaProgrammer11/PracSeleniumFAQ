package com.nord.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class FAQPage {
    private WebDriverWait wait;
    private WebDriver driver;
    private Logger logger;

    public FAQPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public LatestNewsCard getLatestNewsWidget() {
        logger.info("getting latest news widget");
        return new LatestNewsCard();
    }

    public class LatestNewsCard {
        private final By latestNewsLinks = By.cssSelector("section.content-card span.fakeArticle>ul>li>a");
        private final By latestNewsLinkHeader = By.cssSelector("section.content-card h2");
        private ArrayList<WebElement> latestNewsLinkList = new ArrayList<>();

        public LatestNewsCard getAllLatestNewsLink() {
            latestNewsLinkList.addAll(driver.findElements(latestNewsLinks));
            return this;
        }

        public int countLatestNewsLink() {
            return latestNewsLinkList.size();
        }

        public void printLatestNewsLinkText() {
            for (WebElement news : latestNewsLinkList) {
                logger.info("news text::::"+news.getText());
            }
        }

        public String getAttribute(String attributeName, int elementNumber) {
            return latestNewsLinkList.get(elementNumber - 1).getAttribute(attributeName);
        }

        public String getCardHeaderText() {
            return driver.findElement(latestNewsLinkHeader).getText();
        }

        public void clickNthLatestNews(int newsNumber) {
            String redirectionUrl = getAttribute("href", newsNumber);
            latestNewsLinkList.get(newsNumber - 1).click();
            wait.until(ExpectedConditions.urlMatches(redirectionUrl));
        }

        public String getRedirectedUrl() {
            return driver.getCurrentUrl();
        }
    }
}
