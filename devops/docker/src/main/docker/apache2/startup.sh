#!/bin/bash

/usr/sbin/sshd -D &
 
apache2 -D FOREGROUND
