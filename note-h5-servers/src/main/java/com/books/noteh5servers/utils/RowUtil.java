
package com.books.noteh5servers.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class RowUtil {

    public static Object getCellType(Cell cell, Object obj) {
        switch (cell.getCellTypeEnum()) {
        case NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                obj = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
            } else {
                obj = cell.getNumericCellValue();
            }
            break;
        case STRING:
            obj = cell.getStringCellValue();
            break;
        case BOOLEAN:
            obj = cell.getBooleanCellValue();
            break;
        case FORMULA:
            obj = cell.getCellFormula();
            break;

        default:
            break;
        }
        return obj;

    }

}
