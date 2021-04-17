package tests;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormTests extends TestBase {

    @Test
    void successfulFillFormTest() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("in-ID"), new RandomService());
        String siteName = "https://demoqa.com/automation-practice-form",
                firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                email = fakeValuesService.bothify("????##@gmail.com"),
                sex = "Male",
                number = faker.number().digits(10),
                birthMonth = "June",
                birthYear = ("19" + faker.number().digits(2)),
                subjects = "Maths",
                hobbies = "Reading",
                address = faker.address().streetAddress(),
                state = "NCR",
                city = "Delhi";
        step("Open student registration form", () -> open(siteName));
        step("Fill student registration form", () -> {
            step("Fill common data", () -> {
                $("#firstName").setValue(firstName);
                $("#lastName").setValue(lastName);
                $("#userEmail").setValue(email);
                $("#genterWrapper").find(byText(sex)).click();
                $("#userNumber").setValue(number);
            });
            step("Set date", () -> {
                $("#dateOfBirthInput").click();
                $(".react-datepicker__month-select").selectOption(birthMonth);
                $(".react-datepicker__year-select").selectOption(birthYear);
                $(".react-datepicker__day.react-datepicker__day--013").click();
            });
            step("Set subjects", () -> {
                $("#subjectsInput").val("ma");
                $(byText(subjects)).click();
            });
            step("Set address", () -> {
                $("#currentAddress").setValue(address);
                $("#state").click();
                $(byText(state)).click();
                $("#city").click();
                $(byText(city)).click();
            });
            step("Upload capture", () -> $("#uploadPicture").uploadFromClasspath("per.png"));
            step("Set hobbies", () -> $("[for='hobbies-checkbox-2']").click());
            step("Submit form", () -> $("#submit").click());
        });
        step("Verify student form ", () -> {
            $(".table").shouldHave(text(firstName),
                    text(lastName),
                    text(email),
                    text(sex),
                    text(number),
                    text(birthMonth),
                    text(birthYear),
                    text(subjects),
                    text(hobbies),
                    text(address),
                    text("per.png"),
                    text(state),
                    text(city));
        });

    }
}