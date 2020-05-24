# CMakeList.txt: проект CMake для Jetson Nano; включите исходный код и определения,
# укажите здесь логику для конкретного проекта.
#
cmake_minimum_required (VERSION 3.8)

project ("Jetson Nano")

# Добавьте источник в исполняемый файл этого проекта.
add_executable (CMakeTarget "Jetson Nano.cpp" "Jetson Nano.h")

# TODO: Добавьте тесты и целевые объекты, если это необходимо.
