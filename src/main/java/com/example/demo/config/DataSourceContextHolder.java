package com.example.demo.config;

public class DataSourceContextHolder {

    public enum DataSourceType {
        MASTER, READER
    }

    private static final ThreadLocal<DataSourceType> context = new ThreadLocal<>();

    public static void set(DataSourceType type) {
        System.out.println("🔁 Switching DB to: " + type);
        context.set(type);
    }

    public static DataSourceType get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}

