package org.netbeans.modules.trintejs.tools;

import java.util.Arrays;

/**
 * @author Aleks
 */
public class GeneratorHelper {

    public static String CreateFields(String[] modelFields) {
        int count = modelFields.length;
        String templateField = "	         FIELDNAME : { type: FIELDTYPE }";
        String templateDef = ", 'default' : TPLDEF";
        String[] fields = new String[count];

        for (int i = 0; i < count; i++) {
            String[] fieldArg = modelFields[i].split(":");
            String fieldType = fieldArg[1];
            if (fieldArg[2] == null || "null".equals(fieldArg[2])) {
            } else {
                String defval;
                if("true".equals(fieldArg[2].toLowerCase()) || "false".equals(fieldArg[2].toLowerCase()) || DataType.isNumber(fieldArg[2])) {
                    defval = fieldArg[2];
                } else {
                    defval = "'" + fieldArg[2] + "'";
                }
                fieldType += templateDef.replace("TPLDEF", defval);
            }
            String field = templateField.replace("FIELDNAME", fieldArg[0]).replace("FIELDTYPE", fieldType);
            fields[i] = field;
        }
        return Arrays.asList(fields).toString().replaceAll(", ", ",\n").replaceAll("^\\[|\\]$", "");
    }
}
