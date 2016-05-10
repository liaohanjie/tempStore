#!/bin/bash

_IP="119.29.107.126"

_SRC_DIR="/data/sjwy"
_DST_IDR="/data/sjwy"

fn_help(){
    echo "useage: $0 [all|game|web|boss]"
}

fn_deploy_web(){
    echo "deploy sjwy web war: "
    rsync -avzcP ${_SRC_DIR}/web/web_1/war/ root@${_IP}:${_DST_IDR}/web/web_1/war/
}

fn_deploy_boss(){
    echo "deploy sjwy boss war: "
    rsync -avzcP ${_SRC_DIR}/web/boss_1/war/ root@${_IP}:${_DST_IDR}/web/boss_1/war/
}

fn_deploy_game(){
    echo "deploy sjwy all lib: "
    rsync -avzcP ${_SRC_DIR}/parent_lib/ root@${_IP}:${_DST_IDR}/parent_lib
}

fn_deploy_all(){
   fn_deploy_web
   fn_deploy_boss
   fn_deploy_game
}

case $1 in
web)
    fn_deploy_web
    ;;
boss)
    fn_deploy_boss
    ;;
game)
    fn_deploy_game
    ;;
all)
    fn_deploy_all
    ;;
*)
    fn_help
    ;;
esac
