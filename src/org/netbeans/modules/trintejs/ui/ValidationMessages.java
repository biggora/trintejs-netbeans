package org.netbeans.modules.trintejs.ui;

/**
 * @author Aleks
 */
public enum ValidationMessages {
    IS_REQUIRED("%s is required."),
    IS_TOO_SHORT("%s is too short, please enter at least %d characters."),
    IS_NOT_ONLY_CHARACTERS("%s must contains only characters."),
    IS_NOT_SINGULAR("%s must by singular not plural.")
;
    /**
     * @param text
     */
    private ValidationMessages(final String message) {
        this.message = message;
    }

    private final String message;

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return message;
    }
}