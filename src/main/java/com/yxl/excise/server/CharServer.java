package com.yxl.excise.server;

import com.yxl.excise.protocol.SharedMessageCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CharServer {

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        SharedMessageCodec sharedMessageCodec = new SharedMessageCodec();
        LoggingHandler loggingHandler = new LoggingHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap().group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(
                                    new LengthFieldBasedFrameDecoder(1024,12,4,0,0));
                            ch.pipeline().addLast(sharedMessageCodec);

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            log.error("server error",e);
        }finally {
           boss.shutdownGracefully();
           worker.shutdownGracefully();
        }

    }
}
