package ru.internet.sergeevss90.tests;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class RegFormTests extends TestBase {

    @Test
    @DisplayName("Successful fill form test")
    void execute() {
        //Блок переметров
        LocalDate birth = LocalDate.of(1990, 1, 12);    //дата в стандартном для Java формате
        String firstName = "Sergei";
        String lastName = "Sergeev";
        String email = "SergeevSS90@internet.ru";
        String gender = "Male";
        String phoneNumber = "8007553535";
        String subject = "English";
        String hobby = "Music";
        String imgPath = "img/test.png";
        String address = "Shantipath, Chanakyapuri, New Delhi, 110021";
        String state = "NCR";
        String city = "Delhi";
        String month = birth.getMonth().toString().charAt(0)                 //получаем из даты название месяца
                + birth.getMonth().toString().substring(1).toLowerCase();
        SelenideElement stateCity = $("#stateCity-wrapper");        //переменная для часто используемого селектора
        ArrayList<SelenideElement> actions = new ArrayList<>();              // коллекция селекторов для обёртки stateCity
        actions.add(stateCity.$(byText("Select State")));
        actions.add(stateCity.$(byText(state)));
        actions.add(stateCity.$(byText("Select City")));
        actions.add(stateCity.$(byText(city)));
        step("Open registration form", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });
        step("Fill registration form", () -> {
            $("#firstName").setValue(firstName);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(email);
            $("#genterWrapper").$(byText(gender)).click();
            $("#userNumber").setValue(phoneNumber);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption(month);
            $(".react-datepicker__year-select").selectOption(String.valueOf(birth.getYear()));
            $("[aria-label$='" + month + " " + birth.getDayOfMonth() + "th, " + birth.getYear() + "']").click();
            $("#subjectsInput").setValue(subject).pressEnter();
            $("#hobbiesWrapper").$(byText(hobby)).click();
            $("#uploadPicture").uploadFromClasspath(imgPath);
            $("#currentAddress").setValue(address);
            for (SelenideElement selenideElement : actions) {
                selenideElement.click();                                //прокликиваем всю коллекцию
            }
            $("#submit").click();
        });
        step("Verify form data", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".modal-body").shouldHave(
                    text(firstName + " " + lastName),
                    text(email),
                    text(gender),
                    text(phoneNumber),
                    text(birth.getDayOfMonth() + " " + month + "," + birth.getYear()),
                    text(subject),
                    text(hobby),
                    text(imgPath.substring(4)),
                    text(address),
                    text(state + " " + city)
            );
        });
    }
}