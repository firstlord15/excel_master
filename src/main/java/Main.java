import com.master.org.excel_master.Setting;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Setting setting = new Setting();
//        setting.createConfigFile();
        setting.editConfigFile(List.of("MB_7434264", "5"));
        System.out.println(setting.readConfigFile());
    }
}
