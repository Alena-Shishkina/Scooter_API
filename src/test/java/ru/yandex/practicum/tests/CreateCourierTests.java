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
import static org.hamcrest.CoreMatchers.*;

public class CreateCourierTests extends BaseTest {

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

    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка ответа системы, при попытке создать курьера")
    public void shouldCreateCourierTest() {
        couriersSteps
                .createCourier(courier)
                .statusCode(SC_CREATED)
                .assertThat().body("ok", is(true));
    }


    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка ответа системы, при попытке создать двух одинаковых курьеров. Тест failed, ожидаемое и фактическое сообщение в теле ответа разные")
    public void shouldCreateAnExistCourierTest() {
        couriersSteps
                .createCourier(courier);
        couriersSteps.createCourier(courier)
                .statusCode(SC_CONFLICT)
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ответа системы, при попытке создать курьера без пароля")
    public void shouldCreateCourierWithoutPasswordTest() {
        courier.setPassword("");
        couriersSteps
                .createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ответа системы, при попытке создать курьера без логина")
    public void shouldCreateCourierWithoutLoginTest() {
        courier.setLogin("");
        couriersSteps
                .createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
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