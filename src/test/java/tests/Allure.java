package tests;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Allure {

    @Attachment(value = "{name}", type = "text/html")
    protected byte[] getHtmlSource(String name) throws IOException {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{name}", type = "image/png")
    protected byte[] saveScreenshot(String name) throws IOException {
        File file = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(file);
    }

    /**
     * @method gets html page sources for all browsers.
     * If browser is not HtmlUnit - then PNG screenshot will be taken too.
     */
    protected void takeScreenShots() {
        try {
            getHtmlSource("html page source");
            if (!isHtmlUnit())
                saveScreenshot("image screenshot");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @method isHtmlUnit() checks if browser is instance of HtmlUnitDriver.
     * Return True if is HtmlUnitDriver, False if not.
     */
    private boolean isHtmlUnit() {
        return getWebDriver() instanceof HtmlUnitDriver;
    }
}
