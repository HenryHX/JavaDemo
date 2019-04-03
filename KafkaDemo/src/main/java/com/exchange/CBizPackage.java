package com.exchange;

import com.exchange.PBEntity.IEntity;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 对消息传输包的封装， 包括一个Header 以及多个Field 这个传输包已经把Field解析到Map中
 */
public class CBizPackage {

    private static Logger logger = LoggerFactory.getLogger(CBizPackage.class);

    public CBizPackage() {
        header = new CBizPackageHeader();

        fieldMap = new HashMap<Integer, IEntity>();
    }

    public CBizPackage(int tID) {
        this();
        header.settID(tID);
    }

    public CBizPackageHeader getHeader() {
        return header;
    }

    /**
     * 从Buf中读取整个包
     * 
     * @param buf
     * @return
     */
    public boolean readFromBuf(ByteBuf buf) {
        boolean ret = header.readFromBuf(buf);

        if (!ret)
            return false;

        try {
            for (int cnt = 0; cnt < header.fieldCount; ++cnt) {
                if (buf.readableBytes() < 2 * Integer.SIZE / Byte.SIZE)
                    return false;

                int fieldId = StringEncodeHelper.ctoji(buf.readInt());
                int fieldLen = StringEncodeHelper.ctoji(buf.readInt());

                Class<? extends IEntity> clz = PBEntity.getClz(fieldId);

                if (clz == null) {
                    logger.error("invalid proto filed id {}", fieldId);
                    return false;
                }

                if (buf.readableBytes() < fieldLen) {
                    return false;
                }

                byte[] by = new byte[fieldLen];
                buf.readBytes(by);

                IEntity entity;
                try {
                    entity = clz.newInstance();
                    entity.parseFrom(by);

                    fieldMap.put(fieldId, entity);

                } catch (Exception e) {
                    logger.error("parse protobuf error ", e);

                    return false;
                }

            }
        } finally {
            header.setFieldCount(fieldMap.size());
        }

        return true;
    }

    /**
     * 获取某个Field对象
     * 
     * @param clz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends IEntity> T getField(Class<T> clz) {
        Integer fid = PBEntity.getFieldID(clz);
        if (fid == null)
            return null;

        return (T) fieldMap.get(fid);
    }

    public <T extends IEntity> void addField(T field) {
        fieldMap.put(field.getFieldID(), field);
        header.setFieldCount(fieldMap.size());
    }

    /**
     * 写入到Buf中
     * 
     * @param buf
     */
    public void writeToBuf(ByteBuf buf) {
        //ByteBuf buf = Unpooled.buffer(1024);
        header.writeToBuf(buf);

        for (int fieldID : fieldMap.keySet()) {
            buf.writeInt(StringEncodeHelper.ctoji(fieldID));
            byte[] content = fieldMap.get(fieldID).toByteArray();

            buf.writeInt(StringEncodeHelper.ctoji(content.length));
            buf.writeBytes(content);
        }
    }

    private HashMap<Integer, IEntity> fieldMap;
    private CBizPackageHeader         header;
}
