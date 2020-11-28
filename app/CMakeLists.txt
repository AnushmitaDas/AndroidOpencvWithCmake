cmake_minimum_required(VERSION 3.10.1)

set(CMAKE_VERBOSE_MAKEFILE on)
set(CMAKE_CXX_FLAGS "-std=c++0x")
set(CMAKE_CXX_FLAGS "-fstack-protector-all -fvisibility=hidden -ffunction-sections -fdata-sections")
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -fuse-ld=gold -Wl,--gc-sections -fvisibility=hidden -Wl,--strip-debug" )

include_directories(${CMAKE_SOURCE_DIR}/src/main/jniIncludes)

add_library(native-lib
            SHARED
            src/main/cpp/native-lib.cpp)

add_library(lib_opencv SHARED IMPORTED)

set_target_properties(lib_opencv PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so)

find_library(log-lib
             log)

target_link_libraries(native-lib ${log-lib} lib_opencv)