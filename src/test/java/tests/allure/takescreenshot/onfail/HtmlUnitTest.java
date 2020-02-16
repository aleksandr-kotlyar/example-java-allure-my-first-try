package tests.allure.takescreenshot.onfail;

import com.codeborne.selenide.Condition;
import org.junit.Test;
import tests.AbstractWebTest;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HtmlUnitTest extends AbstractWebTest {
    /**
     * negative
     */
    @Test
    public void testGoogleLogoNegativeWithJsOff() {
        switchOffJavaScript();
        open(baseUrl);
        $(by("id", "hplogo")).shouldNotBe(Condition.visible);
    }

    @Test
    public void testGoogleLogoNegativeWithJsOn() {
        switchOnJavaScript();
        open(baseUrl);
        $(by("id", "hplogo")).shouldNotBe(Condition.visible);
    }

    /**
     * positive
     */
    @Test
    public void testGoogleLogoPositiveWithJsOn() {
        switchOnJavaScript();
        open(baseUrl);
        $(by("id", "hplogo")).shouldBe(Condition.visible);
    }

    @Test
    public void testGoogleLogoPositiveWithJsOff() {
        switchOffJavaScript();
        open(baseUrl);
        $(by("id", "hplogo")).shouldBe(Condition.visible);
    }
}