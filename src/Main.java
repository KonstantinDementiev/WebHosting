import inputdata.ConsoleDataReader;
import inputdata.DataReaderImpl;
import inputdata.RandomDataLoader;
import parsers.DateParser;
import parsers.QueryDataParser;
import parsers.TimeLineDataParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main<T extends DataReaderImpl> {

    private final T dataReader;
    private final TimeLineDataParser timeLineDataParser;
    private final QueryDataParser queryDataParser;
    private final DateParser dateParser;
    private final TimeLineFinder timeLineFinder;
    private final List<LineRecord> timeLineDB = new ArrayList<>();
    private final List<String> resultMinutesList = new ArrayList<>();

    public Main(
            T dataReader,
            TimeLineDataParser timeLineDataParser,
            QueryDataParser queryDataParser,
            DateParser dateParser,
            TimeLineFinder timeLineFinder
    ) {
        this.dataReader = dataReader;
        this.timeLineDataParser = timeLineDataParser;
        this.queryDataParser = queryDataParser;
        this.dateParser = dateParser;
        this.timeLineFinder = timeLineFinder;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please, enter data input type: 'a' - AUTO, 'm' - MANUAL:");
            String inputType = scanner.nextLine();
            choiceInputType(inputType).run();
        }
    }

    private static Main<? extends DataReaderImpl> choiceInputType(String type) {
        return switch (type) {
            case "m" -> createNewMainInstance(new ConsoleDataReader());
            case "a" -> createNewMainInstance(new RandomDataLoader());
            default -> throw new IllegalArgumentException();
        };
    }

    private static <T extends DataReaderImpl> Main<T> createNewMainInstance(T linesResource) {
        return new Main<>(
                linesResource,
                new TimeLineDataParser(),
                new QueryDataParser(),
                new DateParser(),
                new TimeLineFinder());
    }

    private void run() {
        List<String> inputTimeLines = dataReader.inputData();
        for (String dataLine : inputTimeLines) {
            lineHandling(dataLine);
        }
        resultMinutesList.forEach(System.out::println);
    }

    private void lineHandling(String dataLine) {
        char lineType = dataLine.charAt(0);
        switch (lineType) {
            case 'C' -> writeDataToDB(dataLine.substring(2));
            case 'D' -> readDataFromDB(dataLine.substring(2));
            default -> throw new IllegalArgumentException();
        }
    }

    private void writeDataToDB(String inputLine) {
        timeLineDB.add(new RecordCreator<>(inputLine, timeLineDataParser, dateParser).
                createNewTimeLine());
    }

    private void readDataFromDB(String inputLine) {
        LineRecord queryToDB = createQueryLine(inputLine);
        int averageTimeWaiting = timeLineFinder.calculateAverageWaitingMinutes(timeLineDB, queryToDB);
        if (averageTimeWaiting == 0) {
            resultMinutesList.add("-");
        } else {
            resultMinutesList.add(String.valueOf(averageTimeWaiting));
        }
    }

    private LineRecord createQueryLine(String inputLine) {
        return new RecordCreator<>(inputLine, queryDataParser, dateParser).
                createNewTimeLine();
    }

}
