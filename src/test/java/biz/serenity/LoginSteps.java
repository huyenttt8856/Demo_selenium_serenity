package biz.serenity;

import biz.common.LoginPage;
import net.thucydides.core.annotations.Step;

public class LoginSteps {
    LoginPage login;

    @Step
    public void accessWebsite() {
        login.accessWebsite();
    }

    @Step
    public void login(String mail, String pw){
        login.hoverLoginButton();
        login.enterPhone(mail);
        login.enterPass(pw);
        login.clickLoginBtn();
    }
}
