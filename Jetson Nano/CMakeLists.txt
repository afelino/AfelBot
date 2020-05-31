# CMakeList.txt: проект CMake для Jetson Nano; включите исходный код и определения,
# укажите здесь логику для конкретного проекта.
#
cmake_minimum_required (VERSION 3.10)

project (AfelBot VERSION 2.0 LANGUAGES CXX CUDA)

# Через этот файл версия приложения будет проброшена в само приложение.
configure_file(AfelBotConfig.h.in AfelBotConfig.h)

# Стандарт C++
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)

# Поиск библиотеки libevent
find_package(Libevent CONFIG REQUIRED)
include_directories(${LIBEVENT_INCLUDE_DIRS})

# Добавьте источник в исполняемый файл этого проекта.
add_executable (AfelBot "main.cu" "main.h" )

# We need to explicitly state that we need all CUDA files in the 
# particle library to be built with -dc as the member functions 
# could be called by other libraries and executables
set_target_properties( AfelBot
                       PROPERTIES CUDA_SEPARABLE_COMPILATION ON)

target_include_directories(AfelBot PUBLIC ${PROJECT_BINARY_DIR})

target_link_libraries(AfelBot PRIVATE event_shared event_core_shared event_extra_shared)
