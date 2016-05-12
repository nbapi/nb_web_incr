#!/bin/bash
# egg: sh restart_tomcat_by_port 8081
# func: find project depand on tomcat_8081, and restart the project     
tomcat_port=$1
if [ "$1" = "" ]; then
	echo "pelease input tomcat port"
	exit 1
fi
productline=`ps -ef | grep tomcat_$tomcat_port | grep catalina.sh | grep -v grep | awk {'print $9'} | awk -F '/' {'print $4'}`
if [ "$productline" = "" ]; then
	productline=`ps -ef | grep tomcat_$tomcat_port | grep "conf/logging.properties" | grep -v grep | awk {'print $9'} | awk -F '/' {'print $4'}`
fi
if [ "$productline" = "" ]; then
	productline=`find /home/work/ -name tomcat_$tomcat_port -type d | awk -F '/' {'print $4'}`
fi

if [ "$productline" = "" ]; then
	echo "cannot find base dir"
        exit 1
fi

base_dir=/home/work/$productline
##base_file_list=`ls $base_dir`
base_file_list=`find $base_dir -name 'stop_tomcat.sh' | grep "script/stop_tomcat.sh"`
restart_status=0
for base_file in $base_file_list
do
	if [[ "$base_file" =~ "tomcat_" ]]; then
		continue
	elif [[ "$base_file" =~ "WEB-INF/classes" ]]; then 
		continue
	fi
	
	check_port=`cat $base_file | grep $tomcat_port`
	
	if [ "$check_port" = "" ]; then
		continue
	fi
	echo "停止tomcat_$tomcat_port"
        echo "开始执行:sh '$base_file"
        sh $base_file
        echo '执行完成' 
	
	base_file=`echo $base_file | sed 's/stop_tomcat.sh/start_tomcat.sh/g'`

        echo "启动tomcat_$tomcat_port"
        echo "开始执行:sh $base_file"
        sh $base_file
	echo '执行完成'
done
