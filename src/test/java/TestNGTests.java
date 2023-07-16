import com.fasterxml.jackson.databind.ObjectMapper;
import models.TestObject;
import org.testng.annotations.AfterTest;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestNGTests {
    protected final String RESULT_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\user.json";
    protected SoftAssert softAssert = new SoftAssert();
    protected Pattern pattern;
    protected Matcher matcher;
    protected TestObject testData;
    protected ObjectMapper mapper = new ObjectMapper();
}