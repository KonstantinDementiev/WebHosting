import java.util.List;

public class TimeLineFinder {

    int calculateAverageWaitingMinutes(List<LineRecord> inputTimeLines, LineRecord queryToFind) {
        double average = inputTimeLines.stream()
                .filter(lineRecord -> compareServiceId(lineRecord, queryToFind))
                .filter(lineRecord -> compareQuestionTypeId(lineRecord, queryToFind))
                .filter(lineRecord -> compareAnswerType(lineRecord, queryToFind))
                .filter(lineRecord -> compareDate(lineRecord, queryToFind))
                .mapToInt(LineRecord::lineTime)
                .average().orElse(0);
        return (int) Math.round(average);
    }

    private boolean compareServiceId(LineRecord currentLine, LineRecord queryToFind) {
        return isItemIdMatch(currentLine.serviceId(), queryToFind.serviceId());
    }

    private boolean compareQuestionTypeId(LineRecord currentLine, LineRecord queryToFind) {
        return isItemIdMatch(currentLine.questionTypeId(), queryToFind.questionTypeId());
    }

    private boolean isItemIdMatch(int[] actualItemId, int[] requiredItemId) {
        for (int i = 0; i < requiredItemId.length; i++) {
            if (requiredItemId[i] == 0 || actualItemId[i] == 0) {
                break;
            } else if (requiredItemId[i] != actualItemId[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean compareAnswerType(LineRecord currentLine, LineRecord queryToFind) {
        return currentLine.answerType() == queryToFind.answerType();
    }

    private boolean compareDate(LineRecord currentLine, LineRecord queryToFind) {
        return currentLine.lineDateFrom().isAfter(queryToFind.lineDateFrom())
                && currentLine.lineDateFrom().isBefore(queryToFind.lineDateTo());
    }
}
