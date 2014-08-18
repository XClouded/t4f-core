################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/main/c/ascii.c \
../src/main/c/hello1.c \
../src/main/c/hello2.c \
../src/main/c/main.c \
../src/main/c/sub.c 

OBJS += \
./src/main/c/ascii.o \
./src/main/c/hello1.o \
./src/main/c/hello2.o \
./src/main/c/main.o \
./src/main/c/sub.o 

C_DEPS += \
./src/main/c/ascii.d \
./src/main/c/hello1.d \
./src/main/c/hello2.d \
./src/main/c/main.d \
./src/main/c/sub.d 


# Each subdirectory must supply rules for building sources it contributes
src/main/c/%.o: ../src/main/c/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


