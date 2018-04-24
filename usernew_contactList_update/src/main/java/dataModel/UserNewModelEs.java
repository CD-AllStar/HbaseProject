package dataModel;

import Util.ConstantUtil;
import Util.Util;

import java.util.*;

public class UserNewModelEs{
    private String address;
    private String city;
    private List<String> phone;
    private String contact_name;
    private String province;
    private String country;
    private List<String> email;
    private String groups;
    private String last_message;
    private String last_message_id;
    private Long last_message_time;
    private String last_name;
    private String uid;
    private String emails;
    private Integer contact_count;
    private Integer be_contact_count;
    private Integer msg_count;
    private List<Map> platforms;
    private List<Map> names;
    private List<String> word_list;
    private List<Map<String, Object>> chat_hotwords;
    private Boolean gender;
    private Long ana;
    private Map<String, Object> join_relationship;
    private Map<String, Object> other;
    private String org_hotwords;
    private List<Long> birthday;
    private List<Map> card;
    private String profession;
    private Long  user_update_time;
    private Long msg_update_time;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String values) {
        List<String>  lists=new LinkedList<String>();
        String[] phones = values.split("\u0002");
        if (phones.length > 0) {
            lists.addAll(Arrays.asList(phones));
        }
        this.phone = lists;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        String [] emails=email.split("\u0002");
        List<String> emailList=new LinkedList<String>();
        if(emails.length>0){
            emailList.addAll(Arrays.asList(emails));
        }
        this.email = emailList;
    }

    public void setGroup(String group) {
        this.groups = group;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public void setLast_message_id(String last_message_id) {
        this.last_message_id = last_message_id;
    }

    public void setLast_message_time(String values) {
        this.last_message_time = Util.toLong(values);
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public void setContact_count(String values) {
        this.contact_count = Integer.valueOf(values);
    }

    public void setBe_contact_count(String values) {
        this.be_contact_count = Integer.valueOf(values);
    }

    public void setMsg_count(String values) {
        this.msg_count = Integer.valueOf(values);
    }

    public void setOrg_hotwords(String org_hotwords) {
        this.org_hotwords = org_hotwords;
    }

    public void setPlatforms(String values) {
        List<Map> platformList = new LinkedList<Map>();
        String[] platformArray = values.split("\u0002");
        if (platformArray.length > 0) {
            for (String platformInfo : platformArray) {
                Map<String, Object> platformMap = new HashMap<String, Object>();
                String[] platformInfos = platformInfo.split("\\|");
                for (String str : platformInfos) {
                    String[] strings = str.split("=");
                    if (strings.length == 1) {
                        if (strings[0].equals(ConstantUtil.platformFields.last_login)) {
                            platformMap.put(ConstantUtil.platformFields.last_login, null);
                        } else if (strings[0].equals(ConstantUtil.platformFields.platform)) {
                            platformMap.put(ConstantUtil.platformFields.platform, null);
                        } else if (strings[0].equals(ConstantUtil.platformFields.uid)) {
                            platformMap.put(ConstantUtil.platformFields.uid, null);
                        } else if (strings[0].equals(ConstantUtil.platformFields.reg_time)) {
                            platformMap.put(ConstantUtil.platformFields.reg_time, null);
                        }
                    } else if (strings.length > 1) {
                        if (strings[0].equals(ConstantUtil.platformFields.last_login)) {
                            if (strings[1].matches("^[0-9]*$")) {
                                platformMap.put(ConstantUtil.platformFields.last_login, Util.toLong(strings[1]));
                            } else if (strings[1].contains("/")) {
                                platformMap.put(ConstantUtil.platformFields.last_login, Util.dateTransform(strings[1]));
                            }
                        } else if (strings[0].equals(ConstantUtil.platformFields.platform)) {
                            platformMap.put(ConstantUtil.platformFields.platform, strings[1]);
                        } else if (strings[0].equals(ConstantUtil.platformFields.uid)) {
                            platformMap.put(ConstantUtil.platformFields.uid, strings[1]);
                        } else if (strings[0].equals(ConstantUtil.platformFields.reg_time)) {
                            if (strings[1].matches("^[0-9]*$")) {
                                platformMap.put(ConstantUtil.platformFields.reg_time, Util.toLong(strings[1]));
                            } else if (strings[1].contains("/")) {
                                platformMap.put(ConstantUtil.platformFields.reg_time, Util.dateTransform(strings[1]));

                            }
                        }
                    }

                }
                platformList.add(platformMap);
            }
        }
        this.platforms = platformList;
    }

    public void setNames(String values) {
        List<Map> nameList = new LinkedList<Map>();
        String[] nameArray = values.split("\u0002");
        if (nameArray.length>0) {
            for (String nameInfo : nameArray) {
                Map<String, Object> nameMaps = new HashMap<String, Object>();
                String[] nameInfos = nameInfo.split("\t");
                if (nameInfos.length == 1) {
                    nameMaps.put("name", nameInfos[0]);
                } else if (nameInfos.length == 2) {
                    nameMaps.put("name", nameInfos[0]);
                    nameMaps.put("source_id", nameInfos[1]);
                } else if (nameInfos.length >= 3) {
                    nameMaps.put("name", nameInfos[0]);
                    nameMaps.put("source_id", nameInfos[1]);
                    nameMaps.put("source_name", nameInfos[2]);
                }
                nameList.add(nameMaps);
            }
        }
        this.names = nameList;
    }

    public void setWord_list(String values) {
        List<String> wordList = new LinkedList<String>();
        String[] words = values.split("\u0002");
        if (words.length > 0) {
            wordList.addAll(Arrays.asList(words));
        }
        this.word_list = wordList;
    }

    public void setChat_hotwords(String values) {
        List<Map<String, Object>> msg_list = new LinkedList<Map<String, Object>>();
        String[] msgs = values.split("\u0002");
        for (String msg : msgs) {
            Map<String, Object> msgmap = new HashMap<String, Object>();
            String[] msginfo = msg.split("\t");
            if (msginfo.length >= 2) {
                msgmap.put(ConstantUtil.chat_hotwordsFields.word, msginfo[0]);
                msgmap.put(ConstantUtil.chat_hotwordsFields.msgid, msginfo[1]);
                msgmap.put(ConstantUtil.chat_hotwordsFields.time, Long.valueOf(msginfo[2]));
            }
            msg_list.add(msgmap);
        }
        this.chat_hotwords = msg_list;
    }

    public void setGender(String values) {
        if (values.equals(ConstantUtil.genderFields.man)) {
            this.gender = true;
        } else if (values.equals(ConstantUtil.genderFields.woman)) {
            this.gender = false;
        }
    }

    public void setAna(String values) {
        this.ana = Util.toLong(values);
    }

    public void setJoin_relationship() {
        Map<String, Object> contact_map = new HashMap<String, Object>();
        contact_map.put("name", "user");
        this.join_relationship = contact_map;
    }

    public void setOther(String col, String values) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(col, values);
        this.other = map;
    }

    public void setBirthday(String birthday) {
        List<Long> birthList = new LinkedList<Long>();
        if (!(null == birthday || birthday.equals("null"))) {
            String[] births = birthday.split("\u0002");
            for (String birth : births) {
                birthList.add(Util.toLong(birth));
            }
            this.birthday = birthList;
        }
    }

    public void setCard(String card) {
        List<Map> cardList = new LinkedList<Map>();
        if (card != null) {
            String[] cards = card.split("\u0002");
            for (String str : cards) {
                Map<String, Object> map = new HashMap<String, Object>();
                String[] cardInfos = str.split("\\|");
                for (String cardInfo : cardInfos) {
                    String[] cardArray = cardInfo.split("=");
                    if (cardArray.length > 1) {
                        if (cardArray[0].equals(ConstantUtil.cardFields.card_type)) {
                            map.put("card_type", cardArray[1]);
                        } else if (cardArray[0].equals(ConstantUtil.cardFields.card_id)) {
                            map.put("card_id", cardArray[1]);
                        } else if (cardArray[0].equals(ConstantUtil.cardFields.issue_date)) {
                            map.put("issue_date", Util.toLong(cardArray[1]));
                        } else if (cardArray[0].equals(ConstantUtil.cardFields.issue_org)) {
                            map.put("issue_org", cardArray[1]);
                        } else if (cardArray[0].equals(ConstantUtil.cardFields.issue_place)) {
                            map.put("issue_place", cardArray[1]);
                        }
                    }
                }
                cardList.add(map);
            }
        }
        this.card = cardList;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setUser_update_time(String user_update_time) {
        this.user_update_time = Util.toLong(user_update_time);
    }

    public void setMsg_update_time(String msg_update_time) {
        this.msg_update_time = Util.toLong(msg_update_time);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!(address == null || address.equals("null"))) {
            map.put("address", address);
        }
        if (!(city == null || city.equals("null"))) {
            map.put("city", city);
        }
        if (!(phone == null )) {
            map.put("phone", phone);
        }
        if (!(contact_name == null || contact_name.equals("null"))) {
            map.put("contact_name", contact_name);
        } else if (names != null) {
            map.put("contact_name", names.get(0).get("name"));
        }
        if (!(province == null || province.equals("null"))) {
            map.put("province", province);
        }
        if (!(country == null || country.equals("null"))) {
            map.put("country", country);
        }
        if (!(email == null)) {
            map.put("email", email);
        }
        if (!(groups == null || groups.equals("null"))) {
            map.put("groups", groups);
        }
        if (!(last_message == null || last_message.equals("null"))) {
            map.put("last_message", last_message);
        }
        if (!(last_message_id == null || last_message_id.equals("null"))) {
            map.put("last_message_id", last_message_id);
        }
        if (!(last_message_time == null)) {
            map.put("last_message_time", last_message_time);
        }
        if (!(last_name == null || last_name.equals("null"))) {
            map.put("last_name", last_name);
        }
        if (!(uid == null || uid.equals("null"))) {
            map.put("uid", uid);
        }
        if (!(emails == null || emails.equals("null"))) {
            map.put("emails", emails);
        }
        if (contact_count != null) {
            map.put("contact_count", contact_count);
        }
        if (be_contact_count != null) {
            map.put("be_contact_count", be_contact_count);
        }
        if (msg_count != null) {
            map.put("msg_count", msg_count);
        }
        if (platforms != null) {
            map.put("platforms", platforms);
        }
        if (names != null) {
            map.put("names", names);
        }
        if (word_list != null) {
            map.put("word_list", word_list);
        }
        if (chat_hotwords != null) {
            map.put("chat_hotwords", chat_hotwords);
        }
        if (gender != null) {
            map.put("gender", gender);
        }
        if (ana != null) {
            map.put("ana", ana);
        }
        if (!(org_hotwords == null || org_hotwords.equals("null"))) {
            map.put("org_hotwords", org_hotwords);
        }
        if (!(birthday == null)) {
            map.put("birthday", birthday);
        }
        if (!(profession == null || profession.equals("null"))) {
            map.put("profession", profession);
        }
        if (card != null) {
            map.put("card", card);
        }
        if(msg_update_time!=null){
            map.put("msg_update_time",msg_update_time);
        }
        if(user_update_time!=null){
            map.put("user_update_time",user_update_time);
        }
        map.put("join-relationship", join_relationship);
        return map;
    }
}
