/*
** This program writes a 4-byte integer to a file (you may need to change
** the name of this file if you're not running on Linux).
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc, char** argv) 
{    
    int fd = creat("/tmp/example.dat", 0777);
    if (fd < 0)
    {
        perror("unable to create file");
        return(1);
    }

    int value = 0x12345678;
    write(fd, &value, sizeof(value));

    close(fd);
    
    return (0);
}

