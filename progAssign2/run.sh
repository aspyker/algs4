#!/bin/sh
DEBUG=-agentpath:/Applications/YourKit_Java_Profiler_2013_build_13086.app/bin/mac/libyjpagent.jnilib
java $DEBUG -cp ../stdlib.jar:../algs4.jar:bin Deque
