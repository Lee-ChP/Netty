package protocol;

import protocol.impl.JSONSerializer;

public interface Serializer {
    /**
     * JSON序列化
     */
    byte JSON_SERIALIZE = 1;

    /**
     * 默认序列化方式
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法标识
     */
    byte getSerializeAlgorithm();

    /**
     * 序列化
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     */
    <T> T deSerialize(Class<T> clazz,byte[] bytes);
}
