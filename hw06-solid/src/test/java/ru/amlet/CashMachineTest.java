package ru.amlet;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import ru.amlet.atm.Cash;
import ru.amlet.atm.CashMachine;
import ru.amlet.atm.NoMatchingBills;
import ru.amlet.atm.NotEnoughMoney;
import ru.amlet.atm.CashCassette;
import ru.amlet.money.Money;
import ru.amlet.money.Rubles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CashMachineTest {

    @Test()
    void cashMachineTest() {
        //создаём банкомат
        var cashMachine = new CashMachine();
        //получаем его состояние
        int startBalance = cashMachine.getBalance();
        //проверяем что он пуст
        assertThat(startBalance).isEqualTo(0);
        //наполняем его купюрами на 180 рублей
        var cassettes = new TreeSet<Cash<?>>();
        cassettes.add(new CashCassette<>(Rubles.HUNDRED, 1));
        cassettes.add(new CashCassette<>(Rubles.ONE, 0));
        cassettes.add(new CashCassette<>(Rubles.THOUSAND, 0));
        cassettes.add(new CashCassette<>(Rubles.FIFTY, 1));
        cassettes.add(new CashCassette<>(Rubles.TEN, 3));

        cashMachine.setCassettes(cassettes);
        //получаем его состояние
        int currentBalance = cashMachine.getBalance();

        //проверяем соответствие состояния банкомата и загруженных купюр
        assertThat(currentBalance).isEqualTo(180);

        //запрашиваем получение слишком большой суммы
        assertThrows(NotEnoughMoney.class, () ->
            //проверяем исключение о недостаточном балансе
            cashMachine.issueBills(1000), new NotEnoughMoney(1000).getMessage()
        );

        //запрашиваем не доступный номинал
        assertThrows(NoMatchingBills.class, () ->
            //проверяем исключение о невозможности выдачи таких купюр
            cashMachine.issueBills(4), new NoMatchingBills(4).getMessage()
        );

        //запрашиваем доступный номинал
        Map<Money, Integer> bundleOfBills = cashMachine.issueBills(160);
        //проверяем сумму полученных купюр
        AtomicInteger quantityOfMoney = new AtomicInteger();
        bundleOfBills.forEach((money, integer) -> quantityOfMoney.addAndGet(money.getDenomination() * integer));
        assertThat(quantityOfMoney.get()).isEqualTo(160);
    }

}
