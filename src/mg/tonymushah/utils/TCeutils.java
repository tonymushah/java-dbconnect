package mg.tonymushah.utils;

import java.lang.reflect.*;

import mg.tonymushah.utils.class_t_file.ArClass_File;


public class TCeutils<T extends Object> {
    protected T to_use;
    protected Field[] fields;
    protected Method[] methods;
    protected void setFields(Field[] fields) {
        this.fields = fields;
    }
    protected void setMethods(Method[] methods) {
        this.methods = methods;
    }
    protected void setTo_use(T to_use) {
        this.to_use = to_use;
    }
    public Field[] getFields() {
        return fields;
    }
    public Method[] getMethods() {
        return methods;
    }
    public T getTo_use() {
        return to_use;
    }
    public void setFields_Methods(){
        this.setFields(TUtils.getAllFields(to_use));
        this.setMethods(TUtils.getAllMethods(to_use));
    }
    public void setFieldsAccessible(boolean arg) {
        for (Field field : this.fields) {
            field.setAccessible(arg);
        }
    }
    public void setMethodsAccessible(boolean arg) {
        for (Method method : this.methods) {
            method.setAccessible(arg);
        }
    }
    public int getFieldslength() {
        return this.fields.length;
    }
    public int getMethodslength() {
        return this.methods.length;
    }
    public void setAllAcessible(boolean arg){
        this.setFieldsAccessible(arg);
        this.setMethodsAccessible(arg);
    }
    public TCeutils(T object){
        this.setTo_use(object);
        this.setFields_Methods();
    }
    public Object getInField(String name){
        for (Field field : fields) {
            if(field.getName().compareTo(name) == 0){
                field.setAccessible(true);
                try {
                    Object returns = field.get(to_use);
                    return returns;
                } catch (Exception e) {
                    //TODO: handle exception
                    //e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }
        throw new NoSuchFieldError("No field named " + name + " in the class " + this.to_use.getClass().getName());
    }
    public Object getInField(Field fi){
        String name = fi.getName();
        for (Field field : fields) {
            if(field.getName().compareTo(name) == 0){
                field.setAccessible(true);
                try {
                    Object returns = field.get(to_use);
                    return returns;
                } catch (Exception e) {
                    //TODO: handle exception
                    //e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }
        throw new NoSuchFieldError("No field named " + name + " in the class " + this.to_use.getClass().getName());
    }
    public Method getMethodByName(String name) {
        return TUtils.getMethodbyName(this.to_use , name);
    }
    public void setInField(String name, Object value){
        //System.out.println(name);
        int ao = 0;
        for (Field field : fields) {
            if (field.getName().compareToIgnoreCase(name) == 0) {
                TUtils.setinField(to_use, value, field);
                //System.out.println("ok");
                ao++;
                //System.out.println(ao);
            }
        }
        if (ao == 0) {
            throw new NoSuchFieldError("No field named " + name + " in the class " + this.to_use.getClass().getName());
        }
        
    }

    public void setInNField(int id, Object value){
        if(id >= 0 && id < this.getFieldslength()){
            Field field = this.fields[id];
            field.setAccessible(true);
            TUtils.setinField(to_use, value, field);
        }else{
            throw new IndexOutOfBoundsException("index " + id + " of " + this.getFieldslength());
        }
    }
    public String Field_to_array(int index){
        if(index < 0 || index >= this.fields.length){
            throw new ArrayIndexOutOfBoundsException("index is " + index + " of " + this.fields.length);
        }else if(fields[index].getType().getName().indexOf("[L") != -1){
            ArClass_File<?> array = new ArClass_File<Object>((Object[]) this.getInField(this.fields[index]));
            String returns = fields[index].getName() + "::" + fields[index].getType().getName() + "==" + array.getArray_file().getPath();
            return returns;
        }else{
            String returns = fields[index].getName() + "::" + fields[index].getType().getName() + "==" + String.valueOf(getInField(fields[index]));
            //System.out.println(returns);
            return returns;
        }
    }
    public String Field_to_array(int index, String path){
        if(index < 0 || index >= this.fields.length){
            throw new ArrayIndexOutOfBoundsException("index is " + index + " of " + this.fields.length);
        }else if(fields[index].getType().getName().indexOf("[L") != -1){
            ArClass_File<?> array = new ArClass_File<Object>((Object[]) this.getInField(this.fields[index]), path);
            String returns = fields[index].getName() + "::" + fields[index].getType().getName() + "==" + array.getArray_file().getPath();
            return returns;
        }else{
            String returns = fields[index].getName() + "::" + fields[index].getType().getName() + "==" + String.valueOf(getInField(fields[index]));
            //System.out.println(returns);
            return returns;
        }
    }
    // NOTE "Field_to_arrayD" show the information of field 
    public String Field_to_arrayD(int index){
        if(index < 0 || index >= this.fields.length){
            throw new ArrayIndexOutOfBoundsException("index is " + index + " of " + this.fields.length);
        }else{
            String returns = fields[index].getName() + "::" + fields[index].getType().getName() + "==" + String.valueOf(getInField(fields[index]));
            //System.out.println(returns);
            return returns;
        }
    }
    public String[] Fields_to_strArray(){
        String[] returns = new String[this.fields.length];
        for (int i = 0; i < fields.length; i++) {
            returns[i] = this.Field_to_array(i);
        }
        return returns;
    }

    public int getFieldIndex(String name){
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].getName().compareTo(name) == 0){
                return i;
            }
        }
        throw new NoSuchFieldError("No field named " + name + " in TCeutils " + this.toString());
    }
}