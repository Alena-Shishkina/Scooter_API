package ru.yandex.practicum.steps;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.model.Courier;

import static io.restassured.RestAssured.given;

public class CouriersSteps {

    public static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static final String DELETE_COURIER = "/api/v1/courier/{id}";


    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(Courier courier) {
        return given()
                .pathParam("id", courier.getId())
                .when()
                .delete(DELETE_COURIER)
                .then();
    }
}
