#!/bin/bash

docker run -d -t --dns 127.0.0.1 -e NODE_TYPE=s -P --name slave1 -h slave1.mycorp.kom aosio/hadoop-datanode

SLAVE_1_IP=$(docker inspect --format="{{.NetworkSettings.IPAddress}}" slave1)
echo SLAVE_1_IP=${SLAVE_1_IP}

docker run -i -t --dns 127.0.0.1 -e NODE_TYPE=m -e JOIN_IP=$SLAVE_1_IP -P --name master -h master.mycorp.kom aosio/hadoop-namenode
