package com.jw.call;

public enum REStateEnum {

    NOT_FOLLOW("not_follow", "未关注"),
    FOLLOW("follow","已关注"),
    SEND("send", "已发放待领取"),
    RECEIVED("RECEIVED", "已领取"),
    RFUND_ING("RFUND_ING","退款中 "),
    REFUND("REFUND","已退款"),
    FAILED("FAILED","发送失败");
    private String code;
    private String desc;

    REStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        System.out.println(REStateEnum.SEND.getDesc());
        for (REStateEnum e:
             REStateEnum.values()) {
            System.out.println(e.toString());
        }
    }
}
