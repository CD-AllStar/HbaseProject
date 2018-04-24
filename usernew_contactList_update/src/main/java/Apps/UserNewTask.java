package Apps;

import Client.EsClient;
import dataModel.UserNewHbase;
import dataModel.UserNewModelEs;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.elasticsearch.action.update.UpdateRequest;

public class UserNewTask {
    private static String row = null;
    private static String index = Conf.getInstance().getIndex();
    private static String type = Conf.getInstance().getType();

    public static void excuteTask(Result[] results) {
        for (Result result : results) {
            UserNewModelEs userNew = new UserNewModelEs();
            row = Bytes.toString(result.getRow());
            if (!(row == null || row.equals("null"))) {
                for (Cell cell : result.rawCells()) {
                    String colunm = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String values = Bytes.toString(CellUtil.cloneValue(cell));
                    if (!(values.equals("null") || values.equals(""))) {
                        if (colunm.equals(UserNewHbase.address.name())) {
                            userNew.setAddress(values);
                        } else if (colunm.equals(UserNewHbase.city.name())) {
                            userNew.setCity(values);
                        } else if (colunm.equals(UserNewHbase.contact_name.name())) {
                            userNew.setContact_name(values);
                        } else if (colunm.equals(UserNewHbase.country.name())) {
                            userNew.setCountry(values);
                        } else if (colunm.equals(UserNewHbase.email.name())) {
                            userNew.setEmail(values);
                        } else if (colunm.equals(UserNewHbase.group.name())) {
                            userNew.setGroup(values);
                        } else if (colunm.equals(UserNewHbase.last_message.name())) {
                            userNew.setLast_message(values);
                        } else if (colunm.equals(UserNewHbase.last_message_id.name())) {
                            userNew.setLast_message_id(values);
                        } else if (colunm.equals(UserNewHbase.last_name.name())) {
                            userNew.setLast_name(values);
                        } else if (colunm.equals(UserNewHbase.province.name())) {
                            userNew.setProvince(values);
                        } else if (colunm.equals(UserNewHbase.uid.name())) {
                            userNew.setUid(values);
                        } else if (colunm.equals(UserNewHbase.emails.name())) {
                            userNew.setEmails(values);
                        } else if (colunm.equals(UserNewHbase.contact_count.name())) {
                            userNew.setContact_count(values);
                        } else if (colunm.equals(UserNewHbase.platform_new.name())) {
                            userNew.setPlatforms(values);
                        } else if (colunm.equals(UserNewHbase.names.name())) {
                            userNew.setNames(values);
                        } else if (colunm.equals(UserNewHbase.message_count.name())) {
                            userNew.setMsg_count(values);
                        } else if (colunm.equals(UserNewHbase.gender.name())) {
                            userNew.setGender(values);
                        } else if (colunm.equals(UserNewHbase.last_message_time.name())) {
                            userNew.setLast_message_time(values);
                        } else if (colunm.equals(UserNewHbase.word_list.name())) {
                            userNew.setWord_list(values);
                        } else if (colunm.equals(UserNewHbase.ana.name())) {
                            userNew.setAna(values);
                        } else if (colunm.equals(UserNewHbase.phone.name())) {
                            userNew.setPhone(values);
                        } else if (colunm.equals(UserNewHbase.msg_words.name())) {
                            userNew.setChat_hotwords(values);
                        } else if (colunm.equals(UserNewHbase.be_contact_count.name())) {
                            userNew.setBe_contact_count(values);
                        } else if (colunm.equals(UserNewHbase.org_words.name())) {
                            userNew.setOrg_hotwords(values);
                        } else if (colunm.equals(UserNewHbase.birth.name())) {
                            userNew.setBirthday(values);
                        } else if (colunm.equals(UserNewHbase.profession.name())) {
                            userNew.setProfession(values);
                        } else if (colunm.equals(UserNewHbase.card.name())) {
                            userNew.setCard(values);
                        } else if (colunm.equals(UserNewHbase.msg_updatetime.name())) {
                            userNew.setMsg_update_time(values);
                        } else if (colunm.equals(UserNewHbase.contact_updatetime.name())) {
                            userNew.setUser_update_time(values);
                        } else {
                            userNew.setOther(colunm, values);
                        }
                    }
                }
                userNew.setJoin_relationship();
                EsClient.bulkProcessor.add(new UpdateRequest(index, type, row).doc(userNew.toMap()).upsert(userNew.toMap()));
            }
        }
    }
}



