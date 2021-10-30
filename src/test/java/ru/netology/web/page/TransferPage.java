package ru.netology.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

@Name("Пополнение карты")
public class TransferPage extends AkitaPage {

    private SelenideElement heading = $$(".heading").find(text("Пополнение карты"));
    @Name("Сумма")
    @FindBy(css = "[data-test-id=amount] input")
    private SelenideElement sum;
    @Name("Откуда")
    @FindBy(css = "[data-test-id=from] input")
    private SelenideElement from;
    @Name("Пополнить")
    @FindBy(css = "[data-test-id=action-transfer]")
    private SelenideElement transfer;

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage successfulTransfer(int amount, String cardId) {
        sum.setValue(String.valueOf(amount));
        from.setValue(cardId);
        transfer.click();
        return Selenide.page(DashboardPage.class);

    }

}
