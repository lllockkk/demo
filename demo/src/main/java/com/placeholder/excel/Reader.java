package com.placeholder.excel;

import java.util.List;

// 缺少坐标

public interface Reader<T> {
    List<T> readAll();
    int size();
    T next();
    boolean hasNext();
}

interface Table {

}

interface NamedTable {

}
