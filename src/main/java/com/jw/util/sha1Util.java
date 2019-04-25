package com.jw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 姜雯
 * @date 2019/4/20--23:34
 */
public class sha1Util {

    /**
     * sha1加密
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sha1(String data) throws NoSuchAlgorithmException {
        //加盐   更安全一些
        data += "lyz";
        //信息摘要器                                算法名称
        MessageDigest md = MessageDigest.getInstance("SHA1");
        //把字符串转为字节数组
        byte[] b = data.getBytes();
        //使用指定的字节来更新我们的摘要
        md.update(b);
        //获取密文  （完成摘要计算）
        byte[] b2 = md.digest();
        //获取计算的长度
        int len = b2.length;
        //16进制字符串
        String str = "0123456789abcdef";
        //把字符串转为字符串数组
        char[] ch = str.toCharArray();

        //创建一个40位长度的字节数组
        char[] chs = new char[len*2];
        //循环20次
        for(int i=0,k=0;i<len;i++) {
            byte b3 = b2[i];//获取摘要计算后的字节数组中的每个字节
            // >>>:无符号右移
            // &:按位与
            //0xf:0-15的数字
            chs[k++] = ch[b3 >>> 4 & 0xf];
            chs[k++] = ch[b3 & 0xf];
        }

        //字符数组转为字符串
        return new String(chs);
    }
    public static String getSha1(String str){
          if(str==null || str.length()==0){
          return null;
        }
       char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
       try {
             MessageDigest mdTemp=MessageDigest.getInstance("SHA1");
             mdTemp.update(str.getBytes("UTF-8"));
             byte[] md=mdTemp.digest();
             int j=md.length;
             char buf[]=new char[j*2];
             int k=0;
             for(int i=0;i<j;i++){
                    byte byte0=md[i];
                    buf[k++]=hexDigits[byte0>>>4 & 0xf];
                   buf[k++]=hexDigits[byte0 & 0xf];
              }
           return new String(buf);
         } catch (Exception e) {
           return null;
      }
    }


}
