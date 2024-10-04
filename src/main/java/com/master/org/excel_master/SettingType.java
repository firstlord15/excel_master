package com.master.org.excel_master;

public enum SettingType {
    NUMBER("Integer", Integer.class),
    STRING("String", String.class);

    private final String TypeName;
    private final Class<?> clazz;

    SettingType(String TypeName, Class<?> clazz) {
        this.TypeName = TypeName;
        this.clazz = clazz;
    }

    public String getTypeName() {
        return TypeName;
    }
    public Class<?> getClassType() {
        return clazz;
    }

    @Override
    public String toString() {
        return TypeName;
    }
}
