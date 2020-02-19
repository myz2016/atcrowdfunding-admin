package com.mfh.crowd.funding.util;

import com.mfh.crowd.funding.constants.CrowdFundingConstant;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

/**
 * @author mfh
 * @date 2019/12/15 18:10
 */
public final class CrowdFundingUtils {

    private CrowdFundingUtils(){}
    /**
     * 检查是否为异步请求
     * @param request
     * @return 异步请求：true，同步请求：false
     */
    public static boolean checkAsyncRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWithHeader = request.getHeader("X-Requested-With");
        return (stringEffective(acceptHeader) && acceptHeader.contains("application/json")) || (stringEffective(xRequestedWithHeader) && xRequestedWithHeader.contains("XMLHttpRequest"));
    }
    /**
     * 判断 Map 是否有效
     * @param map   待验证的 Map
     * @return  true 表示有效，false 表示无效
     */
    public static <K, V> boolean mapEffective(Map<K, V> map) {
        return null != map && map.size() > 0;
    }

    /**
     * 判断 collection 是否有效
     * @param collection 待验证的集合
     * @return  true 表示有效，false 表示无效
     */
    public static <E> boolean collectionEffective(Collection<E> collection) {
        return null != collection && collection.size() > 0;
    }

    /**
     * 判断 source 是否有效
     * @param source 明文密码
     * @return  true 表示有效，false 表示无效
     */
    public static boolean stringEffective(String source) {
        return null != source && source.length() > 0;
    }

    /**
     * md5加密
     *
     * @param source 被加密的明文字符串
     * @return 加密后的字符串
     */
    public static String md5(String source) {
        if (!stringEffective(source)) {
            throw new RuntimeException(CrowdFundingConstant.MESSAGE_CODE_INVALID);
        }
        final StringBuilder sb = new StringBuilder();
        // 指定加密算法
        String algorithm = "MD5";
        // 准备字符数组
        char[] character = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            // 执行加密操作的核心对象
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            // 将要加密的明文转成字节数组形式
            final byte[] inputBytes = source.getBytes();
            // 执行加密
            final byte[] outputBytes = digest.digest(inputBytes);
            // 遍历outputBytes
            for (byte outputByte : outputBytes) {
                // 获取低四位值
                int lowValue = outputByte & 15;
                // 右移四位和15做与运算得到高四位值
                int highValue = (outputByte >> 4) & 15;
                // 以高四位、低四位的值为下标从字符数组中获取对应字符
                sb.append(character[highValue]).append(character[lowValue]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.valueOf(sb);
    }

}
