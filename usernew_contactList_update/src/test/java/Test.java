public class Test {
    public static void main(String [] args){
        String str="WARN: The method class org.apache.commons.logging.impl.SLF4JLogFactory#release() was invoked.";
        if(!(str.contains("-")||str.length()==32)){
            System.out.println(str);
        }
    }
}
