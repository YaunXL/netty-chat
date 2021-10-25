package com.yxl.excise.protocol;

import com.yxl.excise.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        //1. 4字节的魔数
        byteBuf.writeBytes(new byte[]{1,2,3,4});
        //2. 1字节的版本号
        byteBuf.writeByte(1);
        //3. 1字节序列化方式
        byteBuf.writeByte(0);
        //4. 1字节的指令类型
        byteBuf.writeByte(message.getMessageType());
        //5. 4个字节的请求序号
        byteBuf.writeInt(message.getSequenceId());
        byteBuf.writeByte(0xff);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();
        //6. 正文长度
        byteBuf.writeInt(bytes.length);
        //7. 正文内容
        byteBuf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serilizer = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes,0,len);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message msg = (Message) ois.readObject();
        list.add(msg);
        log.debug("{},{},{},{},{},{}",magicNum,version,serilizer,messageType,sequenceId,len);
        log.debug("{}",msg);


    }
}
