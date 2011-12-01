#! /bin/bash
SVR=$1
RMI="rmi://"
SVC="/NetworkDiagSvr"
SERVER=$RMI$SVR$SVC
echo Connecting to $SERVER
java Viewer $SERVER
