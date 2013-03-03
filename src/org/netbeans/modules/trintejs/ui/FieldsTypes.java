package org.netbeans.modules.trintejs.ui;

/**
 * @author Aleks
 */
public enum FieldsTypes {

    CHAR("Char"),
    VARCHAR("Varchar"),
    STRING("String"),
    TEXT("Text"),
    INTEGER("Integer"),
    LONG("Long"),
    DOUBLE("Double"),
    BOOLEAN("Boolean"),
    DATE("Date");

    /**
     * @param fieldType
     */
    private FieldsTypes(final String fieldType) {
        this.fieldType = fieldType;
    }
    private final String fieldType;

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return fieldType;
    }
}