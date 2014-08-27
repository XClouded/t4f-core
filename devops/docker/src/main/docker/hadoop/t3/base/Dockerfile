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

FROM aosio/serf

MAINTAINER aos.io

RUN apt-get install -y maven llvm-gcc build-essential zlib1g-dev make cmake pkg-config libssl-dev automake autoconf 

RUN addgroup hadoop
RUN useradd -d /home/hduser -m -s /bin/bash -G hadoop hduser

RUN su hduser -c "ssh-keygen -t rsa -f ~/.ssh/id_rsa -P ''"
RUN su hduser -c "cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys" 
ADD config/ssh_config ./ssh_config
RUN mv ./ssh_config /home/hduser/.ssh/config

RUN chown hduser:hduser /home/hduser/.ssh/config
RUN chmod 600 /home/hduser/.ssh/config

RUN wget http://apache.mirror.anlx.net/hadoop/core/hadoop-2.3.0/hadoop-2.3.0-src.tar.gz 
RUN tar -xvf hadoop-2.3.0-src.tar.gz  

RUN wget https://protobuf.googlecode.com/files/protobuf-2.5.0.tar.gz  
RUN tar -xvf protobuf-2.5.0.tar.gz  
RUN cd protobuf-2.5.0 && ./configure  
RUN cd protobuf-2.5.0 && make  
RUN cd protobuf-2.5.0 && make check  
RUN cd protobuf-2.5.0 && make install  
RUN cd protobuf-2.5.0 && ldconfig  
RUN protoc --version  
#ENV MAVEN_OPTS -Xms512m -XX:MaxPermSize=256 -Xmx512m
RUN cd hadoop-2.3.0-src && mvn package -Pdist,native -DskipTests -Dtar  

RUN  cd hadoop-2.3.0-src && tar -xvf hadoop-dist/target/hadoop-2.3.0.tar.gz -C /usr/local/  
RUN ln -s /usr/local/hadoop-2.3.0 /usr/local/hadoop  
RUN chown -R hduser:hadoop /usr/local/hadoop-2.3.0

ADD config/bashrc /home/hduser/.bashrc

RUN rm -f /usr/local/hadoop/etc/hadoop/hadoop-env.sh 
ADD config/hadoop-env.sh /usr/local/hadoop/etc/hadoop/hadoop-env.sh 

EXPOSE 22
