package ru.yandex.practicum.tests;

import io.qameta.allure.Description;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.steps.OrderSteps;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class CreateOrderTests extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private Order order;
    private final String[] color;

    Faker faker = new Faker();

    @Before
    public void setUp() {
        order = new Order();
        order
                .setName(faker.name().firstName())
                .setSurname(faker.name().lastName())
                .setAddress(faker.address().streetAddress())
                .setMetro(faker.address().city())
                .setPhoneNumber(faker.phoneNumber().phoneNumber())
                .setRentTime(faker.number().numberBetween(1, 7))
                .setDeliveryDate(LocalDate.now().plusDays(3).toString())
                .setComments(faker.lorem().sentence());
    }

    public CreateOrderTests(String colorDescription) {
        if (colorDescription.equals("не выбран")) {
            this.color = new String[0];
        } else {
            this.color = colorDescription.split(",\\s*");
        }
    }

    @Parameterized.Parameters(name = "Создание заказа (цвет: {0}).")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {"не выбран"}
        });
    }


    @Test
    @Description("Проверка ответа системы, при создании заказа с выбором одного из/или нескольких цветов, или без выбора цвета")
    public void createOrderWithDifferentColorTest() {
        order.setColor(color);
        orderSteps
                .createOrder(order)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }

    @After
    public void tearDown() {
        try {
            Integer track = orderSteps
                    .createOrder(order)
                    .extract().body().path("track");

            if (track != null) {
                order.setTrack(track);
                orderSteps.cancelOrder(order);
            }
        } catch (Exception ignored) {
        }
    }
}

