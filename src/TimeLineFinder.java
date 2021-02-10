import java.util.List;

public class TimeLineFinder {

    int calculateAverageWaitingMinutes(List<LineRecord> inputTimeLines, LineRecord queryToFind) {
        int sumMinutes = 0;
        int count = 0;
        for (LineRecord currentLine : inputTimeLines) {
            if (compareServiceId(currentLine, queryToFind)) {
                if (compareQuestionTypeId(currentLine, queryToFind)) {
                    if (compareAnswerType(currentLine, queryToFind)) {
                        if (compareDate(currentLine, queryToFind)) {
                            sumMinutes += currentLine.lineTime();
                            count++;
                        }
                    }
                }
            }
        }
        if (count == 0) {
            return 0;
        } else {
            return sumMinutes / count;
        }
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
