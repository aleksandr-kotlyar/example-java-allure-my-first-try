import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.lang.annotation.Annotation;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.fail;

public class AbstractWebTest {

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        Allure allure = new Allure();

        @Override
        protected void failed(Throwable e, Description description) {
            allure.takeScreenShots(description);
        }

        @Override
        protected void finished(Description description) {
            closeWebdriver();
        }
    };

    private void closeWebdriver() {
        if (getWebDriver() != null)
            getWebDriver().quit();
    }


    private static final String CHROME = "chrome";
    private static final String HTML_UNIT_DRIVER = "htmlUnitDriver";


    @Before
    public void setUp() throws Exception {
        WebDriver driver = null;
        String driverName = "";

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

    protected void switchOnJavaScript() {
        if (isHtmlUnit()) {
            ((HtmlUnitDriver) getWebDriver()).setJavascriptEnabled(true);
        }
    }

    protected void switchOffJavaScript() {
        if (isHtmlUnit()) {
            ((HtmlUnitDriver) getWebDriver()).setJavascriptEnabled(false);
        }
    }

    private boolean isHtmlUnit() {
        return getWebDriver() instanceof HtmlUnitDriver;
    }
}