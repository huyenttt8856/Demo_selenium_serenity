/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.serenity;

import biz.common.OrderPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class OrderSteps {
    OrderPage order;
    @Step
    public void orderProduct(){
        order.selectProduct();
        order.goToCart();
        order.OderProduct();
        order.payment();
    }
}
