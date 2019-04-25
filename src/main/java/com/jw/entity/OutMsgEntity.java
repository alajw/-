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
public class OutMsgEntity {
    private String  ToUserName;   //用户的openID
    private String  FromUserName;   //测试号的微信号
    private Long    CreateTime;   //消息创建时间 （整型）
    private String  MsgType	;   //消息类型，文本为text/image
    private String  Content;   //文本消息内容
    private Long    MediaId	;   //图片消息媒体id，可以调用获取临时素材接口拉取数据



}

