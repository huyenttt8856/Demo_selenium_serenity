/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.common;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CommonPageObject extends PageObject {

    public WebElement explicitWait(By xpath) {
        System.out.println("--Explicit Wait--");
        WebDriverWait explicitwait = new WebDriverWait(getDriver(), 100);
        return explicitwait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
    }

    public WebElement fluentWait(By locator) {
        System.out.println("Fluant Wait ----");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(100))
                .pollingEvery(Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementVisible(By locator) {
        System.out.println("Wait for visible ----");
        WebDriverWait wait = new WebDriverWait(getDriver(), 100);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Boolean waitForElementInvisible(By locator) {
        System.out.println("Wait for Invisible ----");
        WebDriverWait wait = new WebDriverWait(getDriver(),500);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator){
        WebDriverWait wait1 = new WebDriverWait(getDriver(),60);
        return wait1.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitElementClickedByJS(By locator){
        WebDriverWait wait = new WebDriverWait(getDriver(),60);
        ExpectedCondition<Boolean> jsclick = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor)getDriver()).executeScript("argument[0].click()",locator);
            }
        };
        Boolean js = (Boolean) ((JavascriptExecutor)getDriver()).executeScript("argument[0].click()",locator);
        if (!js){
            wait.until(jsclick);
        }
    }

    public void waitUntilJSReady() {

        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        ExpectedCondition<Boolean> jsload = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").equals("complete");
            }
        };
        boolean jsReady = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString().equals("complete");
        if (!jsReady) {
            wait.until(jsload);
        }
    }

    public void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((Long) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active") == 0);
            }
        };
        boolean jqueryReady = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active==0");
        if (!jqueryReady) {
            wait.until(jQueryLoad);
        }
    }

    public void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 45);
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        ExpectedCondition<Boolean> angularLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return Boolean.valueOf(((JavascriptExecutor) getDriver()).executeScript(angularReadyScript).toString());
            }
        };
        boolean angularReady = Boolean.valueOf(((JavascriptExecutor) getDriver()).executeScript(angularReadyScript).toString());
        if (!angularReady) {
            wait.until(angularLoad);
        }
    }

    public void waitUntilJQueryReady() {
        Boolean jQueryDefined = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
            //Pre Wait for stability (Optional)
            sleep(30);

            //Wait JQuery Load
            waitForJQueryLoad();

            //Wait JS Load
            waitUntilJSReady();

            //Post Wait for stability (Optional)
            sleep(30);
        }
    }

    public void waitUntilAngularReady() {
        Boolean angularUnDefined = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return angular.element(document).injector() === undefined");
            if (!angularInjectorUnDefined) {
                //Pre Wait for stability (Optional)
                sleep(30);

                //Wait Angular Load
                waitForAngularLoad();

                //Wait JS Load
                waitUntilJSReady();

                //Post Wait for stability (Optional)
                sleep(30);
            }
        }
    }

    public void waitForLoad() {
        getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        waitUntilJQueryReady();
        waitUntilAngularReady();
    }

    public static void sleep(Integer seconds) {
        long secondsLong = (long) seconds;
        try {
            Thread.sleep(secondsLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitUntilJQueryRequestCompleted(int timeoutInSeconds) {
        new FluentWait<>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(5000)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                try {
                    JavascriptExecutor jsExec = (JavascriptExecutor) d;
                    return (Boolean) jsExec.executeScript("return jQuery.active == 0");
                } catch (Exception e) {
                    return true;
                }
            }
        });
    }

    public void waitUntilAjaxCompletes(int timeoutInSeconds) {
        new FluentWait<>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(5000)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                try {
                    JavascriptExecutor jsExec = (JavascriptExecutor) d;
                    return (Boolean) jsExec.executeScript("return Ajax.activeRequestCount == 0");
                } catch (Exception e) {
                    return true;
                }
            }
        });
    }

    public void waitForAllJSComplete() {
        waitUntilJQueryRequestCompleted(60);
        waitUntilAjaxCompletes(60);
        waitForWithRefresh();
    }
}

