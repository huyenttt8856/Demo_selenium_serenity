/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.common;

import net.thucydides.core.annotations.DefaultUrl;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://tiki.vn/")
public class LoginPage extends CommonPageObject {
//        @FindBy(xpath = "//*[@id='onesignal-popover-cancel-button']")
//    private static WebElement BTN_SKIP;
    By BTN_SKIP = By.xpath("//*[@id='onesignal-popover-cancel-button']");

    @FindBy(xpath = "//span[text()='Đăng nhập']")
    private static WebElement BTN_LOGIN1;

    @FindBy(xpath = "//button[text()='Đăng nhập']")
    private static WebElement BTN_LOGIN2;

    @FindBy(xpath = "//*[@id='email']")
    private static WebElement TXT_USERNAME;

    @FindBy(xpath = "//*[@id='password']")
    private static WebElement TXT_PASS;

    @FindBy(xpath = "//p[@class='forgot-password']//following-sibling::button[1]")
    private static WebElement BTN_LOGIN3;

    //truy cập vào trang web Tiki
    public void accessWebsite() {
        open();
        String actualTitle = getDriver().getTitle();
        String expectedtitle = "Mua Hàng Trực Tuyến Uy Tín với Giá Rẻ Hơn tại Tiki.vn";
        Assert.assertEquals(actualTitle, expectedtitle);
    }

    //Đăng nhập
    public void hoverLoginButton() {
        explicitWait(BTN_SKIP); //đợi popup xuất hiện
        getDriver().findElement(BTN_SKIP).click(); //click bỏ qua
       // BTN_SKIP.click();
        waitForElementInvisible(BTN_SKIP); // đợi popup biến mất
        Actions mouseHover = new Actions(getDriver()); // di chuột đến vùng Đăng nhập
        mouseHover.moveToElement(BTN_LOGIN1).perform();

        boolean isBtnLoginDisplayed = BTN_LOGIN2.isDisplayed(); //verify khi di chuột xuất hiện Dropdown lựa chọn phương thức đăng nhập
        //Assert.assertTrue(isBtnLoginDisplayed);
        Assert.assertTrue("Fail", isBtnLoginDisplayed);

        BTN_LOGIN2.click(); //chọn button Đăng nhập
        //verify xuất hiện modal đăng nhập
        boolean isLoginModalDisplayed = TXT_USERNAME.isDisplayed();
        Assert.assertTrue(isLoginModalDisplayed);
    }

    /*
     * Nhập số điện thoại
     * parameter : mail (phonenumber)*/
    public void enterPhone(String mail) {
        TXT_USERNAME.sendKeys(mail);
    }

    /*
     * Nhập password
     * parameter : pw (password)*/
    public void enterPass(String pw) {
        TXT_PASS.sendKeys(pw);
    }

    //Nhấn nút Đăng nhập
    public void clickLoginBtn() {
        BTN_LOGIN3.click();
        //verify Đăng nhập thành công
        String actualURL = "https://tiki.vn/";
        String expectedURL = getDriver().getCurrentUrl();
        Assert.assertEquals("Login Fail", expectedURL, actualURL);
        System.out.println("LOGIN SUCCESSFULLY");
    }


}
