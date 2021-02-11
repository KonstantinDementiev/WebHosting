package inputdata;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataLoader extends DataReaderImpl {

    private final int MAX_LINE_COUNT = 10;
    private final int MAX_SERVICES = 10;
    private final int MAX_VARIATIONS = 3;
    private final int MAX_QUESTION_TYPES = 10;
    private final int MAX_CATEGORIES = 20;
    private final int MAX_SUBCATEGORIES = 5;
    private final int MAX_MINUTES = 1440;
    private final Random random = new Random();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<String> inputData() {
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < MAX_LINE_COUNT; i++) {
            resultList.add(generateLine());
        }
        printResult(resultList);
        return resultList;
    }

    private String generateLine() {
        StringBuilder resultString = new StringBuilder();
        String lineCharacter = randomBooleanGenerator() ? "C" : "D";
        String responseType = randomBooleanGenerator() ? "P" : "N";
        int minutes = randomIntGenerator(MAX_MINUTES, 1);
        resultString
                .append(lineCharacter).append(" ")
                .append(printServiceBlock(lineCharacter)).append(" ")
                .append(printQuestionBlock(lineCharacter)).append(" ")
                .append(responseType).append(" ")
                .append(printDateBlock(lineCharacter)).append(" ")
                .append(printMinutes(lineCharacter, minutes))
        ;
        return resultString.toString();
    }

    private String printServiceBlock(String lineCharacter) {
        StringBuilder resultString = new StringBuilder();
        int service = randomIntGenerator(MAX_SERVICES, 1);
        int variation = randomIntGenerator(MAX_VARIATIONS, service);
        resultString
                .append(replaceZeroByStar(lineCharacter, service))
                .append(replaceZeroByEmpty(variation));
        return resultString.toString();
    }

    private String printQuestionBlock(String lineCharacter) {
        StringBuilder resultString = new StringBuilder();
        int questionType = randomIntGenerator(MAX_QUESTION_TYPES, 1);
        int category = randomIntGenerator(MAX_CATEGORIES, questionType);
        int subcategory = randomIntGenerator(MAX_SUBCATEGORIES, category);
        resultString
                .append(replaceZeroByStar(lineCharacter, questionType))
                .append(replaceZeroByEmpty(category))
                .append(replaceZeroByEmpty(subcategory));
        return resultString.toString();
    }

    private String printDateBlock(String lineCharacter) {
        StringBuilder resultString = new StringBuilder();
        LocalDate dateFrom = randomDate(LocalDate.of(2020, Month.JANUARY, 1));
        LocalDate dateTo = randomDate(dateFrom);
        String printDateFrom = dateFrom.format(formatter);
        resultString
                .append(printDateFrom)
                .append(printSecondDate(lineCharacter, dateTo));
        return resultString.toString();
    }

    private String replaceZeroByEmpty(int number) {
        return number == 0 ? "" : "." + number;
    }

    private String replaceZeroByStar(String character, int number) {
        if (character.equals("D")) {
            return number == 0 ? "*" : "" + number;
        }
        return number == 0 ? "1" : "" + number;
    }

    private boolean randomBooleanGenerator() {
        return random.nextBoolean();
    }

    private int randomIntGenerator(int maxValue, int previousCategoryValue) {
        if (previousCategoryValue != 0) {
            return random.nextInt(maxValue);
        }
        return 0;
    }

    private String printSecondDate(String character, LocalDate dateTo) {
        if (character.equals("D")) {
            return randomBooleanGenerator() ? "-" + dateTo.format(formatter) : "";
        }
        return "";
    }

    private String printMinutes(String character, int minutes) {
        return character.equals("C") ? "" + minutes : "";
    }

    private LocalDate randomDate(LocalDate start) {
        LocalDate end = LocalDate.now();
        return between(start, end);
    }

    public LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private void printResult(List<String> listToPrint) {
        for (String line : listToPrint) {
            System.out.println(line);
        }
    }

}
