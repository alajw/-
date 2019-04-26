package com.jw.entity;



import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author 姜雯
 * @date 2019/4/21--0:02
 */
@Data
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class InMsgEntity {
    private String   ToUserName;   //开发者微信号
    private String   FromUserName;   //发送方帐号（一个OpenID）
    private Long     CreateTime;   //消息创建时间 （整型）
    private String   MsgType	;   //消息类型，文本为text/image
    private String   Content;   //文本消息内容
    private String   Event;    //触发事件的类型，subscribe(订阅)、unsubscribe(取消订阅)
    private Long     MsgId;   //消息id，64位整型
    private String   PicUrl;   //	图片链接（由系统生成）
    private String     MediaId	;   //图片消息媒体id，可以调用获取临时素材接口拉取数据









}

