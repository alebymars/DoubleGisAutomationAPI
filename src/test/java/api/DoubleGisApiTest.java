package api;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("Тестирование API")
public class DoubleGisApiTest {

    private final static String BASE_URL = "https://regions-test.2gis.com/1.0/regions";

    @Test
    @DisplayName("Получить регионы без параметров")
    @Description("Получение регионов без параметров (по умолчанию 15 на странице)")
    @Tag("API")
    @Link(BASE_URL)
    @Owner("Alexandr Voronkov")
    public void getRegionsWithoutParameters() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationOK200());
        List<RegionsData> regions;

        regions = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().getList("items", RegionsData.class);

        Integer actual = regions.size();
        Integer expected = 15;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertEquals(expected, actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Получить только регионы kz")
    @Description("Получение регионов kz")
    @Tag("API")
    @Link(BASE_URL + "?country_code=kz")
    @Owner("Alexandr Voronkov")
    public void getRegionsKz() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationOK200());
        List<CountryData> countries;

        countries = given()
                .when()
                .queryParam("country_code", "kz")
                .get()
                .then()
                .extract().body().jsonPath().getList("items.country", CountryData.class);

        List<String> actualCodes = countries.stream().map(x -> x.getCode().toString()).collect(Collectors.toUnmodifiableList());

        Boolean actual = actualCodes.stream().allMatch(x -> x.contains("kz"));
        Boolean expected = true;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertTrue(actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Игнорируем все параметры, кроме q")
    @Description("Проверка игнорирования всех остальных параметров, если передан параметр q")
    @Tag("API")
    @Link(BASE_URL + "?page=-10&country_code=error&page_size=1000&q=рск")
    @Owner("Alexandr Voronkov")
    public void checkIfSearchParametersAreIgnored() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationOK200());
        List<RegionsData> regions;

        regions = given()
                .when()
                .queryParams("country_code", "error", "page", "-10", "page_size", "1000", "q", "рск")
                .get()
                .then()
                .extract().body().jsonPath().getList("items", RegionsData.class);

        List<String> actualNames = regions.stream().map(x -> x.getName()).collect(Collectors.toUnmodifiableList());

        Boolean actual = actualNames.stream().allMatch(x -> x.endsWith("рск"));
        Boolean expected = true;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertTrue(actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Количество регионов (total)")
    @Description("Получаем общее количество регионов в базе")
    @Tag("API")
    @Link(BASE_URL)
    @Owner("Alexandr Voronkov")
    public void getTheTotalOfRegions() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationOK200());
        Integer total;

        total = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().get("total");

        Integer actual = total;
        Integer expected = 22;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertEquals(expected, actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Получить 10 регионов на странице")
    @Description("Получение 10 регионов на странице")
    @Tag("API")
    @Link(BASE_URL + "?page_size=10")
    @Owner("Alexandr Voronkov")
    public void getTenRegions() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationOK200());
        List<RegionsData> regions;

        regions = given()
                .when()
                .queryParam("page_size", "10")
                .get()
                .then()
                .extract().body().jsonPath().getList("items", RegionsData.class);

        Integer actual = regions.size();
        Integer expected = 10;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertEquals(expected, actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Ошибка при запросе с пустым параметром q")
    @Description("Получение ошибки при запросе с пустым параметром q")
    @Tag("API")
    @Link(BASE_URL + "?q=")
    @Owner("Alexandr Voronkov")
    public void getErrorWithEmptySearchParameter() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationError400());
        String error;

        error = given()
                .when()
                .queryParam("q", "")
                .get()
                .then()
                .extract().body().jsonPath().get("error.message");


        Boolean actual = error.equals("Параметр 'q' должен быть не менее 3 символов");
        Boolean expected = true;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertTrue(actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Ошибка при запросе с некорректным параметром page")
    @Description("Получение ошибки при запросе с некорректным параметром page=0")
    @Tag("API")
    @Link(BASE_URL + "?page=0")
    @Owner("Alexandr Voronkov")
    public void getErrorWithIncorrectPageParameter() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationError400());
        String error;

        error = given()
                .when()
                .queryParam("page", "0")
                .get()
                .then()
                .extract().body().jsonPath().get("error.message");


        Boolean actual = error.equals("Параметр 'page' должен быть больше 0");
        Boolean expected = true;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertTrue(actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Ошибка при запросе с некорректным параметром q")
    @Description("Получение ошибки при запросе с некорректным параметром q=рск;+любой+текст")
    @Tag("API")
    @Link(BASE_URL + "?q=рск;+любой+текст")
    @Owner("Alexandr Voronkov")
    public void getErrorWithInvalidSearchParameter() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL + "?q=рск;+любой+текст"), Specifications.responseSpecificationError400());
        String error;

        error = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().get("error.message");

        Boolean actual = error.equals("Параметр 'q' должен состоять только из букв.");
        Boolean expected = true;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertEquals(expected, actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }


    @Test
    @DisplayName("Ошибка при запросе с некорректным параметром country_code")
    @Description("Получение ошибки при запросе с некорректным параметром country_code")
    @Tag("API")
    @Link(BASE_URL + "?q=рск;+любой+текст")
    @Owner("Alexandr Voronkov")
    public void getErrorWithIncorrectCountryCode() {
        Specifications.installSpecification(Specifications.requestSpecification(BASE_URL), Specifications.responseSpecificationError400());
        String error;

        error = given()
                .when()
                .queryParam("country_code", "us")
                .get()
                .then()
                .extract().body().jsonPath().get("error.message");

        Boolean actual = error.equals("Параметр 'country_code' может быть одним из следующих значений: ru, kg, kz, cz");
        Boolean expected = true;
        String result = "Фактический результат: " + actual + "\nОжидаемый результат: " + expected;
        String errorMessage = "Ошибка. Фактический результат не сходится с ожидаемым.";

        try {
            Assertions.assertEquals(expected, actual, errorMessage);
        } catch (AssertionError e) {
            Allure.addAttachment("Результаты:", result);
            throw e;
        }

        Allure.addAttachment("Результаты:", result);

    }

}

