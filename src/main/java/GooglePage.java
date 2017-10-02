import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selenide.$;

public class GooglePage {

    public SelenideElement getGoogleLogo() {
        return $(by("id", "hplogo"));
    }
}