package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.steps.OrderSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    @DisplayName("Тест на получение списка заказов.")
    @Description("Проверяем, что в тело ответа возвращается список заказов.")
    public void getOrderListTests(){
        orderSteps
                .getOrderList(order)
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
