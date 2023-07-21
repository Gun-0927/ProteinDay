package com.blue.bluearchive.constant;

public enum MemberStat {
    //    MEMBER,NOMEMBER,OUTMEMBER;
//    public static MemberStat fromString(String value) {
//        for (MemberStat stat : MemberStat.values()) {
//            if (stat.name().equalsIgnoreCase(value)) {
//                return stat;
//            }
//        }
//        throw new IllegalArgumentException("Invalid MemberStat value: " + value);
//    }
    MEMBER("활동"), NOMEMBER("정지"), OUTMEMBER("영구정지");

    private final String koreanName;

    MemberStat(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static MemberStat fromString(String value) {
        for (MemberStat stat : MemberStat.values()) {
            if (stat.name().equalsIgnoreCase(value)) {
                return stat;
            }
        }
        throw new IllegalArgumentException("Invalid MemberStat value: " + value);
    }
}