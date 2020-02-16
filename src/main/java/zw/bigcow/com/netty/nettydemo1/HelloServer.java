package zw.bigcow.com.netty.nettydemo1;

import java.net.InetAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Create by suzhiwu on 2019/02
 */
public class HelloServer {

    private final static int PORT = 9527;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();

            //使用主从Reactor多线程模型
            b.group(bossGroup, workerGroup);

            //使用nio传输方式
            b.channel(NioServerSocketChannel.class);

            //添加我们自己的handler处理流程
            b.childHandler(new HelloServerInitializer());

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(PORT).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        /**
         * 获取pipeline，并定义流程
         */
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer",
                new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloServerHandler());

    }
}

class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 每一次收到消息时执行此方法
     *
     * 字符串最后面的"\n"是必须的。
     * 因为我们在前面的解码器DelimiterBasedFrameDecoder是一个根据字符串结尾为“\n”来结尾的。
     * 假如没有这个字符的话。解码会出现问题。
     *
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

        // 返回客户端消息 - 我已经接收到了你的消息
        ctx.writeAndFlush("Received your message !" + " msg: " + msg + "\n");
    }

    /**
     * 覆盖 channelActive 方法
     * 在channel被启用的时候触发 (在建立连接的时候)
     *
     * 这里channeActive的意思是当连接活跃(建立)的时候触发.输出消息源的远程地址。并返回欢迎消息。
     *
     * 在3.x版本中此处有很大区别。在3.x版本中write()方法是自动flush的。
     * 在4.x版本的前面几个版本也是一样的。但是在4.0.9之后修改为WriteAndFlush。
     * 普通的write方法将不会发送消息。需要手动在write之后flush()一次
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
        ctx.flush();

        super.channelActive(ctx);
    }
}