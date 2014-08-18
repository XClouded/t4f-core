package io.aos.netty5.chp12;

import io.netty.channel.ChannelHandler;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
@ChannelHandler.Sharable
public class SpdyRequestHandler extends HttpRequestHandler {
    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\r\n";
    }
}
