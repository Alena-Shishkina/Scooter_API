package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    private final static String ORDER = "/api/v1/orders";
    private final static String CANCEL_ORDER = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order){
        return given()
                .body(order)
                .when()
                .post(ORDER)
                .then();
    }

    @Step("Закрытие заказа")
    public ValidatableResponse cancelOrder(Order order){
        return given()
                .body(order)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList(Order order){
        return given()
                .body(order)
                .when()
                .get(ORDER)
                .then();
    }

}
