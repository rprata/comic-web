# comic-web
CRUD Example using angularjs and node

## Requirements
* [Nodejs](https://nodejs.org/)
* [Android SDK and Android Studio](https://developer.android.com/)

## Installation
Follow the instructions for server side:
```sh
$ cd comics-app
$ cd server
$ npm install
$ npm start
```

The application will be running in port 8080: 
* localhost.com.br:8080/index - Dashboard (frontend app)

In case of cross compilation:
```sh
$ cd ibutterfree
$ mkdir build
$ cmake CMakeLists.txt -Bbuild -DCMAKE_TOOLCHAIN_FILE=toolchains/<toolchain_file>.cmake
$ cd build
$ make
```
