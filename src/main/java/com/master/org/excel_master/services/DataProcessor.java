package com.master.org.excel_master.services;

import com.master.org.excel_master.models.RowData;
import com.master.org.excel_master.utils.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

public class DataProcessor {
    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);
    private final FileReader FILE_READER;
    private File mainFile;
    private File secondaryFile;

    public DataProcessor() {
        FILE_READER = new FileReader();
    }

    public File getMainFile() {
        return mainFile;
    }

    public void setMainFile(File mainFile) {
        this.mainFile = mainFile;
    }

    public File getSecondaryFile() {
        return secondaryFile;
    }

    public void setSecondaryFile(File secondaryFile) {
        this.secondaryFile = secondaryFile;
    }

    public List<RowData> getData() {
        if (mainFile == null) {
            LOGGER.error("Main file not set.");
            throw new IllegalStateException("Main file not set.");
        }
        return FILE_READER.readExcelFile(getMainFile());
    }

    public List<String> getShkBoxes() {
        if (secondaryFile == null) {
            LOGGER.error("Secondary file not set.");
            throw new IllegalStateException("Secondary file not set.");
        }
        return FILE_READER.getDataShkBoxFromFile(getSecondaryFile());
    }

    public void toMainConsole() {
        getData().forEach(System.out::println);
    }

    public void toSHKConsole() {
        List<String> shkBoxes = getShkBoxes();
        for (int i = 0; i < shkBoxes.size(); i++) {
            String string = shkBoxes.get(i);
            System.out.println(MessageFormat.format("index: {0}, data: {1}", i, string));
        }
    }
}
