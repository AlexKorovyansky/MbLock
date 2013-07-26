===Install protobuf version 2.5.0:

https://code.google.com/p/protobuf/downloads/detail?name=protobuf-2.5.0.zip&can=2&q=

$ ./configure
$ make
$ make check
$ make install

===Generate Java
protoc --java_out=java/ person.proto

===HELP
protoc --help for details