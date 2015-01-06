/**
 * 
 */
package com.gdc.nms.web.mibquery;
import java.io.IOException;

public class SnmpConnectorException extends Exception {

    private static final long serialVersionUID = -173520499143801202L;

    public static final int UNKNOWN = 0;

    public static final int SNMP_ERROR = 1;

    public static final int IO_EXCEPTION = 2;

    public static final int TIMEOUT = 3;

    public static final int AUTH_FAILED = 4;

    public static final int UNKNOWN_USERNAME = 5;

    private static String[] CAUSE_DESCRIPTIONS = { "Unknown error", "SNMP Error", "IO Exception", "Timeout",
            "Authentication failed", "Unknown user name" };

    private int causeCode;

    private int snmpErrorCode;

    /**
     * 
     * @param causeCode
     * @param snmpErrorCode
     * @param message
     */
    public SnmpConnectorException(int causeCode, int snmpErrorCode, String message) {
        super(message);
        this.causeCode = causeCode;
        this.snmpErrorCode = snmpErrorCode;
    }

    /**
     * 
     * @param causeCode
     * @param message
     */
    public SnmpConnectorException(int causeCode, String message) {
        this(causeCode, -1, message);
    }

    /**
     * 
     * @param causeCode
     */
    public SnmpConnectorException(int causeCode) {
        this(causeCode, CAUSE_DESCRIPTIONS[causeCode]);
    }

    /**
     * @param cause
     */
    public SnmpConnectorException(IOException cause) {
        super(cause);
        causeCode = IO_EXCEPTION;
    }

    /**
     * 
     * @param message
     * @param cause
     */
    public SnmpConnectorException(String message, Throwable cause) {
        super(message, cause);

        if (cause instanceof IOException) {
            causeCode = IO_EXCEPTION;
        }
    }

    /**
     * @return the causeCode
     */
    public int getCauseCode() {
        return causeCode;
    }

    /**
     * @return the snmpErrorCode
     */
    public int getSnmpErrorCode() {
        return snmpErrorCode;
    }

    public boolean isUnknown() {
        return causeCode == UNKNOWN;
    }

    public boolean isSnmpError() {
        return causeCode == SNMP_ERROR;
    }

    public boolean isIoException() {
        return causeCode == IO_EXCEPTION;
    }

    public boolean isTimeout() {
        return causeCode == TIMEOUT;
    }

    public boolean isAuthFailed() {
        return causeCode == AUTH_FAILED;
    }

    public boolean isUnknownUserName() {
        return causeCode == UNKNOWN_USERNAME;
    }
}
