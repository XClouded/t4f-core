package io.aos.netty5.chp15;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class RegisterSocket {

    public void register(java.nio.channels.SocketChannel socket, EventLoop eloop) {
        java.nio.channels.SocketChannel mySocket = socket;
        SocketChannel ch = new NioSocketChannel(eloop, mySocket);
        EventLoopGroup group = eloop;
        // TODO(eric)
//        group.(ch);
    }


}
