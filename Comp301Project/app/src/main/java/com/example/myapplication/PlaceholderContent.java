package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderContent {
    public static final List<PlaceholderItem> ITEMS = new ArrayList<>();

    static {
        // 添加一些示例项
        addItem(new PlaceholderItem("1", "Item 1"));
        addItem(new PlaceholderItem("2", "Item 2"));
        addItem(new PlaceholderItem("3", "Item 3"));
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
    }

    // PlaceholderItem 类定义
    public static class PlaceholderItem {
        public final String id;
        public final String content;

        public PlaceholderItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
