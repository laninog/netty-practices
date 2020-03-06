package org.training.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.training.messages.Ask;
import org.training.messages.AskParams;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class OtherServerMain {

    public static void bootstrapServer(int port) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                // .option(ChannelOption.TCP_NODELAY, true)
                .localAddress(new InetSocketAddress("localhost", 9999));


        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ObjectEncoder());
                        p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                        p.addLast(new NettyServerHandler());
                    }
                });

        ChannelFuture f = bootstrap.bind().sync();

        if (f.isSuccess()) {
            System.out.println("Server start...");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        bootstrapServer(9999);

        while (true) {

            Channel channel = NettyChannelMap.get("001");
            if (channel != null) {
                channel.writeAndFlush(new Ask(new AskParams("Who are you?")));
            }

            TimeUnit.SECONDS.sleep(5);

        }

    }

}
