/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.common;

import net.thucydides.core.annotations.DefaultUrl;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://tiki.vn/")
public class
SearchPage extends CommonPageObject {

    @FindBy(xpath = "//input[@type='text']")
    private static WebElement TXT_SEARCH;

    By SEARCH_TXT = By.xpath("//input[@type='text']");

    @FindBy(xpath = "//input[@type='text']//following-sibling::div[2]")
    private static WebElement SEARCH_SUGGEST;

    @FindBy(xpath = "//input[@type='text']//following-sibling::button")
    private static WebElement BTN_SEARCH;

    /*Nhập từ khóa tìm kiếm
     * parameter : key (keysearch)*/
    public void enterSearchkey(String key) {
        waitForAllJSComplete();
        //fluentWait(searchTXB);
        waitForElementVisible(SEARCH_TXT);
        TXT_SEARCH.click();
        TXT_SEARCH.sendKeys(key);
        //verify sau khi nhập keysearch xuất hiện gợi ý tìm kiếm
        boolean isSuggestDisplay = SEARCH_SUGGEST.isDisplayed();
        Assert.assertTrue("FAIL",isSuggestDisplay);
    }
    //Nhấn nút Tìm kiếm
    public void clickSearchBtn() {
        BTN_SEARCH.click();
        //verify chuyển đến trang hiển thị các Kết quả tìm kiếm
        String actualURL = "https://tiki.vn/search?q=S%C3%A1ch&_lc=Vk4wMzQwMzAwMDc=";
        String expectedURL = getDriver().getCurrentUrl();
        Assert.assertEquals("Search Fail", expectedURL, actualURL);
        System.out.println("SEARCH SUCCESSFULLY");
    }
}
