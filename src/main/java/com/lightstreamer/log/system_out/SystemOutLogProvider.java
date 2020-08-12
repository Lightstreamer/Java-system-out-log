/*
 * Copyright (c) 2004-2015 Weswit s.r.l., Via Campanini, 6 - 20124 Milano, Italy.
 * All rights reserved.
 * www.lightstreamer.com
 *
 * This software is the confidential and proprietary information of
 * Weswit s.r.l.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Weswit s.r.l.
 */
package com.lightstreamer.log.system_out;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.lightstreamer.log.Logger;
import com.lightstreamer.log.LoggerProvider;

/**
 * 
 */
public class SystemOutLogProvider implements LoggerProvider {
  
  HashMap<String,Logger> loggers = new HashMap<String,Logger>();
  private boolean printDebug = true;
  private boolean printInfo = true;
  
  private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yy HH:mm:ss,SSS");
  
  @Override
  public Logger getLogger(String category) {
    if (loggers.containsKey(category)) {
      return loggers.get(category);
    }
    
    Logger newLogger = new SystemOutLogger(category);
    loggers.put(category, newLogger);
    return newLogger;
  }
  
  public void disableDebug(boolean disabled) {
    this.printDebug = !disabled;
  }
  
  public void disableInfo(boolean disabled) {
    this.printInfo = !disabled;
  }
  
  private class SystemOutLogger implements Logger {

    private String cat;
   
    public SystemOutLogger(String category) {
      this.cat = category;
    }

    @Override
    public void error(String line) {
      
      System.err.println(date()+"|ERROR|"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
    }

    @Override
    public void error(String line, Throwable exception) {
      System.err.println(date()+"|ERROR|"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
      exception.printStackTrace();
    }

    @Override
    public void warn(String line) {
      System.err.println(date()+"|WARN |"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
    }

    @Override
    public void warn(String line, Throwable exception) {
      System.err.println(date()+"|WARN |"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
      exception.printStackTrace();
    }

    @Override
    public void info(String line) {
      if (!printInfo) {
        return;
      }
      System.out.println(date()+"|INFO |"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
    }

    @Override
    public void info(String line, Throwable exception) {
      if (!printInfo) {
        return;
      }
      System.out.println(date()+"|INFO |"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
      exception.printStackTrace();
    }

    @Override
    public void debug(String line) {
      if (!printDebug) {
        return;
      }
      System.out.println(date()+"|DEBUG|"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
    }

    @Override
    public void debug(String line, Throwable exception) {
      if (!printDebug) {
        return;
      }
      System.out.println(date()+"|DEBUG|"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
      exception.printStackTrace();
    }

    @Override
    public void fatal(String line) {
      System.err.println(date()+"|FATAL|"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
    }

    @Override
    public void fatal(String line, Throwable exception) {
      System.err.println(date()+"|FATAL|"+this.cat+"|"+Thread.currentThread().getName()+"|"+line);
      exception.printStackTrace();
    }

    /**
     * @return
     */
    private String date() {
      Date ts = new Date();
      synchronized (formatter) {
          return formatter.format(ts);
      }
    }
    
    @Override
    public boolean isDebugEnabled() {
      return printDebug;
    }

    @Override
    public boolean isInfoEnabled() {
      return printInfo;
    }

    @Override
    public boolean isWarnEnabled() {
      return true;
    }

    @Override
    public boolean isErrorEnabled() {
      return true;
    }

    @Override
    public boolean isFatalEnabled() {
      return true;
    }
    
  }
  

}
