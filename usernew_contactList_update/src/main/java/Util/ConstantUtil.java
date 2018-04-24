package Util;

public interface ConstantUtil {
    //hbase 表性别字符
    public static class genderFields{
    public static final String  man="true";
    public static final String woman= "false";
    }
    //es-hbase字段对照
    public static class cardFields{
        public static final String card_id="sno";
        public static final String card_type="type";
        public static final String issue_date="issue_date";
        public static final String issue_org="issue_auth";
        public static final String issue_place="issue_place";
    }
    //es-hbase 字段对照platform field
    public static class platformFields{
        public static final String last_login="last_login";
        public static final String platform="platform";
        public static final String uid="uid";
        public static final String reg_time="reg_time";

    }
    //es-hbase 字段
    public static class chat_hotwordsFields{
        public static final String word="word";
        public static final String msgid="msgid";
        public static final String time="time";
    }
}
