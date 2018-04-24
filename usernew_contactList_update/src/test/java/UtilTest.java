import Apps.Conf;
import Util.Util;

import java.util.List;

public class UtilTest {
    public static void main(String [] args){
      List<String>  list=Util.getFileList(Conf.getInstance().getPaths(),Conf.getInstance().getTmpPath());
        for(String str:list){
            System.out.println(str);
        }
    }
}
