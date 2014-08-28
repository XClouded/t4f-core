#!/bin/bash

# Licensed to the AOS Community (AOS) under one or more
# contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The AOS licenses this file
# to you under the Apache License, Version 2.0 (the 
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

docker run -i -d -t --dns 127.0.0.1 -e NODE_TYPE=m -P --name master -h master.aos.io aosio/hadoop-namenode

NAMENODE_IP=$(docker inspect --format="{{.NetworkSettings.IPAddress}}" master)
for i in `seq 1 5`
do
  sleep 10
  docker run -i -d -t --dns 127.0.0.1 -e NODE_TYPE=s -e JOIN_IP=$NAMENODE_IP -P --name slave${i} -h slave${i}.aos.io aosio/hadoop-datanode
done

docker ps
