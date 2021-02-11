import parsers.DateParser;
import parsers.RecordDataParser;

public class RecordCreator<T extends RecordDataParser> {

    private final String inputLine;
    private final T recordLineParser;
    private final DateParser dateParser;

    RecordCreator(String inputLine, T recordLineParser, DateParser dateParser) {
        this.inputLine = inputLine;
        this.recordLineParser = recordLineParser;
        this.dateParser = dateParser;
    }

    LineRecord createNewTimeLine() {
        final int NUMBER_OF_SERVICE_LEVELS = 2;
        final int NUMBER_OF_QUESTION_LEVELS = 3;
        String[] dataItem = inputLine.split(" ");
        int[] serviceId = recordLineParser.getServiceOrQuestionId(dataItem[0], NUMBER_OF_SERVICE_LEVELS);
        int[] questionId = recordLineParser.getServiceOrQuestionId(dataItem[1], NUMBER_OF_QUESTION_LEVELS);
        return new LineRecord(
                serviceId,
                questionId,
                recordLineParser.getAnswerType(dataItem[2]),
                dateParser.getDateFrom(dataItem[3]),
                dateParser.getDateTo(),
                parseMinutes(dataItem)
        );
    }

    private int parseMinutes(String[] dataItem) {
        return dataItem.length < 5 ? 0 : recordLineParser.getMinutes(dataItem[4]);
    }

}
