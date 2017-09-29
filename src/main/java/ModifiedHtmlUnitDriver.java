import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class ModifiedHtmlUnitDriver extends HtmlUnitDriver {

    public ModifiedHtmlUnitDriver() {
        super(BrowserVersion.CHROME);
    }

    public ModifiedHtmlUnitDriver(BrowserVersion browserVersion) {
        super(browserVersion);
    }

    protected WebClient modifyWebClient(WebClient client) {
        client.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException arg0) throws CSSException {
            }

            @Override
            public void fatalError(CSSParseException arg0) throws CSSException {
            }

            @Override
            public void error(CSSParseException arg0) throws CSSException {
            }
        });

        client.getOptions().setCssEnabled(false);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);

        client.getOptions().setUseInsecureSSL(true);

        return client;
    }

    public WebClient retrieveWebClient() {
        return getWebClient();
    }
}
