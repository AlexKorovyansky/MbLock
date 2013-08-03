===Install protobuf version 2.5.0:

https://code.google.com/p/protobuf/downloads/detail?name=protobuf-2.5.0.zip&can=2&q=

===Compile protoc
$ ./configure
$ make
$ make check
$ make install

===Install python module
$ python setup.py build
$ python setup.py test
$ python setup.py install

If you have error while loading shared libraries on Linux, try this command:
$ sudo ldconfig

===Generate Java
protoc --java_out=java/ person.proto

===Generate Python
protoc --python_out=python/ proto/person.proto --proto_path=proto/

===HELP
protoc --help for details