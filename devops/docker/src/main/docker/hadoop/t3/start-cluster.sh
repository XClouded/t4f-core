#!/bin/bash

docker run -d -t --dns 127.0.0.1 -e NODE_TYPE=s -P --name slave1 -h slave1.datalayer.io aosio/hadoop-datanode

FIRST_IP=$(docker inspect --format="{{.NetworkSettings.IPAddress}}" slave1)

docker run -i -t --dns 127.0.0.1 -e NODE_TYPE=m -e JOIN_IP=$FIRST_IP -P --name master -h master.datalayer.io aosio/hadoop-namenode
