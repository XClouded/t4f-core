This example demonstrates wrapping an existing chunk of memory with a direct
ByteBuffer via JNI. I use a System V Shared Memory segment, which means that
you will only be able to build and run this example on a Unix-link system.

Build

    Since this example only contains 4 source files, and mixes Java and C
    code, I decided not to use a build tool. Instead, run "build.sh" from
    the top-level directory. As long as you've set JAVA_HOME, and have the
    Java and C compilers on your path, it should work fine. And if not,
    you should be able to figure out the changes easily enough.

    There are three top-level directories:

        "src"   contains the source code; it has sub-directories "java"
                and "native"
        "build" is a staging area for the build; it also has sub-directories
                for Java and JNI code
        "dist"  will contain the resulting JAR and shared library.

    If you're lucky, you can skip the build step entirely. I've already built
    the project using Ubuntu 10.04 32-bit.

Run

    From the top-level directory, execute the following, replacing "KEY" with
    any 32-bit number:

        java -Djava.library.path=dist -jar dist/sharedmem.jar KEY

    You'll see a ">" prompt. If you type some text, the program will first
    display whatever's in the shared memory buffer and then replace it with
    your text. If you just hit <Enter>, you'll see whatever's in the buffer
    but it won't be replaced. If you hit <Ctrl-D> (end-of-file), then the
    program will exit.

    You can start as many processes as you like; as long as they all use the
    same key, they'll all see the same buffer.

    When finished, you'll have to manually remove the shared memory buffer:

        ipcrm -M KEY

Caveats

    This is in no way meant to be production code. For one thing, it doesn't
    attempt to synchronize access to the shared memory; a real program would
    use an inter-process semaphore, and wouldn't expose the actual ByteBuffer.
    Second, it doesn't clean up after itself; once the shared memory block is
    created, it remains in the kernel mappings until explicitly removed. And
    finally, shared memory is a 1980s techique for inter-process communication;
    there is almost always a better alternative (typically message queues).
