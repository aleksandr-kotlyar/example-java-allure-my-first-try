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

    protected void takeScreenShots() {
        try {
            getHtmlSource("html page source");
            if (!isHtmlUnit())
                saveScreenshot("image screenshot");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private boolean isHtmlUnit() {
        return getWebDriver() instanceof HtmlUnitDriver;
    }
}
