package org.training.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.training.messages.Ask;
import org.training.messages.Login;
import org.training.messages.Message;
import org.training.messages.Reply;

public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        switch (msg.getType()) {
            case LOGIN:
                ctx.writeAndFlush(new Login("AA", "FF"));
                break;
            case PING:
                System.out.println("Receiving PING from server");
                break;
            case ASK:
                System.out.println(((Ask)msg).getParams());
                ctx.writeAndFlush(new Reply.ReplyBodyClient("Server"));
                break;
            case REPLY:
                Reply reply = (Reply) msg;
                System.out.println(reply);
                break;
            default:
                break;
        }

    }

}
