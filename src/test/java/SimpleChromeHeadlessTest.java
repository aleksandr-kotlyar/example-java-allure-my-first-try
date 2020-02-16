import com.codeborne.selenide.Condition;
import org.junit.Test;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@NonDefaultDriver
public class SimpleChromeHeadlessTest extends AbstractWebTest {

    @Test
    public void testGoogleLogoNegative() {
        open(baseUrl);
        $(by("id", "hplogo")).shouldNotBe(Condition.visible);
    }

    @Test
    public void testGoogleLogoPositive() {
        open(baseUrl);
        $(by("id", "hplogo")).shouldBe(Condition.visible);
    }
}