import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.junit.runner.Description;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.isHtmlUnit;

public class Allure {

    @Attachment(value = "htmlPageSource", type = "text/html")
    protected byte[] getHtmlSource() throws IOException {
        return getWebDriver().getPageSource().getBytes();
    }

    @Attachment(value = "imageScreenShot", type = "image/png")
    protected byte[] saveScreenshot() throws IOException {
        File file = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(file);
    }

    protected void takeScreenShots(Description description) {
        try {
            getHtmlSource();
            if (!isHtmlUnit())
                saveScreenshot();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
