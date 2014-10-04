################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/main/cpp/hello1.cpp \
../src/main/cpp/hello2.cpp \
../src/main/cpp/main.cpp \
../src/main/cpp/main2.cpp \
../src/main/cpp/test.cpp \
../src/main/cpp/test2.cpp 

C_SRCS += \
../src/main/cpp/print_hello.c \
../src/main/cpp/test1.c 

OBJS += \
./src/main/cpp/hello1.o \
./src/main/cpp/hello2.o \
./src/main/cpp/main.o \
./src/main/cpp/main2.o \
./src/main/cpp/print_hello.o \
./src/main/cpp/test.o \
./src/main/cpp/test1.o \
./src/main/cpp/test2.o 

C_DEPS += \
./src/main/cpp/print_hello.d \
./src/main/cpp/test1.d 

CPP_DEPS += \
./src/main/cpp/hello1.d \
./src/main/cpp/hello2.d \
./src/main/cpp/main.d \
./src/main/cpp/main2.d \
./src/main/cpp/test.d \
./src/main/cpp/test2.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/cpp/%.o: ../src/main/cpp/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cross G++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

src/main/cpp/%.o: ../src/main/cpp/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


