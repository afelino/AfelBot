# CMakeList.txt: проект CMake для Jetson Nano; включите исходный код и определения,
# укажите здесь логику для конкретного проекта.
#
cmake_minimum_required (VERSION 3.8)

project ("main" LANGUAGES CXX CUDA)

# Добавьте источник в исполняемый файл этого проекта.
add_executable (AfelBot "main.cu" "main.h")

# TODO: Добавьте тесты и целевые объекты, если это необходимо.

# We need to explicitly state that we need all CUDA files in the 
# particle library to be built with -dc as the member functions 
# could be called by other libraries and executables
set_target_properties( AfelBot
                       PROPERTIES CUDA_SEPARABLE_COMPILATION ON)