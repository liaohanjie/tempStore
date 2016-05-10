#!/bin/bash

if [ $# != 1 ]; then
  echo "useage: $0 [number]"
  exit -1
fi

cd `dirname $0`
_PWD=`pwd`

BASE_SERVER_PATH="$_PWD/sjwy_$1"

server_world="world_$1"
server_game="game_$1"
server_logic="logic_$1"
server_login="login_$1"

server_names="$server_world |$server_game |$server_logic |$server_login "
game_servers=($server_world $server_game $server_logic $server_login)

# 循环数组
for s in ${game_servers[*]}
  do

    if [ ! -d "${BASE_SERVER_PATH}/${s}" ]; then
      echo "${BASE_SERVER_PATH}/${s} is not existed."
      exit -1
    fi

  done


echo "stoping all servers. [$server_names]..."
ps aux | grep -v grep | grep java | grep -E 'instance.id=' | grep -E "($server_names)" | awk '{print $2}' | xargs kill

#exit 1

path_cd=$BASE_SERVER_PATH/$server_world
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg
sleep 5s

path_cd=$BASE_SERVER_PATH/$server_game
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg
sleep 5s

path_cd=$BASE_SERVER_PATH/$server_logic
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg
sleep 5s

path_cd=$BASE_SERVER_PATH/$server_login
echo "start $path_cd ..."
cd $path_cd
./start.sh -bg

echo "start complete!"


