import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Allure {

    @Attachment(value = "html page source", type = "text/html")
    protected byte[] getHtmlSource() throws IOException {
        return getWebDriver().getPageSource().getBytes("UTF-8");
    }

    @Attachment(value = "image screenshot", type = "image/png")
    protected byte[] saveScreenshot() throws IOException {
        File file = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(file);
    }

    protected void takeScreenShots() {
        try {
            getHtmlSource();
            if (!isHtmlUnit())
                saveScreenshot();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private boolean isHtmlUnit() {
        return getWebDriver() instanceof HtmlUnitDriver;
    }
}
