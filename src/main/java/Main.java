import com.master.org.excel_master.services.DataProcessor;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.setFile(new File("src/resources/file.xlsx"));
        dataProcessor.toConsole();
    }
}
