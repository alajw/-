package com.jw.controller;

import com.jw.entity.InMsgEntity;
import com.jw.entity.OutMsgEntity;
import com.jw.util.sha1Util;
import com.jw.util.wechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @author 姜雯
 * @date 2019/1/2--19:19
 * 微信公众号URL接入校验
 */
@RestController
public class WController {

      @GetMapping("/mactlo")
    public String getAppurl(String signature,String  timestamp,String nonce,String echostr){
          System.out.println("连接测试，拿到数据为："+signature);
         // 1.将token、timestamp、nonce三个参数进行字典序排序
          String [] arr={wechatUtil.TOKEN,timestamp,nonce};
          Arrays.sort(arr);
          // 2.将三个参数字符串拼接成一个字符串进行sha1加密
          StringBuilder sb=new StringBuilder();
          for (String temp:arr) {
              sb.append(temp);
          }
          String  mySignature =sha1Util.getSha1(sb.toString());
          System.out.println("sha加密："+mySignature);
          // 3.开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
          if (mySignature.equals(signature)){
              System.out.println(mySignature+":"+signature);

              System.out.println("接入成功");
              return  echostr;
          }
          //请远洋返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
          System.out.println("接入失败");
          return  null;
      }

    /**
     * 消息处理
     * @return
     */
    @PostMapping("/mactlo")
    public   Object hahdleMessage(@RequestBody InMsgEntity inMsgEntity) {
        System.out.println("进入消息处理");
        System.out.println(inMsgEntity);
        String toUserName = inMsgEntity.getToUserName();// 公众号原始ID
        String openid = inMsgEntity.getFromUserName();// 发送方帐号openId
        StringBuffer str = new StringBuffer();
        switch (inMsgEntity.getMsgType()){
            case "text":
                String content=inMsgEntity.getContent();
                if (content.contains("你") & content.contains("是")){
                    content="我是你的口袋程序助手";
                }else if(content.contains("爱")){
                    content="口袋程序助手一直在你身边";
                }else if(content.contains("时间")){
                    content=new Date().toString();
                }
                str.append("<xml>");
                str.append("<ToUserName><![CDATA[" + openid + "]]></ToUserName>");
                str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
                str.append("<CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>");
                str.append("<MsgType><![CDATA[" + "text" + "]]></MsgType>");
                str.append("<Content><![CDATA["+ content+ "]]></Content>");
                str.append("</xml>");
                break;
            case "image":
                str.append("<xml>");
                str.append("<ToUserName><![CDATA[" + openid + "]]></ToUserName>");
                str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
                str.append("<CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>");
                str.append("<MsgType><![CDATA[" + "image" + "]]></MsgType>");
                str.append("<Image><MediaId><![CDATA["+inMsgEntity.getMediaId()+ "]]></MediaId></Image>");
                str.append("</xml>");
                break;
            case "event":
                if (inMsgEntity.getEvent().equals("subscribe")) {
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + openid + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
                    str.append("<CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>");
                    str.append("<MsgType><![CDATA[" + "event" + "]]></MsgType>");
                    str.append("<Event> <![CDATA[" + inMsgEntity.getEvent() + "]]> </Image>");
                    str.append("</xml>");
                }
                break;
        }
        System.out.println(str.toString());
        return str.toString();
    }

    /**
     * 测试
     * @return
     */
    @GetMapping("/xmlTest")
    public Object getOutMsgEntity() {
        OutMsgEntity outMsgEntity=new OutMsgEntity();
        //发送方
        outMsgEntity.setFromUserName("12");
        //接受方
        outMsgEntity.setToUserName("12");
        //消息创建时间
        outMsgEntity.setCreateTime(new Date().getTime());
        //消息类型
        outMsgEntity.setMsgType("text");
        //消息内容
        outMsgEntity.setContent("12");
        return  outMsgEntity;
    }
}
