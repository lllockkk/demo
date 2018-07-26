package com.placeholder.excel;

import java.util.List;

public class CollectionUtils {

    public static void set(List<Object> list, int index, Object value) {
        set(list, index, value, null);
    }

    public static void set(List<Object> list, int index, Object value, Object fill) {
        int size = list.size();
        int fillNum = size - index;
        if (fillNum > 0) {
            for (int i=0; i<fillNum; i++) {
                list.add(fill);
            }
        }
        list.add(value);
    }
}
