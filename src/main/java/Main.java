import com.master.org.excel_master.services.DataProcessor;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file1 = new File("C:\\Users\\firstlord\\OneDrive\\Рабочий стол\\поставки\\template (28).xlsx");
        File file2 = new File("C:\\Users\\firstlord\\OneDrive\\Рабочий стол\\поставки\\shk-excel (93).xlsx");
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.setMainFile(file1);
        dataProcessor.setSecondaryFile(file2);
        dataProcessor.toMainConsole();
        dataProcessor.toSHKConsole();

//        PathService pathService = new PathService();
//        pathService.getPath();
    }
}
