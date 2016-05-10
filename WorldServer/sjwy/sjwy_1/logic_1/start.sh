#!/bin/bash 

cd `dirname $0`
BIN_DIR=`pwd`
BASE_DIR=`basename $BIN_DIR`

if [ -z $logic_lib ]; then
    cd ../../
    . conf.sh
    cd $BIN_DIR
fi

jvmArgs=`cat jvm.args`
GAME_OPTS="-Dinstance.id=$BASE_DIR -Dbase.dir=$BIN_DIR"

if [[ $1 = "-bg" ]]; then
	#java -cp ./bin -Djava.ext.dirs=lib $GAME_OPTS $jvmArgs com.ks.logic.kernel.LogicServerKernel >> log/log.log 2>&1 &
	java -cp ./bin -Djava.ext.dirs=$logic_lib $GAME_OPTS $jvmArgs com.ks.logic.kernel.LogicServerKernel 2&>/dev/null &
        echo $! >server.pid
else
	java -cp ./bin -Djava.ext.dirs=$logic_lib $GAME_OPTS $jvmArgs com.ks.logic.kernel.LogicServerKernel
fi

