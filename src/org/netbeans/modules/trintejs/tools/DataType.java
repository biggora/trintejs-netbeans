package org.netbeans.modules.trintejs.tools;

/**
 * @author Aleks
 */
public class DataType {

    /**
     * String to int convert test
     */
    public static boolean isNumber(String data) {
        try {
            int value = Integer.parseInt(data);
            if (value >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Return only allowed data types
     */
    public static String fieldType(String type) {
        if (type.equals("Char") || type.equals("Varchar") || type.equals("Text")) {
            type = "String";
        } else if (type.equals("Integer") || type.equals("Long") || type.equals("Double")) {
            type = "Number";
        }
        return type;
    }
}
