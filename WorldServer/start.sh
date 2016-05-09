#!/bin/sh  
# -----------------------------------------------------------------------------  
# Start script for the CMGP BOSSCONTROL   
#  
# $Id: run_bosscontrol.sh,v 1.0 2007/11/06 Exp $  
# -----------------------------------------------------------------------------  
#指定字符集  
LANG=zh_CN.GBK export LANG  
RUN_HOME=.  
CLASSPATH=$CLASSPATH:$RUN_HOME/bin
CLASSPATH=$CLASSPATH:$RUN_HOME/lib/log4j-1.2.16.jar
CLASSPATH=$CLASSPATH:$RUN_HOME/lib/netty-3.2.7.Final.jar

export CLASSPATH

java -server -Xms800M -Xmx800M -cp $CLASSPATH  com.ks.wrold.kernel.WorldServerKernel >> log.out &