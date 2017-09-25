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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;

import static com.codeborne.selenide.Configuration.reportsFolder;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.fail;

public class AbstractWebTest {

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            //TODO screenName doesn't use in Allure. why? */
            String screenName = description.getClassName() + "_" + description.getMethodName() + "_" + System.currentTimeMillis();
            try {
                getHtmlSource(screenName);
                if (Screenshots.takeScreenShotAsFile() != null)
                    saveScreenshot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        protected void succeeded(Description description) {
        }
    };

    //TODO getHtmlSource returns .xhtml when using Chrome. why?
    //TODO could selenide takePageSourceToFile be more easier than that copy/paste from ScreenShotLaboratory?
    @Attachment(value = "customName", type = "string/html")
    private byte[] getHtmlSource(String screenName) throws IOException {
        File pageSource = new File(reportsFolder, screenName + ".html");
        String content = getWebDriver().getPageSource();
        try (ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"))) {
            try (FileOutputStream out = new FileOutputStream(pageSource)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                fail("Failed to write file " + pageSource.getAbsolutePath() + e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Files.toByteArray(pageSource);
    }

    @Attachment(type = "image/png")
    private byte[] saveScreenshot() throws IOException {
        File file = Screenshots.takeScreenShotAsFile();
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
        //TODO testwatcher fails after tearDown, why? How can i close webdriver than?
//        if (getWebDriver() != null)
//            getWebDriver().quit();
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