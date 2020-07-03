/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.steps;

import biz.serenity.LoginSteps;
import biz.serenity.OrderSteps;
import biz.serenity.SearchSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class OrderStepDefs {
    @Steps
    LoginSteps loginSteps;
    @Steps
    SearchSteps searchSteps;
    @Steps
    OrderSteps orderSteps;

    @Given("Access Tiki website")
    public void accessTikiWebsite() {
        loginSteps.accessWebsite();
    }

    @When("Login the website {string} {string}")
    public void loginTheWebsite(String mail, String pass) {
        loginSteps.login(mail, pass);
    }

    @And("Search by {string}")
    public void searchBy(String key) {
        searchSteps.searchByKeysearch(key);
    }

    @Then("Order product")
    public void orderProduct() {
        orderSteps.orderProduct();
    }


}
