package ru.internet.sergeevss90;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.module.Configuration;

import static com.codeborne.selenide.Selenide.open;

public class RegFormTests {

    @BeforeAll
    static void prepare() {
        //Configuration.
    }

    @Test
    void execute() {
        open("https://demoqa.com/automation-practice-form");
    }
}

/*
 * Блочный комментарий
 */