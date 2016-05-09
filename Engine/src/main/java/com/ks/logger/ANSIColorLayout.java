package com.ks.logger;

import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class ANSIColorLayout extends PatternLayout{
	public static final String DEFAULT_COLOR_ALL        = "\u001B[1;37m";
    public static final String DEFAULT_COLOR_FATAL      = "\u001B[0;31;5;7m";
    public static final String DEFAULT_COLOR_ERROR      = "\u001B[0;31m";
    public static final String DEFAULT_COLOR_WARN       = "\u001B[1;33m";
    public static final String DEFAULT_COLOR_INFO       = "\u001B[0;37m";
    public static final String DEFAULT_COLOR_DEBUG      = "\u001B[0;36m";
    public static final String DEFAULT_COLOR_RESET      = "\u001B[1;37m";
    public static final String DEFAULT_COLOR_STACKTRACE = "\u001B[0;31m";
    public static final String DEFAULT_COLOR            = "\u001B[0m";

    public ANSIColorLayout() {
        setDefaultColors();
    }

    /**
     * Constructor for ANSIColorLayout.
     * @param string String
     */
    public ANSIColorLayout(String string) {
        super(string);
        setDefaultColors();
    }

    /**
     * set the color patterns to the defaults
     */
    public void setDefaultColors() {
        all = DEFAULT_COLOR_ALL;
        fatal = DEFAULT_COLOR_FATAL;
        error = DEFAULT_COLOR_ERROR;
        warn = DEFAULT_COLOR_WARN;
        info = DEFAULT_COLOR_INFO;
        debug = DEFAULT_COLOR_DEBUG;
        stacktrace = DEFAULT_COLOR_STACKTRACE;
        defaultcolor = DEFAULT_COLOR;
    }

    /**
     *  All - color string for events that do not have a specified type
     */
    private String all;
    /**
     * Method getAll.
     * @return String
     */
    public String getAll(){return all;}
    /**
     * Method setAll.
     * @param inp String
     */
    public void setAll(String inp){
        all = inp;
    }

    /**
     *  Fatal - color string for fatal events.  Default is red.
     */
    private String fatal;
    /**
     * Method getFatal.
     * @return String
     */
    public String getFatal(){return fatal;}
    /**
     * Method setFatal.
     * @param inp String
     */
    public void setFatal(String inp){
        fatal = inp;
    }

    /**
     *  Error - color string for error events.  Default is red.
     */
    private String error;
    /**
     * Method getError.
     * @return String
     */
    public String getError(){return error;}
    /**
     * Method setError.
     * @param inp String
     */
    public void setError(String inp){
        error = inp;
    }

    /**
     *  Warn - color string for warn events.  Default is yellow.
     */
    private String warn;
    /**
     * Method getWarn.
     * @return String
     */
    public String getWarn(){return warn;}
    /**
     * Method setWarn.
     * @param inp String
     */
    public void setWarn(String inp){
        warn = inp;
    }

    /**
     *  Info - color string for info events.  Default is gray.
     */
    private String info;
    /**
     * Method getInfo.
     * @return String
     */
    public String getInfo(){return info;}
    /**
     * Method setInfo.
     * @param inp String
     */
    public void setInfo(String inp){
        info = inp;
    }

    /**
     *  Debug - color string for debug events.  Default is blue.
     */
    private String debug;
    /**
     * Method getDebug.
     * @return String
     */
    public String getDebug(){return debug;}
    /**
     * Method setDebug.
     * @param inp String
     */
    public void setDebug(String inp){
        debug = inp;
    }

    /**
     *  stacktrace - color string for stacktrace events.  Default is red.
     */
    private String stacktrace;
    /**
     * Method getStacktrace.
     * @return String
     */
    public String getStacktrace(){return stacktrace;}
    /**
     * Method setStacktrace.
     * @param inp String
     */
    public void setStacktrace(String inp){
        stacktrace = inp;
    }

    /**
     *  defaultcolor - default terminal color.  this is the color that the terminal will be reset to after each line.  default is white
     */
    private String defaultcolor;
    /**
     * Method getDefaultcolor.
     * @return String
     */
    public String getDefaultcolor(){return defaultcolor;}
    /**
     * Method setDefaultcolor.
     * @param inp String
     */
    public void setDefaultcolor(String inp){
        defaultcolor = inp;
    }

    /**
     * Method format.
     * @param loggingEvent LoggingEvent
     * @return String
     */
    public String format(LoggingEvent loggingEvent) {

        StringBuffer oBuffer = new StringBuffer();
        switch (loggingEvent.getLevel().toInt()) {
            case Level.ALL_INT:
                oBuffer.append(all);
                break;
            case Level.FATAL_INT:
                oBuffer.append(fatal);
                break;
            case Level.ERROR_INT:
                oBuffer.append(error);
                break;
            case Level.WARN_INT:
                oBuffer.append(warn);
                break;
            case Level.INFO_INT:
                oBuffer.append(info);
                break;
            case Level.DEBUG_INT:
                oBuffer.append(debug);
                break;
            default:
            	break;
        }
        oBuffer.append(super.format(loggingEvent)).append(" ");
        oBuffer.delete(oBuffer.length()-2,oBuffer.length());
        oBuffer.append(defaultcolor).append("\n");
        return oBuffer.toString();
    }
}
