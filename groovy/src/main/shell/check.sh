#! /bin/sh

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

TMP=/tmp; export TMP
TMPDIR=$TMP; export TMPDIR

ORACLE_BASE=/u01/app/oracle; export ORACLE_BASE
ORACLE_HOME=$ORACLE_BASE/product/10.2.0/db_1; export ORACLE_HOME
ORACLE_SID=PROD; export ORACLE_SID
ORACLE_TERM=xterm; export ORACLE_TERM
PATH=/usr/sbin:$PATH; export PATH
PATH=$ORACLE_HOME/bin:$PATH; export PATH

LD_LIBRARY_PATH=$ORACLE_HOME/lib:/lib:/usr/lib; export LD_LIBRARY_PATH
CLASSPATH=$ORACLE_HOME/JRE:$ORACLE_HOME/jlib:$ORACLE_HOME/rdbms/jlib; export CLASSPATH

#begin of scritps

. /usr/local/nagios/libexec/utils.sh

OK_STATE=0
WARNING_STATE=1
CRITICAL_STATE=2


var=$(sqlplus  -s ehub/ehubadmin@PROD <<EOF | tail -1
set feedback off;
select count('xXx') from message where status='STATUS_ERROR' and message_type='INVOICE' and message.SENDER like 'BT%' and message.ENTRY_DATE<sysdate-0.16 and message.ENTRY_DATE>TO_DATE('01/01/2008','dd/MM/YYYY');
EOF
)

var2=$(sqlplus  -s ehub/ehubadmin@PROD <<EOF | tail -1
set feedback off;
select count('xXx') from message where status='STATUS_ERROR' and message_type='INVOICE' and message.SENDER like 'BT%' and message.ENTRY_DATE>sysdate-0.16 and message.ENTRY_DATE>TO_DATE('01/01/2008','dd/MM/YYYY');
EOF
)



if [ $var = 0 ] ; then
	if [ $var2 = 0 ] ; then
		echo "OK: $var INVOICE in error younger than 4 hours \n"
		exit $OK_STATE
	else echo "WARNING: $var2 INVOICE in error younger than 4 hours \n"
		 exit $WARNING_STATE
	fi
else
		echo "ERROR: $var2 INVOICE in error younger than 4 hours; $var INVOICE older than 4 hours"
		exit $CRITICAL_STATE
fi

