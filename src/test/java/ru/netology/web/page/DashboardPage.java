package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import jdk.jfr.Name;
import lombok.val;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Name("Дашбоард")
public class DashboardPage extends AkitaPage {
    @FindBy(css = "[data-test-id=dashboard]")
    private SelenideElement heading;

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    @FindBy(css = "[data-test-id=action-deposit]")
    @Name("Пополнить")
    private SelenideElement deposit;


    public DashboardPage() {
        heading.shouldBe(visible);
    }
    @Name("баланс")
    public int getCardBalance(String id) {
        val text = cards.findBy(text(id)).shouldHave(text(id));
        return extractBalance(String.valueOf(text));
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage transferMoney(int index) {
        deposit = DataHelper.getCardButton(index);
                deposit.click();

        return Selenide.page(TransferPage.class);
    }


}
