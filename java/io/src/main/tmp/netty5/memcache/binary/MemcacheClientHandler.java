/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.aos.netty5.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.DefaultFullBinaryMemcacheRequest;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.util.CharsetUtil;

public class MemcacheClientHandler extends ChannelHandlerAdapter {

    /**
     * Transforms basic string requests to binary memcache requests
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        String command = (String) msg;
        if (command.startsWith("get ")) {
            String key = command.substring("get ".length());

            BinaryMemcacheRequest req = new DefaultBinaryMemcacheRequest(key);
            req.setOpcode(BinaryMemcacheOpcodes.GET);
            req.setKeyLength((short) key.length());
            req.setTotalBodyLength(key.length());

            ctx.write(req, promise);
        } else if (command.startsWith("set ")) {
            String[] parts = command.split(" ", 3);
            if (parts.length < 3) {
                throw new IllegalArgumentException("Malformed Command: " + command);
            }
            String key = parts[1];
            String value = parts[2];

            ByteBuf content = Unpooled.wrappedBuffer(value.getBytes(CharsetUtil.UTF_8));
            ByteBuf extras = ctx.alloc().buffer(8);

            BinaryMemcacheRequest req = new DefaultFullBinaryMemcacheRequest(key, extras, content);
            req.setOpcode(BinaryMemcacheOpcodes.SET);
            req.setKeyLength((short) key.length());
            req.setExtrasLength((byte) 8);
            req.setTotalBodyLength(key.length() + 8 + value.length());

            ctx.write(req, promise);
        } else {
            throw new IllegalStateException("Unknown Message: " + msg);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FullBinaryMemcacheResponse res = (FullBinaryMemcacheResponse) msg;
        System.out.println(res.content().toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
