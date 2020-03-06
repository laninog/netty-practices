package org.training.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.training.messages.Ask;
import org.training.messages.AskParams;
import org.training.messages.Login;

import java.util.concurrent.TimeUnit;

public class OtherClientMain {

    private static String host;
    private static int port;

    private static SocketChannel channel;

    private static Channel nettyClientBootstrap (String host, int port) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .group(group)
                .remoteAddress(host, port)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect(host, port).sync();
        if (future.isSuccess()) {
            System.out.println("Connect server successful---------");
        }

         return future.channel();
    }

    public static void main(String[] args) throws InterruptedException {

        Channel localhost = nettyClientBootstrap("localhost", 9999);

        localhost.writeAndFlush(new Login("AA", "FF"));

        while (true) {

            TimeUnit.SECONDS.sleep(3);

            Ask ask = new Ask(new AskParams("TOKEN"));

            localhost.writeAndFlush(ask);

        }

    }

}