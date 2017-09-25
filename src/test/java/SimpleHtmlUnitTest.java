import com.codeborne.selenide.Condition;
import org.junit.Test;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class SimpleHtmlUnitTest extends AbstractWebTest {
    /**
     * negative
     */
    @Test
    public void testGoogleLogoNegativeWithJsOff() {
        switchOffJavaScript();
        open(baseUrl);
        new GooglePage().getGoogleLogo().shouldNotBe(Condition.visible);
    }

    @Test
    public void testGoogleLogoNegativeWithJsOn() {
        switchOnJavaScript();
        open(baseUrl);
        new GooglePage().getGoogleLogo().shouldNotBe(Condition.visible);
    }

    /**
     * positive
     */
    @Test
    public void testGoogleLogoPositiveWithJsOn() {
        switchOnJavaScript();
        open(baseUrl);
        new GooglePage().getGoogleLogo().shouldBe(Condition.visible);
    }

    @Test
    public void testGoogleLogoPositiveWithJsOff() {
        switchOffJavaScript();
        open(baseUrl);
        new GooglePage().getGoogleLogo().shouldBe(Condition.visible);
    }
}