package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Courier;
import ru.yandex.practicum.steps.CouriersSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthorisationCourierTests extends BaseTest {

    private final CouriersSteps couriersSteps = new CouriersSteps();
    private Courier courier;

    Faker faker = new Faker();

    @Before
    public void setUp() {
        courier = new Courier();
        courier
                .setLogin(faker.name().username())
                .setPassword(faker.internet().password())
                .setFirstName(faker.name().firstName());
        couriersSteps
                .createCourier(courier);

    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка ответа системы, при попытке авторизовать курьера существующей в БД парой логин:пароль")
    public void shouldLoginCourierTest() {
        couriersSteps
                .loginCourier(courier)
                .statusCode(SC_OK)
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным логином")
    @Description("Проверка ответа системы, при попытке авторизоваться с неправильным логином")
    public void shouldLoginCourierIncorrectUsernameTest() {
        courier.setLogin(faker.name().username());
        couriersSteps
                .loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Проверка ответа системы, при попытке авторизоваться с неправильным паролем")
    public void shouldLoginCourierIncorrectPasswordTest() {
        courier.setPassword(faker.internet().password());
        couriersSteps
                .loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка ответа системы, при попытке авторизоваться без ввода пароля")
    public void shouldLoginCourierWithoutPasswordTest() {
        courier.setPassword("");
        couriersSteps
                .loginCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo( "Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка ответа системы, при попытке авторизоваться без ввода логина")
    public void shouldLoginCourierWithoutUsernameTest() {
        courier.setLogin("");
        couriersSteps
                .loginCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo( "Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    @Description("Проверка ответа системы, при попытке авторизоваться несуществующей парой логин:пароль")
    public void shouldLoginNonExistentCourierTest() {
        courier.setLogin(faker.name().username());
        courier.setPassword(faker.internet().password());
        couriersSteps
                .loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .assertThat().body("message", equalTo(  "Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        try {
            Integer id = couriersSteps
                    .loginCourier(courier)
                    .extract().body().path("id");

            if (id != null) {
                courier.setId(id);
                couriersSteps.deleteCourier(courier);

            }
        } catch (Exception ignored) {
        }
    }

}
