package io.aos.netty5.chp12;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;

import javax.net.ssl.SSLEngine;

import org.eclipse.jetty.npn.NextProtoNego;

/**
 * @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
 */
public class DefaultSpdyOrHttpChooser extends SpdyOrHttpChooser {

    public DefaultSpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
        super(maxSpdyContentLength, maxHttpContentLength);
    }

    @Override
    protected SelectedProtocol getProtocol(SSLEngine engine) {
        DefaultServerProvider provider = (DefaultServerProvider) NextProtoNego.get(engine);
        String protocol = provider.getSelectedProtocol();
        if (protocol == null) {
            return SelectedProtocol.UNKNOWN;
        }
        switch (protocol) {
            case "spdy/3.1":
                return SelectedProtocol.SPDY_3_1;
            case "http/1.1":
                return SelectedProtocol.HTTP_1_1;
            default:
                return SelectedProtocol.UNKNOWN;
        }
    }

    @Override
    protected ChannelHandler createHttpRequestHandlerForHttp() {
        return new HttpRequestHandler();
    }

    @Override
    protected ChannelHandler createHttpRequestHandlerForSpdy() {
        return new SpdyRequestHandler();
    }
}
