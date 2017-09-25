import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.fail;

public class AbstractWebTest {

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
//            String screenName = description.getClassName() + "_" + description.getMethodName() + "_" + System.currentTimeMillis();
//            Screenshots.takeScreenShot(description.getClassName() + "_" + description.getMethodName() + "_" + System.currentTimeMillis());
            try {
                saveScreenshot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        protected void succeeded(Description description) {
        }
    };

    @Attachment()
    public byte[] saveScreenshot() throws IOException {
        File file = Screenshots.getLastScreenshot();
        if (file == null)
            return new byte[0]; //to catch NPE
        return Files.toByteArray(file);

    }

    private static final String CHROME = "chrome";
    private static final String HTML_UNIT_DRIVER = "htmlUnitDriver";
    private String driverName = "";

    @Before
    public void setUp() throws Exception {
        WebDriver driver = null;

        if (this.getClass().isAnnotationPresent(NonDefaultDriver.class)) {
            Annotation annotation = this.getClass().getAnnotation(NonDefaultDriver.class);
            NonDefaultDriver nonDefaultDriver = (NonDefaultDriver) annotation;
            driverName = nonDefaultDriver.driverName();
        }
        String webDriver = driverName.isEmpty() ? HTML_UNIT_DRIVER : driverName;
        if (CHROME.equalsIgnoreCase(webDriver)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--window-size=1280,1000");
            driver = new ChromeDriver(chromeOptions);
            System.out.println("-----------------USING CHROME DRIVER-----------------");
        } else if (HTML_UNIT_DRIVER.equalsIgnoreCase(webDriver)) {
            driver = new ModifiedHtmlUnitDriver();
            System.out.println("-----------------USING HTMLUNITDRIVER DRIVER-----------------");
        } else {
            fail("Set property 'driverName' to 'chrome' or 'htmlUnitDriver'");
        }
        WebDriverRunner.setWebDriver(driver);
        Configuration.baseUrl = "https://www.google.ru";
        Configuration.timeout = 4000;
        Configuration.screenshots = true;
    }

    @After
    public void tearDown() throws Exception {
        driverName = "";
        saveScreenshot();
        if (getWebDriver() != null)
            getWebDriver().quit();
    }

    protected void switchOnJavaScript() {
        if (getWebDriver() instanceof HtmlUnitDriver) {
            ((HtmlUnitDriver) getWebDriver()).setJavascriptEnabled(true);
        }
    }

    protected void switchOffJavaScript() {
        if (getWebDriver() instanceof HtmlUnitDriver) {
            ((HtmlUnitDriver) getWebDriver()).setJavascriptEnabled(false);
        }
    }
}