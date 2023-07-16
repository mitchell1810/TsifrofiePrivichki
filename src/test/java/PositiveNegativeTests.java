import lombok.extern.slf4j.Slf4j;
import models.CategoriesItem;
import models.DetectivesItem;
import models.TestObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static util.JsonFileLoader.getTestObjectDataFromFile;

@Slf4j
public class PositiveNegativeTests extends TestNGTests {
    private final String DETECTIVE_SIZE = "^[1-3]$";
    private final String MAIN_ID_VALUE = "^[0-9]|10$";
    private final String CATEGORIES_ID_VALUE = "^[1-2]$";
    static final String DETECTIVE_FIRST_NAME = "Sherlock";

    @Test(groups = {"negative"}, dataProvider = "negativeDP")
    private void negativeTests(TestObject testData) {
        // Здесь должны быть негативные тесты, но их нет, потому что работаем с простым набором данных,
        // при изменении которых не будет происходить реакции системы, как если бы это была проверка API
    }

    @Test(groups = {"positive"}, dataProvider = "positiveDP")
    private void positiveTests(TestObject testData) {

        this.testData = testData;
        // Массив detectives может иметь не менее одного и не более 3х объектов
        assertDetectiveSizeMatchesPattern(testData);

        // Проверка main ID на наличие дубликатов
        assertMainIDsHaveNoDuplicates(testData);

        for (DetectivesItem detective : testData.getDetectives()) {

            // MainId может случайным образом меняться от 0 до 10
            assertMainIDMatchesPattern(detective);

            // CategoryID принимает значения 1 или 2
            assertCategoriesIDMatchesPattern(detective.getCategories());

            // Массив extraArray дожен иметь минимум один элемент для CategoryID=1
            assertFirstCategories(detective.getCategories());

            // Элемент extra может принимать значение null только для CategoryID=2
            assertSecondCategories(detective.getCategories());
        }
        // Поле success принимает значение true только если в массиве detectives есть элемент с firstName ="Sherlock"
        assertSuccessField(testData);

    }

    private void assertSuccessField(TestObject testData) {
        boolean detectivesContainExpectedFirstName =
                checkDetectivesFirstNamePresence(testData, DETECTIVE_FIRST_NAME);
        log.info("Assert success field");
        if (detectivesContainExpectedFirstName) {
            softAssert.assertTrue(testData.isSuccess(),
                    "Success field is not true");
        } else {
            softAssert.assertTrue(!testData.isSuccess(),
                    "Success field is true");
        }
    }

    private boolean checkDetectivesFirstNamePresence(TestObject testData, String firstName) {
        return testData.getDetectives().stream()
                .anyMatch(detective -> detective.getFirstName().equals(firstName));
    }

    private void assertDetectiveSizeMatchesPattern(TestObject testData) {
        log.info("Assert detective size");
        softAssert.assertTrue(sizeMatchesPattern(testData.getDetectives().size(), DETECTIVE_SIZE),
                "Detective array contains invalid number of items");
    }

    private boolean sizeMatchesPattern(int size, String sizePattern) {
        String detectiveListSizeS = String.valueOf(size);
        pattern = Pattern.compile(sizePattern);
        matcher = pattern.matcher(detectiveListSizeS);
        return matcher.matches();
    }

    private void assertMainIDMatchesPattern(DetectivesItem detective) {
        log.info("Assert main id field pattern");
        softAssert.assertTrue(idMatchesPattern(detective.getMainId(), MAIN_ID_VALUE),
                "MainId has invalid value");
    }

    private void assertMainIDsHaveNoDuplicates(TestObject testData) {
        log.info("Assert main id fields have no duplicates");
        Set<Integer> mainId = new HashSet<>();
        testData.getDetectives()
                .forEach(DetectivesItem -> mainId.add(DetectivesItem.getMainId()));
        softAssert.assertTrue(testData.getDetectives().size() == mainId.size(),
                "There are duplicated main id fields in Detectives array");
    }

    private void assertCategoriesIDMatchesPattern(List<CategoriesItem> categoriesList) {
        log.info("Assert categories id");
        for (CategoriesItem category : categoriesList) {
            softAssert.assertTrue(idMatchesPattern(category.getCategoryID(), CATEGORIES_ID_VALUE),
                    "CategoryId has invalid value");
        }
    }

    private boolean idMatchesPattern(int id, String idPattern) {
        String mainIdS = String.valueOf(id);
        pattern = Pattern.compile(idPattern);
        matcher = pattern.matcher(mainIdS);
        return matcher.matches();
    }


    private void assertFirstCategories(List<CategoriesItem> categories) {
        List<CategoriesItem> firstCategories = getCategoryByID(categories, 1);
        log.info("Assert extra array for the category 1");
        firstCategories.forEach(
                categoriesItem ->
                        softAssert.assertTrue(categoriesItem.getExtra().getExtraArray().size() >= 1,
                                "Category with id = 1 has invalid item size"));
    }

    private void assertSecondCategories(List<CategoriesItem> categories) {
        categories.forEach(
                categoriesItem -> {
                    if (categoriesItem.getExtra() == null) {
                        log.info("Assert that category 2 contains extra with null value");
                        softAssert.assertTrue(categoriesItem.getCategoryID() == 2,
                                "Category 1 contains extra with null value");
                    }
                });
    }

    private List<CategoriesItem> getCategoryByID(List<CategoriesItem> categories, int cat) {
        return categories.stream()
                .filter(categoriesItem -> categoriesItem.getCategoryID() == cat)
                .collect(Collectors.toList());
    }

    @DataProvider(name = "positiveDP")
    public Object[][] getPositiveDP() throws IOException {
        return getTestObjectDataFromFile("src/test/resources/testPosData.json");
    }

    @AfterTest
    private void afterTest() throws IOException {
        softAssert.assertAll();
        mapper.writeValue(new File(RESULT_PATH), this.testData);
    }
}