#!/usr/bin/env bash

REPOSITORY=~/app/iterview
cd $REPOSITORY

APP_NAME=iterview
JAR_NAME=$(ls $REPOSITORY/target/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/target/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 프로세스가 없습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
