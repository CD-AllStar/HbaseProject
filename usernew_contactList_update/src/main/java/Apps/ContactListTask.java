package Apps;

import Client.EsClient;
import Util.Util;
import dataModel.ContactListHbase;
import dataModel.ContactListModelEs;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.elasticsearch.action.update.UpdateRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ContactListTask {
    private static String index = Conf.getInstance().getIndex();
    private static String type = Conf.getInstance().getType();

    public static void excuteTask(Result[] results) {
        for (Result result : results) {
            String row =null ;
            String becontact = null;
            String rowkeys = Bytes.toString(result.getRow());
            ContactListModelEs contactList = new ContactListModelEs();
            List<Map<String, Object>> be_names = new LinkedList<Map<String, Object>>();
            if (!(rowkeys == null || rowkeys.equals("null"))) {
                for (Cell cell : result.rawCells()) {
                    String colunm = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String values = Bytes.toString(CellUtil.cloneValue(cell));
                    row = rowkeys.split("-")[0];
                    becontact = rowkeys.split("-")[1];
                    contactList.setJoin_relationship(row);
                    contactList.setContact_id(becontact);
                    if (!(values.equals("null") || values.equals("") || values.equals("\\N"))) {
                        if (colunm.equals(ContactListHbase.MAIL.name())) {
                            contactList.setEmail(values);
                        } else if (colunm.equals(ContactListHbase.PLATFORM.name())) {
                            contactList.setPlatforms(values);
                        } else if (colunm.equals(ContactListHbase.NAMES.name())) {
                            contactList.setNames(values);
                            contactList.setContact_name(values);
                        } else if (colunm.equals(ContactListHbase.PHONE.name())) {
                            contactList.setPhone(values);
                        } else if (colunm.equals(ContactListHbase.INFO.name())) {
                            String[] infos = values.split("-");
                            if (infos.length > 0) {
                                try {
                                    contactList.setCountry(infos[0]);
                                    contactList.setProvince(infos[1]);
                                    contactList.setCity(infos[2]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println(Util.timeParse(System.currentTimeMillis()) + ",Exception hbase INFO:" + row + "-" + becontact);
                                }
                            }
                        } else if (colunm.equals(ContactListHbase.BEETALK.name()) || colunm.equals(ContactListHbase.TANGO2.name()) || colunm.equals(ContactListHbase.OOVOO.name())
                                || colunm.equals(ContactListHbase.NIMBUZZ.name()) || colunm.equals(ContactListHbase.VIBER.name()) || colunm.equals(ContactListHbase.HIKE.name())) {
                            try {
                                String[] nameTimes = values.split("\u0002");
                                for (String nameTime : nameTimes) {
                                    String[] names = nameTime.split("\t");
                                    if (names.length > 1) {
                                        String temp = names[1];
                                        if (!temp.equals("Unknown")) {
                                            Map<String, Object> nameMap = new HashMap<String, Object>();
                                            nameMap.put("platform", colunm);
                                            nameMap.put("name", temp);
                                            be_names.add(nameMap);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("Exception hbase platform:" + row + "-" + becontact);
                            }
                        } else if (colunm.equals(ContactListHbase.SCORE.name())) {
                            contactList.setScore(values);
                        }
                    }
                }
                contactList.setBe_names(be_names);
                EsClient.bulkProcessor.add(new UpdateRequest(index, type, row + becontact).doc(contactList.toMap()).upsert(contactList.toMap()).routing(row));
                if (EsClient.bulkProcessor == null || EsClient.client == null) {
                    EsClient.getInstance();
                }
            }
        }
    }
}
