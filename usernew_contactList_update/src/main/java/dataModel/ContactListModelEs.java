package dataModel;

import java.util.*;

public class ContactListModelEs {
    private List<String> email;
    private List<Map> platforms;
    private List<String> phone;
    private String country;
    private String province;
    private String city;
    private List<Map<String, Object>> be_names;
    private String contact_id;
    private Map join_relationship;
    private List<Map> names;
    private String contact_name;
    private Integer score;
    public ContactListModelEs() {
    }
    public void setEmail(String email) {
        String [] emails=email.split("\u0002");
        List<String> emailList=new LinkedList<String>();
        if(emails.length>0){
            emailList.addAll(Arrays.asList(emails));
        }
        this.email = emailList;
    }

    public void setPlatforms(String platforms) {
        List<Map> platformList = new LinkedList<Map>();
        String[] platformArray = platforms.split("\u0002");
        if (platformArray.length>0) {
            for (String platformInfo : platformArray) {
                Map<String, Object> map = new HashMap<String, Object>();
                String[] platformInfos = platformInfo.split("\t");
                for (String platform : platformInfos) {
                    map.put("platform", platform);
                }
                platformList.add(map);
            }
        }
        this.platforms = platformList;
    }

    public void setPhone(String values) {
        String[] phones = values.split("\u0002");
        List<String>  lists=new LinkedList<String>();
        if (phones.length > 0) {
            for (String phone:phones) {
                lists.add(phone);
            }
        }
        this.phone = lists;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBe_names(List<Map<String, Object>> be_names) {
        this.be_names = be_names;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public void setJoin_relationship(String rowkey) {
        Map<String, Object> contact_map = new HashMap<String, Object>();
        contact_map.put("name", "contact");
        contact_map.put("parent", rowkey);
        this.join_relationship = contact_map;
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

    public void setContact_name(String values) {
        String contact_name = null;
        String[] contactNames = values.split("\u0002");
        if (contactNames.length>0) {
            for (String name : contactNames) {
                String[] nameInfos = name.split("\t");
                if (nameInfos.length >= 1 && !nameInfos[0].equals("") && !nameInfos[0].equals("null")) {
                    contact_name = nameInfos[0];
                }
            }
        }
        this.contact_name = contact_name;
    }
    public void setScore(String  score) {
        this.score = Integer.valueOf(score);
    }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        if(!(email==null)){
            map.put("email", email);
        }
        if(!(platforms==null)){
            map.put("platforms", platforms);
        }
        if(!(phone==null)){
            map.put("phone", phone);
        }
        if(!(country==null||country.equals("null"))){
            map.put("country", country);
        }
        if(!(province==null||province.equals("null"))){
            map.put("province", province);
        }
        if(!(city==null||city.equals("null"))){
            map.put("city", city);
        }
        if(!(be_names==null)){
            map.put("be_names", be_names);
        }
        if(!(contact_id==null||contact_id.equals("null"))){
            map.put("contact_id", contact_id);
        }
        if(!(names==null)){
            map.put("names", names);
        }
        if(!(contact_name==null||contact_name.equals("null"))){
            map.put("contact_name", contact_name);
        }
        if(score!=null){
            map.put("score",score);
        }
        map.put("join-relationship", join_relationship);
        return map;
    }
}
