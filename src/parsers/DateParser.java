package parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {

    private LocalDate dateFrom;
    private LocalDate dateTo;

    public LocalDate getDateFrom(String datesToParse) {
        setDates(datesToParse);
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    private void setDates(String datesToParse) {
        if (datesToParse.contains("-")) {
            String[] dates = datesToParse.split("-");
            dateFrom = getDate(dates[0]);
            dateTo = getDate(dates[1]);
        } else {
            dateFrom = getDate(datesToParse);
            dateTo = LocalDate.now();
        }
    }

    private LocalDate getDate(String inputString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(inputString, formatter);
    }

}
