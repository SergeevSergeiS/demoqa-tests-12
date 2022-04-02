package ru.internet.sergeevss90;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
//import static com.codeborne.selenide.Selectors.byXpath; //Для Xpath
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
        String firstName = "Sergei";
        String lastName = "Sergeev";
        String email = "SergeevSS90@internet.ru";
        String gender = "Male";
        String phoneNumber = "8007553535";
        String subject = "QA Automation";
        String imgPath = "img/test.png";
        LocalDate birth = LocalDate.of(1990, 01, 12);
        String month = birth.getMonth().toString().substring(0, 1)
                + birth.getMonth().toString().substring(1).toLowerCase(Locale.ROOT);

        //Блок заполнения формы
        open("/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(String.valueOf(birth.getYear()));
        $("[aria-label$='" + month + " " + birth.getDayOfMonth() + "th, " + birth.getYear() + "']").click();
        //$(byXpath("//div[@class='react-datepicker__day react-datepicker__day--0" + birth.getDayOfMonth() + "']")).click();        //альтернатива через Xpath
        $("#subjectsInput").setValue(subject);
        $("#hobbiesWrapper").$(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath(imgPath);
    }
}

/*
 * Блочный комментарий
 */