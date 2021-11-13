package ru.netology.web.steps;

import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Тогда;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadProperty;

@Name("Перевод с одной своей карты на другую")
public class TemplateSteps {
    private final AkitaScenario scenario = AkitaScenario.getInstance();

    @Пусть("^пользователь залогинен с именем \"([^\"]*)\", паролем \"([^\"]*)\" и кодом \"([^\"]*)\"$")
    public void пользовательЗалогиненСИменемПаролемИКодом(String login, String password, DataHelper.VerificationCode code) {
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

    @Когда("^он переводит \"([^\"]*)\" рублей с карты с номером \"([^\"]*)\" на свою \"([^\"]*)\" карту с главной страницы$")
    public void онПереводитРублейСКартыСНомеромНаСвоюКартуСГлавнойСтраницы(int amount, int index1, int index2) {

        var dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
        var cardFrom = DataHelper.getCardInfo(index1);
        var cardTo = DataHelper.getCardInfo(index2);
        scenario.setCurrentPage(dashboardPage.transferMoney(cardTo.getDepositButton())
                .successfulTransfer(amount, cardFrom.getNumber()));

        scenario.getCurrentPage().appeared();
    }

    @Тогда("^баланс его \"([^\"]*)\" карты из списка на главной странице должен стать \"([^\"]*)\" рублей$")
    public void балансЕгоКартыИзСпискаНаГлавнойСтраницеДолженСтатьРублей(int index, int balanceAmount) {

        var dashboardPage = (DashboardPage) scenario.getCurrentPage().appeared();
        var cardNumber = DataHelper.getCardInfo(index).getVisiblePart();
        var balance = dashboardPage.getCardBalance(String.valueOf(cardNumber));
        assertEquals(balanceAmount, balance);
    }

}
