package mg.tonymushah.utils.class_t_file;

import java.io.*;

import mg.tonymushah.utils.TCeutils;
import mg.tonymushah.utils.TUtils;


public class Class_File {
    File fishing;
    Object to_use;
    TCeutils to_p;
    public void setTo_p(TCeutils to_p) {
        this.to_p = to_p;
    }
    public TCeutils getTo_p() {
        return to_p;
    }
    public File getFishing() {
        return fishing;
    }
    public Object getTo_use() {
        return to_use;
    }
    public void setTo_use(Object to_use) {
        this.to_use = to_use;
    }
    public void setcnFishing(File fishing) {
        this.fishing = fishing;
        try {
            this.fishing.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    public void setFishing(File fishing) {
        this.fishing = fishing;
    }
    public void write_class_to_file(){
        //System.out.println(touse);
        
        /* [!] Writing structure 
            Class_file :
            classname//"field_stucture";;"field_stucture";;..||"next_class"

            field structure :
            "field_name"::"field_type"=="field_value"
        */
        // FINISHED Class name
        String part1 = to_use.getClass().getName();
        
        // FINISHED input of the the fields
        String[] str_field = this.to_p.Fields_to_strArray();

        // TODO concate and write to the file
        String returns = "";
            // NOTE header
            returns = returns + part1;
            returns = returns + "//";
            
            // NOTE fields to returns
            for (int i = 0; i < str_field.length; i++) {
                returns = returns + str_field[i];
                if (i != (str_field.length - 1)) {
                    returns = returns + ";;";
                }
            }
            returns = returns + "###";
        TUtils.write_to_file(fishing, returns);
    }
    public String getInFishing(){
        return TUtils.getLine(fishing);
    }
    public String generate_Cfilename(){
        String returns = "";
        
        String touse = this.to_use.toString();
        //System.out.println(touse);
        
        String part1 = touse.split("@")[0];
        part1 = TUtils.remplace_str(part1, "classesf.", "");
        part1 = TUtils.remplace_str(part1, "classes.", "");
        //System.out.println(part1);
        //System.out.println();
        
        String part2 = touse.split("@")[1];
        //System.out.println(part2);
        
        returns = part1 + "__" + part2 + "C" + TUtils.rand(9) + ".Tclass";
        //System.out.println(returns);
        
        return returns;
    }
    
    public String generate_filename(){
        char[] totest = to_use.toString().toCharArray();
        if(totest[0] == '[' && totest[1] == 'L'){
            throw new ArrayStoreException("the object " + to_use.toString() + " is an array");
            //return this.generate_Cafilename();
        }else{
            return this.generate_Cfilename();
        }
    }
    public Class_File(Object to_write){
        this.setTo_use(to_write);
        this.setTo_p(new TCeutils(to_write));
        this.setcnFishing(new File(this.generate_filename()));
        this.write_class_to_file();
    }
    public Class_File(Object to_write, String path){
        this.setTo_use(to_write);
        this.setTo_p(new TCeutils(to_write));
        this.setcnFishing(new File(path + this.generate_filename()));
        this.write_class_to_file();
    }
    public Class_File(Object to_write, String path , String additional){
        this.setTo_use(to_write);
        this.setTo_p(new TCeutils(to_write));
        this.setcnFishing(new File(path + this.generate_filename() + additional));
        this.write_class_to_file();
    }
    public Class_File(){
        
    }
}
