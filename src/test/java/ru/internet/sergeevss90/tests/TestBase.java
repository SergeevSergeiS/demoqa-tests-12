package ru.internet.sergeevss90.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.aeonbits.owner.ConfigFactory;
import ru.internet.sergeevss90.config.CredentialsConfig;
import ru.internet.sergeevss90.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

        String selenoidAddress = System.getProperty("selenoidUrlAddress", "selenoid.autotests.cloud/wd/hub");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        System.out.println(config.login());
        System.out.println(config.password());
        Configuration.remote = "https://" + config.login() + ":" + config.password() + "@" + selenoidAddress;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}
