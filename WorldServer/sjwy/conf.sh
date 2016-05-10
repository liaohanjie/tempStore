#!/bin/bash

###
### 游戏全局LIB配置
###

#cd `dirname $0`
_PWD=`pwd`
BASE_LIB_DIR="$_PWD/parent_lib"

world_name="world"
game_name="game"
logic_name="logic"
login_name="login"

web_name="web"
account_name="account"
boss_name="boss"

world_lib="$BASE_LIB_DIR/$world_name"
game_lib="$BASE_LIB_DIR/$game_name"
logic_lib="$BASE_LIB_DIR/$logic_name"
login_lib="$BASE_LIB_DIR/$login_name"

web_lib="$BASE_LIB_DIR/$web_name"
account_lib="$BASE_LIB_DIR/$account_name"
boss_lib="$BASE_LIB_DIR/$boss_name"

export world_name game_name logic_name login_name web_name account_name boss_name
export world_lib game_lib logic_lib login_lib web_lib account_lib boss_lib