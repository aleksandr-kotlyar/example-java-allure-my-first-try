package tests.allure.takescreenshot.onfail;

import com.codeborne.selenide.Condition;
import org.junit.Test;
import tests.AbstractWebTest;
import tests.NonDefaultDriver;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@NonDefaultDriver
public class ChromeTest extends AbstractWebTest {

    /**
     * Test will fail and screenshot will be taken by Allure.
     */
    @Test
    public void testGoogleLogoNegative() {
        open(baseUrl);
        $(by("id", "hplogo")).shouldNotBe(Condition.visible);
    }

    /**
     * Test will pass and screenshot will not be taken.
     */
    @Test
    public void testGoogleLogoPositive() {
        open(baseUrl);
        $(by("id", "hplogo")).shouldBe(Condition.visible);
    }
}