#!/bin/bash 

cd `dirname $0`
BIN_DIR=`pwd`
BASE_DIR=`basename $BIN_DIR`

if [ -z $login_lib ]; then
    cd ../../
    . conf.sh
    cd $BIN_DIR
fi

jvmArgs=`cat jvm.args`
GAME_OPTS="-Dinstance.id=$BASE_DIR -Dbase.dir=$BIN_DIR"

if [[ $1 = "-bg" ]]; then
        java -cp ./bin -Djava.ext.dirs=$login_lib $GAME_OPTS $jvmArgs com.ks.login.kernel.LoginServerKernel >>/dev/null &
        echo $! >server.pid
else
        java -cp ./bin -Djava.ext.dirs=$login_lib $GAME_OPTS $jvmArgs com.ks.login.kernel.LoginServerKernel
fi
