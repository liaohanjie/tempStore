#!/bin/bash

cd `dirname $0`
_PWD=`pwd`

server_account="account_1"
server_web="web_1"
server_boss="boss_1"

SERVER_PATH="$_PWD/web"

server_names="$server_account |$server_web |$server_boss "

echo "stoping all servers. [$server_names]..."
ps aux | grep -v grep | grep java | grep -E 'instance.id=' | grep -E "($server_names)" | awk '{print $2}' | xargs kill

path_cd=$SERVER_PATH/$server_account
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg
sleep 5s

path_cd=$SERVER_PATH/$server_web
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg

path_cd=$SERVER_PATH/$server_boss
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg

echo "web start complete!"
