/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.common;

import net.thucydides.core.annotations.DefaultUrl;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DefaultUrl("https://tiki.vn/")
public class OrderPage extends CommonPageObject {
    //Khai bao element
    @FindBy(xpath = "//div[@class='product-box-list']//a[@class='search-a-product-item']")
    private static List<WebElement> LIST_PRODUCT;

    @FindBy(xpath = "//*[@class='header border-bottom']")
    private static WebElement HEADER_DETAILPRODUCT;

    @FindBy(xpath = "//input[@class='input']")
    private static WebElement TXT_AMOUNTS;

    @FindBy(xpath = "//button[@class='btn btn-add-to-cart']")
    private static WebElement BTN_BUY;

    @FindBy(xpath = "//p[@class='status']")
    private static WebElement CART_NOTIFY;

    @FindBy(xpath = "//a[@class='btn-view-cart']")
    private static WebElement BTN_VIEW_CART;

    @FindBy(xpath = "//button[contains(text(),'+')]")
    private static WebElement BTN_ADD;

    @FindBy(xpath = "//button[@class='cart__submit']")
    private static WebElement BTN_CART_SUBMIT;

    @FindBy(xpath = "//button[@class='btn saving-address']")
    private static WebElement BTN_EXIST_ADDRESS;

    @FindBy(xpath = "//ul[@class='list']/child::li[3]//label")
    private static WebElement LABEL_ATM_PAY;

    By ATM_PAY_LABEL = By.xpath("//ul[@class='list']/child::li[3]//label");

    By ATM = By.xpath("//ul[@class='atms atms--open']/child::li[2]");
    By TITLE_CART = By.xpath("//h2[@class='cart-products__title']");
    By TITLE_ADDRESS = By.xpath("//h3[@class='title']");
    By EXIST_ADDRESS_BTN = By.xpath("//button[@class='btn saving-address']");
    By BTN_ORDER = By.xpath("//div[@class='order-button']");

    //lựa chọn sản phẩm, chọn sản phẩm thứ 2 trong list sản phẩm
    public void selectProduct() {
        WebElement product = LIST_PRODUCT.get(1);
        product.click();
        boolean isProductDetailDisplay = HEADER_DETAILPRODUCT.isDisplayed(); //verify sau khi nhấn vào sản phẩm chuyển sang trang chi tiết sản phẩm
        Assert.assertTrue("FAIL",isProductDetailDisplay);

        //verify khi nhấn button '+' thì số lượng tăng lên
        int a = Integer.parseInt(TXT_AMOUNTS.getAttribute("value"));
        BTN_ADD.click();
        int b = Integer.parseInt(TXT_AMOUNTS.getAttribute("value"));
        if (b > a) {
            System.out.println("Amounts increase");
        } else {
            System.out.println("Fail");
        }

        BTN_BUY.click();
        //verify sau khi nhấn nút Mua hàng, xuất hiện Cart Notification
        boolean isCartNotificationDisplay = CART_NOTIFY.isDisplayed();
        Assert.assertTrue("FAIL",isCartNotificationDisplay);
    }

    //Đi đến giỏ hàng
    public void goToCart() {
        BTN_VIEW_CART.click();
        //waitForAllJSComplete();
        //waitABit(10000);
        fluentWait(TITLE_CART); // đợi Title giỏ hàng xuất hiện
        //verify chuyển đến Trang Giỏ hàng
        String actualTitle = "Giỏ hàng | Tiki.vn";
        String expectedTitle = getDriver().getTitle();
        Assert.assertEquals("Cart Page don't Display", expectedTitle, actualTitle);
        System.out.println("Cart Page is Displayed");

    }

    //Đặt hàng
    public void OderProduct() {
        BTN_CART_SUBMIT.click();
        fluentWait(TITLE_ADDRESS); // Đợi trang xác nhận địa chỉ giao hàng
        //Verify chuyển đến Trang xác nhận địa chỉ giao hàng
        String actualURL = "https://tiki.vn/checkout/shipping";
        String expectedURL = getDriver().getCurrentUrl();
        Assert.assertEquals("Address Page don't Display", expectedURL, actualURL);
        System.out.println("Address Page display");
        fluentWait(EXIST_ADDRESS_BTN);
        BTN_EXIST_ADDRESS.click(); // lựa chọn địa chỉ đã có sẵn
        waitForElementVisible(BTN_ORDER);
        //verify chuyển đến trang Thanh toán
        String actualURL1 = "https://tiki.vn/checkout/payment";
        String expectedURL1 = getDriver().getCurrentUrl();
        Assert.assertEquals("Payment Page don't Display", expectedURL1, actualURL1);
        System.out.println("Payment Page is Display");
    }

    //Thanh toán
    public void payment() {

        //di chuyển thanh cuộn đến element thanh toán bằng thẻ ATM
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", LABEL_ATM_PAY);
        //js.executeScript("window.scrollBy(0,5000)");
        System.out.println("ScrollDown");
        //fluentWait(ATM_PAY_LABEL);
        //waitForElementToBeClickable(ATM_PAY_LABEL);
        //waitElementClickedByJS(ATM_PAY_LABEL);
        if (LABEL_ATM_PAY.isDisplayed()){
            JavascriptExecutor executor = (JavascriptExecutor)getDriver();
            executor.executeScript("arguments[0].click();", LABEL_ATM_PAY);
        }
        else {
            System.out.println("Unable to click");
        }
        //LABEL_ATM_PAY.click();//lựa chọn thanh toán bằng thẻ ATM
        System.out.println("Lua chon Thanh toán bằng ATM");
        waitForElementVisible(ATM);
        //fluentWait(ATM1);
        getDriver().findElement(ATM).click(); // lua chon ngân hàng
        System.out.println("Lua chon ngân hàng");
    }


}
