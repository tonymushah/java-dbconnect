package mg.tonymushah.utils.file_t_class;

import java.io.*;
import java.lang.reflect.*;

import mg.tonymushah.utils.*;

public class File_Class<T extends Object> {
    /*
        TODO File to object splitting sequence
        1 - split with "###"

        2 - take the split("###")[res.length - 1] -> for the recent
        
        3 - split with "//"
            
            Now we have : 
            rsplit[0] = the object type name
            replit[1] = the object field value
                
                Note : rsplit is the result of the split with "//"

        4 - save rsplit[0]
    
        5 - split rsplit[1] with ";;"
            
            Now we have all the field
        
        6 - split these "fields" with "=="

            Now we have the field name and type -> [0] and the value -> [1]

        7 - split these "the field name and type -> [0]" with "::" 

            Now we have the field name -> [0] and the field type -> [1]

    */
    File to_use;
    TCeutils<T> utilis;
    T to_create;
    public void setTo_use(File to_use) {
        this.to_use = to_use;
    }
    public void setUtilis(TCeutils<T> utilis) {
        this.utilis = utilis;
    }
    public T getTo_create() {
        return to_create;
    }
    public File getTo_use() {
        return to_use;
    }
    public TCeutils<T> getUtilis() {
        return utilis;
    }
    public String getClassName(){
        String input = TUtils.getLine(to_use);
        String[] to_u = input.split("###");
        //System.out.println(to_u.length);

        /*for (int index = 0; index < to_u.length; index++) {
            System.out.println(to_u[index]);
        }*/
        //System.out.println(Tutils.getLine(to_use));

        String strClass = to_u[to_u.length - 1];

        //System.out.println(strClass);

        return strClass.split("//")[0];
    }
    public String getClassField(){
        String input = TUtils.getLine(to_use);
        String[] to_u = input.split("###");
        //System.out.println(to_u.length);

        /*for (int index = 0; index < to_u.length; index++) {
            System.out.println(to_u[index]);
        }*/
        //System.out.println(Tutils.getLine(to_use));

        String strClass = to_u[to_u.length - 1];

        //System.out.println(strClass);

        return strClass.split("//")[1];
    }
    public Class<?> get_FCClass(){
        Class<?> returns = Object.class;
        try {
            returns = Class.forName(this.getClassName());
        } catch (Exception e) {
            //TODO: handle exception
            //e.printStackTrace();
        }
        return returns;
    }
    public Constructor<?> getFCConstructor(){
        Constructor<?> returns = null;
        try {
            returns = this.get_FCClass().getConstructor(null);
        } catch (Exception e) {
            //TODO: handle exception
            //e.printStackTrace();
        }
        return returns;
    }
    public T[] build_array_field(String path){
        ArFile_Class<T> array = new ArFile_Class<T>(new File(path));
        return array.getTo_create();
    }
    public void build_to_create(){
        try {
            this.to_create = (T) this.getFCConstructor().newInstance(null);
        } catch (Exception e) {
            //TODO: handle exception
            //e.printStackTrace();
        }
    }
    public void build_utilis(){
        this.build_to_create();
        this.setUtilis(new TCeutils<T>(to_create));
    }
    public void insert_data(){
        String[] fields = this.getClassField().split(";;");

        // NOTE this is the system out debug
        //System.out.println();
        //System.out.println("Fields splits");
        for (String string : fields) {
            System.out.println(string);
        }
        //System.out.println();
        Field[] Fields = utilis.getFields();
        for (Field field : Fields) {
            System.out.println(field.getName());
        }
        //System.out.println();
        for (String string : fields) {
            if(string.split("==")[1].indexOf(".Tclass") != -1){
                this.utilis.setInField(string.split("==")[0].split("::")[0], this.build_array_field(string.split("==")[1]));
            }else{
                this.utilis.setInField(string.split("==")[0].split("::")[0], string.split("==")[1]);
            }
            //System.out.println(this.utilis.getInField(string.split("==")[0].split("::")[0]));
        }

    }
    public File_Class(File to_use){
        this.setTo_use(to_use);
        this.build_utilis();
        this.insert_data();
        //System.out.println(this.to_create.getClass().getName());
    }
}
