package com.placeholder.excel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 输入
 * 输入sheet，输出 提供泛型接口输出
 * 输出
 * page，
 */
public class POIUtils {
    public static void main(String[] args) throws Exception {

    }

    private static List<List<Object>> readAllToList(String filepath, Filterable filterable) throws Exception {
        try (InputStream is = new FileInputStream(filepath)) {
            Workbook workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            return readAllToList(sheet, filterable);
        }
    }

    private static List<List<Object>> readAllToList(Sheet sheet, Filterable filterable) {
        List<List<Object>> data = new ArrayList<>();
        for (Row row : sheet) {
            boolean flag = filterable == null || filterable.filter(row);
            if (!flag) {
                continue;
            }
            List<Object> list = new ArrayList<>();
            for (Cell cell : row) {
                set(list, cell);
            }
            data.add(list);
        }
        return data;
    }

    private static void set(List<Object> list, Cell cell) {
        if (cell == null) {
            return;
        }
        int column = cell.getAddress().getColumn();
        Object value = getCellValue(cell);
        CollectionUtils.set(list, column, value);
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        Object value = null;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = cell.getNumericCellValue();
                break;
        }
        return value;
    }

//    public static List<Map<String, Object>> readAllToMap(String filepath, String... titles) throws Exception {
//        List<List<Object>> lists = readAllToList(filepath);
//        List<Map<String, Object>> data = new ArrayList<>();
//        for (List<Object> list : lists) {
//            data.add(listToMap(list, titles));
//        }
//        return data;
//    }

//    private static Map<String, Object> listToMap(List<Object> list, String... titles) {
//        int size = list.size();
//        int len = titles.length;
//
//        Map<String, Object> map = new HashMap<>();
//        for (int i=0; i<len; i++) {
//            String title = titles[i];
//            Object value = null;
//            if (i < size) {
//                value = list.get(i);
//            }
//            map.put(title, value);
//        }
//
//        return map;
//    }

    public static <T> List<T> readAllToObject(String filepath, Class<T> clazz, Filterable filterable, String... titles) throws Exception {
        List<List<Object>> lists = readAllToList(filepath, filterable);
        List<T> data =new ArrayList<>();
        for (List<Object> list : lists) {
            data.add(listToObject(list, clazz, titles));
        }
        return data;
    }

    private static <T> T listToObject(List<Object> list, Class<T> clazz, String[] titles) throws Exception {
        int size = list.size();
        int len = titles.length;
        T t = clazz.newInstance();
        for (int i=0; i<len; i++) {
            String title = titles[i];
            Object value = null;
            if (i < size) {
                value = list.get(i);
            }

            BeanUtils.setProperty(t, title, value);
        }
        return t;
    }

    /**
     * 用于过滤文件
     */
    public interface Filterable {
        /**
         *
         * @param row
         * @return false则丢弃该数据
         */
        boolean filter(Row row);
    }

}
