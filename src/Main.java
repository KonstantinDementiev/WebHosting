import inputdata.ConsoleDataReader;
import inputdata.DataReader;
import parsers.DateParser;
import parsers.QueryDataParser;
import parsers.TimeLineDataParser;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private final DataReader dataReader;
    private final TimeLineDataParser timeLineDataParser;
    private final QueryDataParser queryDataParser;
    private final DateParser dateParser;
    private final TimeLineFinder timeLineFinder;

    private final List<LineRecord> timeLineDB = new ArrayList<>();
    private final List<String> resultMinutesList = new ArrayList<>();

    public Main(
            DataReader dataReader,
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
        Main main = new Main(
                new ConsoleDataReader(),
                new TimeLineDataParser(),
                new QueryDataParser(),
                new DateParser(),
                new TimeLineFinder()
        );
        main.run();
    }

    private void run() {
        List<String> inputTimeLines = dataReader.inputData();
        for (String dataLine : inputTimeLines) {
            lineHandling(dataLine);
        }
        printResult();
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

    private void printResult() {
        for (String line : resultMinutesList) {
            System.out.println(line);
        }
    }

}
