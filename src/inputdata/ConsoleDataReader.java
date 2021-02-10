package inputdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleDataReader implements DataReader {

    @Override
    public List<String> inputData() {
        List<String> inputLinesList = new ArrayList<>();
        System.out.println("Enter data:");
        try (Scanner scanner = new Scanner(System.in)) {
            int lineCount = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < lineCount; i++) {
                inputLinesList.add(scanner.nextLine());
            }
            return inputLinesList;
        }
    }

}
