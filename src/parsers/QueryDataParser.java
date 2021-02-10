package parsers;

public class QueryDataParser extends RecordDataParser {

    public int[] getServiceOrQuestionId(String inputString, int numberOfCategories) {
        if (inputString.contains("*")) {
            return new int[numberOfCategories];
        } else {
            return super.getServiceOrQuestionId(inputString, numberOfCategories);
        }
    }

    public int getMinutes(String inputString) {
        return -1;
    }

}
