package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.service.Property;
import lombok.Getter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;

/**
 * @author sucl
 * @date 2019/4/22
 */
@Getter
public class SerializedLambda implements Serializable {

    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;

    /**
     * 通过反序列化转换 class
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     */
    public static SerializedLambda convert(Property lambda) {
        byte[] bytes = SerializationUtils.serialize(lambda);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes)) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {

        }
        throw new RuntimeException("lambda convert error");
    }

    @Override
    public String toString() {
        return super.toString() +  implClass.replace("/", ".") + "#" + implMethodName;
    }
}
