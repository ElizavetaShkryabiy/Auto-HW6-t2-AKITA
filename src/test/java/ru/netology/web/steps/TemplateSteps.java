package ru.netology.web.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Тогда;
import lombok.var;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadProperty;

public class TemplateSteps {
    private final AkitaScenario scenario = AkitaScenario.getInstance();


    @Пусть("^пользователь залогинен с именем \"([^\"]*)\" и паролем \"([^\"]*)\"$")
    public void loginWithNameAndPassword(String login, String password) {
        var loginUrl = loadProperty("loginUrl");
        open(loginUrl);

        scenario.setCurrentPage(page(LoginPage.class));
        var loginPage = (LoginPage) scenario.getCurrentPage().appeared();
        var authInfo = new DataHelper.AuthInfo(login, password);
        scenario.setCurrentPage(loginPage.validLogin(authInfo));

        var verificationPage = (VerificationPage) scenario.getCurrentPage().appeared();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        scenario.setCurrentPage(verificationPage.validVerify(verificationCode));

        scenario.getCurrentPage().appeared();
    }

    @Когда("Когда он переводит \"5 000\" рублей с карты с номером \"5559 0000 0000 0002\" на свою \"1\" карту с главной страницы")
    public void deposit(int amount, DataHelper cardFrom, DataHelper cardTo) {
        DataHelper.Card card1 = DataHelper.getFirstCardInfo();
        DataHelper.Card card2 = DataHelper.getSecondCardInfo();
        var dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
        dashboardPage.transferMoney(card1.getDepositButton()).successfulTransfer(amount, card2.getNumber());
    }

    @Тогда("баланс его \"1\" карты из списка на главной странице должен стать \"15 000\" рублей")
    public void getBalance(DataHelper.Card cardNumber) {
        var dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
        DataHelper.Card card1 = DataHelper.getFirstCardInfo();
        assertEquals(15000, dashboardPage.getCardBalance(card1.getVisiblePart()));
    }

}
