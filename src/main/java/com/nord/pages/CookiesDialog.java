package com.nord.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookiesDialog {
    private WebDriverWait wait;
    private WebDriver driver;
    private Logger logger;
    private final By ACCEPT_COOKIES = By.cssSelector("div.wscrBannerContentInner a.wscrOk");


    public CookiesDialog(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public FAQPage acceptAllCookies() {
        logger.info("clicking cookies");
        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIES));
        driver.findElement(ACCEPT_COOKIES).click();
        return new FAQPage(driver, wait);
    }
}
