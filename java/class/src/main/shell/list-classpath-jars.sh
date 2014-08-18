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

USAGE=\
'
Usage (to display jar files):
  list-jars-for-classpath.sh directory1 directory2 ...

Usage (to add jar files to CLASSPATH):
  CLASSPATH=$CLASSPATH:$(listJarsForClasspath.sh directory1 directory2 ...)

For each directory on the command line, this script lists all of the .jar
and .zip files in that directory.  The file names are separated by the
path separator character (: or ; depending on platform).
'

if [ $# -lt 1 ]; then
    echo -e "$USAGE"
    exit 1
fi

case $(uname) in
    CYGWIN*) PATHSEP=";"
        ;;
    *) PATHSEP=":"
        ;;
esac

JARLIST=""

addToJarList () {
    # Params: 1 - directory
    #         2 - archive file extension (jar, zip)
    for arcfile in "$1"/*."$2"; do
        if [ -f "$arcfile" ]; then
            # echo "$arcfile"

            if [ ! -z "$JARLIST" ]; then
                JARLIST="$JARLIST$PATHSEP"
            fi

            JARLIST="$JARLIST$arcfile"
        fi
    done
}

for dir in "$@"; do
    if [ ! -e "$dir" ]; then
        echo "Does not exist: $dir"
    elif [ ! -d "$dir" ]; then
        echo "Not a directory: $dir"
    else
        addToJarList "$dir" "jar"
        addToJarList "$dir" "zip"
    fi
done

if [ ! -z "$JARLIST" ]; then
    echo "$JARLIST"
fi
