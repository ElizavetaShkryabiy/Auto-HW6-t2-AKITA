package ru.netology.web.data;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;

import static com.codeborne.selenide.Selenide.$;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {

        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        private String number;
        private String visiblePart;
        private String id;
        private int depositButton;
        private int index;
    }
    public static Card getCardInfo (int index){
        if (index == getFirstCardInfo().getIndex() ){
            return getFirstCardInfo();
        } else {
            return getSecondCardInfo();
        }

    }
    public static Card getFirstCardInfo (){
        return new Card("5559 0000 0000 0001", "0001",
                "[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']" ,
                0, 1);

    }
    public static Card getSecondCardInfo (){
        return new Card("5559 0000 0000 0002", "0002",
                "[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']",
                1, 2);

    }

    public static SelenideElement getCardButton (int index){
              return  $("[data-test-id=action-deposit]", index);
    }

}
