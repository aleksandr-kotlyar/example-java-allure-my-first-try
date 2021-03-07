# Try Allure!
**Hi all! This is an example how to attach allure screenshots with custom names.**

* Here is setup: 
  * maven 
  * java 1.8
  * allure2 
  * junit4 
  * selenide 3.11
  
* Project contains same two tests: positive and negative (will fail for taking screenshot).

* For each of two webDrivers: headlessChrome and htmlUnit 

  1. allure.takescreenshot.onfail.ChromeTest - .png + .html screenshots should be taken.
 
  2. allure.takescreenshot.onfail.HtmlUnitTest - only .html screenshots should be taken.

     * htmlUnit has examples with js on and js off.
  3. screenshots with [custom names](https://github.com/aleksandr-kotlyar/java-allure-selenide-screenshots/blob/master/src/test/java/tests/Allure.java)! wow! 
     ```java
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
     ```
