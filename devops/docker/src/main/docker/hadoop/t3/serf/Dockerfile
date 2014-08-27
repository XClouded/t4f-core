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

# Creates a base ubuntu image with serf and dnsmasq
#
# It aims to create a dynamic cluster of docker containers
# each able to refer other by fully qulified domainnames
#
# This isn't trivial as docker has readonly /etc/hosts
#
# The docker images was directly taken from sequenceiq and converetd to ubuntu image
# because I wanted to create the cluster on ubuntu.

FROM aosio/ubuntu:utopic

MAINTAINER aos.io

RUN apt-get install -y dnsmasq unzip curl

# dnsmasq configuration
ADD dnsmasq.conf /etc/dnsmasq.conf
ADD resolv.dnsmasq.conf /etc/resolv.dnsmasq.conf

# install serfdom.io
RUN curl -Lso /tmp/serf.zip https://dl.bintray.com/mitchellh/serf/0.5.0_linux_amd64.zip
RUN unzip /tmp/serf.zip -d /bin

ENV SERF_CONFIG_DIR /etc/serf

# configure serf
ADD serf-config.json $SERF_CONFIG_DIR/serf-config.json

ADD event-router.sh $SERF_CONFIG_DIR/event-router.sh
RUN chmod +x  $SERF_CONFIG_DIR/event-router.sh

ADD handlers $SERF_CONFIG_DIR/handlers

ADD start-serf-agent.sh  $SERF_CONFIG_DIR/start-serf-agent.sh
RUN chmod +x  $SERF_CONFIG_DIR/start-serf-agent.sh

EXPOSE 7373 7946

CMD /etc/serf/start-serf-agent.sh
