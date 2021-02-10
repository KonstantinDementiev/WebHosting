package parsers;

public class RecordDataParser {

    public int[] getServiceOrQuestionId(String inputString, int numberOfCategories) {
        int[] result = new int[numberOfCategories];
        if (inputString.contains(".")) {
            String[] categoryPointer = inputString.split("\\.");
            for (int i = 0; i < categoryPointer.length; i++) {
                result[i] = Integer.parseInt(categoryPointer[i]);
            }
        } else {
            result[0] = Integer.parseInt(inputString);
        }
        return result;
    }

    public char getAnswerType(String inputString) {
        return inputString.charAt(0);
    }

    public int getMinutes(String inputString) {
        return Integer.parseInt(inputString);
    }

}
