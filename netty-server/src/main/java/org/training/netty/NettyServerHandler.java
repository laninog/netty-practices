package org.training.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.training.messages.*;

public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Message message = (Message) msg;
        if (MessageType.LOGIN.equals(message.getType())) {
            Login login = (Login) message;
            if ("AA".equals(login.getUserName()) && "FF".equals(login.getPassword())) {
                NettyChannelMap.add(login.getClientId(), ctx.channel());
                System.out.println("Login successful");
            } else if (NettyChannelMap.get(login.getClientId()) == null) {
                Login requestLogin = new Login();
                ctx.channel().writeAndFlush(requestLogin);
            }
        } else if (MessageType.PING.equals(message.getType())) {
            NettyChannelMap.get(message.getClientId()).writeAndFlush(new Reply(new Reply.ReplyBodyServer("H")));
        } else if (MessageType.ASK.equals(message.getType())) {
            Ask ask = (Ask) message;
            if ("TOKEN".equals(ask.getParams().getAuth())) {
                Reply reply = new Reply(new Reply.ReplyBodyServer("From server"));
                NettyChannelMap.get(ask.getClientId()).writeAndFlush(reply);
            }
        } else if (MessageType.REPLY.equals(message.getType())) {
            Reply body = (Reply) message;
            System.out.println("Receive client message " + body.getBody());
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

    }

}
