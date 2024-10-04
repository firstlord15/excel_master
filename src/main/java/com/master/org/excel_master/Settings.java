package com.master.org.excel_master;

public enum Settings {
    NUMBER(SettingType.STRING, "Pattern", "6"),
    PATTERN(SettingType.NUMBER, "Numbers", "MB_1234567");

    private final SettingType type;
    private final String name;
    private final String defaultValue;

    Settings(SettingType type, String name, String defaultValue) {
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getClassName() {
        return name;
    }
    public SettingType getClassType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return name;
    }
}
