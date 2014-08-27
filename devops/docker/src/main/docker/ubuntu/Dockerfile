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

FROM ubuntu:utopic
# FROM stackbrew/ubuntu:utopic

MAINTAINER aos.io

RUN apt-get -y update
RUN apt-get install -y maven llvm-gcc build-essential zlib1g-dev make cmake pkg-config libssl-dev automake autoconf 

# Add oracle java 7 repository
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get -y update

# Accept the Oracle Java license
RUN echo "oracle-java7-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections

# Install Oracle Java
RUN apt-get -y install oracle-java7-installer

RUN update-alternatives --display java

ENV JAVA_HOME /usr/lib/jvm/java-7-oracle/ 
ENV PATH $PATH:$JAVA_HOME/bin

RUN apt-get -y install software-properties-common

#RUN (locale-gen en_US en_US.UTF-8 && dpkg-reconfigure locales)
#RUN (DEBIAN_FRONTEND=noninteractive apt-get update && DEBIAN_FRONTEND=noninteractive apt-get upgrade -y -q && DEBIAN_FRONTEND=noninteractive apt-get dist-upgrade -y -q )
#RUN DEBIAN_FRONTEND=noninteractive apt-get install -y nano apt-utils

RUN apt-get install -y openssh-server
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/#UsePAM no/UsePAM no/g' /etc/ssh/sshd_config
RUN sed -ri 's/PermitRootLogin without-password/PermitRootLogin yes/g' /etc/ssh/sshd_config
RUN mkdir /var/run/sshd

RUN echo 'root:root' | chpasswd

RUN /usr/sbin/sshd -D
