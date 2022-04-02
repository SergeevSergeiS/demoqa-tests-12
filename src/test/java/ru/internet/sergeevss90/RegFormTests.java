package ru.internet.sergeevss90;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
//import static com.codeborne.selenide.Selectors.byXpath; //если понадобится скролл
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegFormTests {

    @BeforeAll
    static void prepare() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void execute() {
        //Блок переметров
        LocalDate birth = LocalDate.of(1990, 1, 12);
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
        String month = birth.getMonth().toString().charAt(0)
                + birth.getMonth().toString().substring(1).toLowerCase();
        SelenideElement stateCity = $("#stateCity-wrapper");
        ArrayList<SelenideElement> actions = new ArrayList<>();
        actions.add(stateCity.$(byText("Select State")));
        actions.add(stateCity.$(byText(state)));
        actions.add(stateCity.$(byText("Select City")));
        actions.add(stateCity.$(byText(city)));

        //Блок заполнения формы
        open("/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        //$(byXpath("//*[@id='submit']")).scrollIntoView(true);       //скролл на случай маленького экрана/рекламы
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(String.valueOf(birth.getYear()));
        $("[aria-label$='" + month + " " + birth.getDayOfMonth() + "th, " + birth.getYear() + "']").click();
        //$(byXpath("//div[@class='react-datepicker__day react-datepicker__day--0" + birth.getDayOfMonth() + "']")).click();        //альтернатива через Xpath
        $("#subjectsInput").setValue(subject).pressEnter();
        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFromClasspath(imgPath);
        $("#currentAddress").setValue(address);
        for (SelenideElement selenideElement : actions) {
            selenideElement.click();
        }
        $("#submit").click();

        //Блок проверок
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
    }
}