/*
 * author : HuyenTTT8856
 * Creat date : 1/7/2020
 * */

package biz.serenity;

import biz.common.SearchPage;
import net.thucydides.core.annotations.Step;

public class SearchSteps {
    SearchPage search;
    @Step
    public void searchByKeysearch(String ks){
        search.enterSearchkey(ks);
        search.clickSearchBtn();
    }
}
