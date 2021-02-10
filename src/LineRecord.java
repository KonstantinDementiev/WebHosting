import java.time.LocalDate;

public record LineRecord(
        int[] serviceId,
        int[] questionTypeId,
        char answerType,
        LocalDate lineDateFrom,
        LocalDate lineDateTo,
        int lineTime
) {

}
