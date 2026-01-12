package mg.tonymushah.utils.file_t_class;

import java.io.File;
import java.lang.reflect.Array;

import mg.tonymushah.utils.TUtils;


public class ArFile_Class<T extends Object> {
    File to_use;
    T[] to_create;
    File_Class<T>[] to_insert;
    public void setTo_create(T[] to_create) {
        this.to_create = to_create;
    }
    public void setTo_insert(File_Class<T>[] to_insert) {
        this.to_insert = to_insert;
    }
    public void setTo_use(File to_use) {
        this.to_use = to_use;
    }
    public T[] getTo_create() {
        return to_create;
    }
    public File_Class<T>[] getTo_insert() {
        return to_insert;
    }
    public File getTo_use() {
        return to_use;
    }
    public String getArClassName(){
        String[] sq1 = TUtils.getLine(to_use).split("###");
        String classname = sq1[sq1.length - 1].split("//")[0].split(";")[0].replaceAll("[\\[]L", "");
        return classname;
    }
    public String getArClassNameR(){
        String[] sq1 = TUtils.getLine(to_use).split("###");
        String classname = sq1[sq1.length - 1].split("//")[0].split(";")[0];
        return classname;
    }
    public String getArrayData(){
        String[] sq1 = TUtils.getLine(to_use).split("###");
        String classname = sq1[sq1.length - 1].split("//")[1];
        return classname;
    }
    public int getArrayLength(){
        String[] sq1 = TUtils.getLine(to_use).split("###");
        String classname = sq1[sq1.length - 1].split("//")[0].split(";")[1];
        return Integer.valueOf(classname);
    }
    public String[] getArrayDatas(){
        return this.getArrayData().split(";;");
    }
    public void initialise_toinsert(){
        String[] data = this.getArrayDatas();
        Class<?> tous = null;
        try {
            tous = Class.forName(this.getArClassName());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        this.setTo_create((T[]) Array.newInstance(tous, this.getArrayLength()));
        this.setTo_insert(new File_Class[this.getArrayLength()]);
        for (int i = 0; i < data.length; i++) {
            this.to_insert[i] = new File_Class<T>(new File(this.to_use.getParent() + "/" + data[i].split("::")[1]));
        }
    }
    public void init_Tocreate(){
        this.initialise_toinsert();
        for (int i = 0; i < to_create.length; i++) {
            this.to_create[i] = this.to_insert[i].getTo_create();
        }
    }
    public ArFile_Class(File to_use){
        this.setTo_use(to_use);
        this.init_Tocreate();
    }

}
