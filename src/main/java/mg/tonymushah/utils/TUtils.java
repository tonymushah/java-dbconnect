package mg.tonymushah.utils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import mg.tonymushah.utils.class_t_file.ArClass_File;
import mg.tonymushah.utils.enums.PrimaryClasses;
import mg.tonymushah.utils.file_t_class.ArFile_Class;

public class TUtils {
    public static PrimaryClasses getPrimaryClass(Class<?> to_evaluate){
        return PrimaryClasses.getPrimaryClass(to_evaluate);
    }
    public static String whoami(Object object){
        String returns = "";
        returns = object.getClass().getName();
        return returns;
    }

    public static String[] whoamiA(Object[] objects){
        String[] returns = new String[objects.length];
        for (int i = 0; i < returns.length; i++) {
            returns[i] = whoami(objects[i]);
        }
        return returns;
    }
    public static void pr_whoamiA(Object[] objects) {
        String[] show = whoamiA(objects);
        for (int i = 0; i < show.length; i++) {
            System.out.println(i + " : " + show);
        }
    }
    public static<T extends Object> String[] methods_info(T object){
        Method[] methods = object.getClass().getMethods();
        String[] returns = new String[methods.length];
        for (int i = 0; i < returns.length; i++) {
            returns[i] = "" + methods[i].getReturnType().getName() + " " + methods[i].getName() + "(";
            Class<?>[] args = methods[i].getParameterTypes();
            if(args == null){
                
            }else if (args.length == 1){
                returns[i] = returns[i] + "" + args[0].getName();
            }else{
                for (Class<?> class1 : args) {
                    returns[i] = returns[i] + "" + class1.getTypeName()+ ", ";
                }
            }
            returns[i] = returns[i] + ")";
        }
        return returns;
    }
    public static<T extends Object> void pr_methods_info(T object) {
        String[] strings = methods_info(object);
        for (int i = 0; i < strings.length; i++) {
            System.out.println(i + " --> " + strings[i]);
        }
    }

    public static<T extends Object> double fsum(T[] objects, String gmethod){
        double returns = 0;
        Integer p = 0;
        for (int i = 0; i < objects.length; i++) {
            try {
                Method use = objects[i].getClass().getMethod(gmethod);
                double sqp;
                if(use.invoke(objects[i]).getClass().getName() == p.getClass().getName()){
                    sqp = (int) use.invoke(objects[i]);
                }else{
                    sqp = (double) use.invoke(objects[i]);
                }
                returns = returns + sqp;
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return returns;
    }

    public static<T extends Object> Field[] getSuperFields(Class<T> obj_class){
        if(obj_class.getSuperclass().getName() != Object.class.getName()){
            return obj_class.getSuperclass().getDeclaredFields();
        }else{
            return null;
        }
    }

    public static<T extends Object> Field[] getAllFields(Class<T> obj_class) {
        if(obj_class.getSuperclass().getName() != Object.class.getName()){
            Field[] cfields = obj_class.getDeclaredFields();
            Field[] sFields = getSuperFields(obj_class);
            Field[] returns = new Field[cfields.length + sFields.length];
            int i = 0;
            for (int j1 = 0; j1 < sFields.length; j1++) {
                returns[i] = sFields[j1];
                i++;
            }
            for (int j = 0; j < cfields.length; j++) {
                returns[i] = cfields[j];
                i++;
            }
            return returns;
        }else{
            return obj_class.getDeclaredFields();
        }
        
    }
    public static<T extends Object> double[] fdoubleA(T[] objects, String gmethod){
        double[] returns = new double[objects.length];
        Integer p = 0;
        for (int i = 0; i < objects.length; i++) {
            try {
                Method use = objects[i].getClass().getMethod(gmethod);
                if(use.invoke(objects[i]).getClass().getName() == p.getClass().getName()){
                    returns[i] = (int) use.invoke(objects[i]);
                }else{
                    returns[i] = (double) use.invoke(objects[i]);
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return returns;
    }
    public static<T extends Object> double fmax(T[] objects, String gmethod){
        double returns = 0;
        double[] returnis = fdoubleA(objects, gmethod);
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < returnis.length; j++) {
                if(returnis[j] > returns){
                    returns = returnis[j];
                    returnis[j] = 0;
                }
            }
        }
        return returns;
    }
    public static<T extends Object> double fmin(T[] objects, String gmethod){
        double returns = fmax(objects, gmethod);
        double[] returnis = fdoubleA(objects, gmethod);
        for (int i = 0; i < objects.length; i++) {
            for (int j = 0; j < returnis.length; j++) {
                if(returnis[j] < returns){
                    returns = returnis[j];
                    //returnis[j] = 0;
                }
            }
        }
        return returns;
    }
    public static<T extends Object> double fmoyen(T[] objects, String gmethod) {
        return (fsum(objects, gmethod) / objects.length);
    }
    public static Field[] getSuperFields(Object object){
        if(object.getClass().getSuperclass().getName() != Object.class.getName()){
            return object.getClass().getSuperclass().getDeclaredFields();
        }else{
            return null;
        }
    }

    public static<T extends Object> Field[] getAllFields(T object) {
        if(object.getClass().getSuperclass().getName() != Object.class.getName()){
            Field[] cfields = object.getClass().getDeclaredFields();
            Field[] sFields = getSuperFields(object);
            Field[] returns = new Field[cfields.length + sFields.length];
            int i = 0;
            for (int j1 = 0; j1 < sFields.length; j1++) {
                returns[i] = sFields[j1];
                i++;
            }
            for (int j = 0; j < cfields.length; j++) {
                returns[i] = cfields[j];
                i++;
            }
            return returns;
        }else{
            return object.getClass().getDeclaredFields();
        }
        
    }
    public static<T extends Object> Field[] getAllFields(T object, Class<?> exception) {
        if(object.getClass().getSuperclass().getName() != Object.class.getName() || object.getClass().getSuperclass().getName() != exception.getName() ){
            Field[] cfields = object.getClass().getDeclaredFields();
            Field[] sFields = getSuperFields(object);
            Field[] returns = new Field[cfields.length + sFields.length];
            int i = 0;
            for (int j1 = 0; j1 < sFields.length; j1++) {
                returns[i] = sFields[j1];
                i++;
            }
            for (int j = 0; j < cfields.length; j++) {
                returns[i] = cfields[j];
                i++;
            }
            return returns;
        }else{
            return object.getClass().getDeclaredFields();
        }
        
    }
    public static<T extends Object> Field getFieldbyName(T object, String name){
        Field[] all = getAllFields(object);
        for (Field field : all) {
            if (field.getName().compareTo(name) == 0) {
                return field;
            }
        }
        throw new NoSuchFieldError("Field called " + name);
    }
    public static<T extends Object> Method[] getSuperMethods(T object){
        if(object.getClass().getSuperclass().getName() != Object.class.getName()){
            return object.getClass().getSuperclass().getMethods();
        }
        throw new NoSuchMethodError("This class" + object.getClass().getName() + " has no super class (only Object)");
    }
    public static<T extends Object> Method[] getAllMethods(T object) {
        if(object.getClass().getSuperclass().getName() != Object.class.getName()){
            Method[] cfields = object.getClass().getMethods();
            Method[] sFields = getSuperMethods(object);
            Method[] returns = new Method[cfields.length + sFields.length];
            int i = 0;
            for (int j1 = 0; j1 < sFields.length; j1++) {
                returns[i] = sFields[j1];
                i++;
            }
            for (int j = 0; j < cfields.length; j++) {
                returns[i] = cfields[j];
                i++;
            }
            return returns;
        }else{
            return object.getClass().getDeclaredMethods();
        }
        
    }
    public static<T extends Object> Method[] getAllMethods(T object, Class<?> exception) {
        if(object.getClass().getSuperclass().getName() != Object.class.getName() || object.getClass().getSuperclass().getName() != exception.getName()){
            Method[] cfields = object.getClass().getMethods();
            Method[] sFields = getSuperMethods(object);
            Method[] returns = new Method[cfields.length + sFields.length];
            int i = 0;
            for (int j1 = 0; j1 < sFields.length; j1++) {
                returns[i] = sFields[j1];
                i++;
            }
            for (int j = 0; j < cfields.length; j++) {
                returns[i] = cfields[j];
                i++;
            }
            return returns;
        }else{
            return object.getClass().getDeclaredMethods();
        }
        
    }
    public static<T extends Object> Method getMethodbyName(T object, String name){
        Method[] methods = getAllMethods(object);
        for (Method method : methods) {
            if(method.getName() == name){
                return method;
            }
        }
        throw new NoSuchMethodError("No method named" + name); 
    }
    public static<T extends Object> Method getMethodbyName(Class<T> to_use, String name){
        Method[] methods = to_use.getMethods();
        for (Method method : methods) {
            if(method.getName() == name){
                return method;
            }
        }
        throw new NoSuchMethodError("No method named" + name); 
    }
    public static<T extends Object> String to_stringField(T to_use, Field to_take){
        String returns = "";
        try {
            to_take.setAccessible(true);
            Object interc = to_take.get(to_use);
            if(interc.getClass() == String.class){
                returns = (String) interc;
            }else if(interc.getClass() == Integer.class){
                returns = "" + ((int) interc);
            } 
            else if(interc.getClass() == Long.class){
                returns = "" + ((long) interc);
            }else if(interc.getClass() == Double.class){
                returns = "" + ((double) interc);
            }else if(interc.getClass() == float.class){
                returns = "" + ((float) interc);
            } 
            to_take.setAccessible(false);
        } catch (Exception e) {
            //TODO: handle exception
            //e.printStackTrace();
        }
        return returns;
    }
    public static String[] basic_classes(){
        String[] returns = new String[6];
        returns[0] = "Integer";
        returns[1] = "Boolean";
        returns[2] = "Float";
        returns[3] = "Double";
        returns[4] = "Long";
        returns[5] = "Short";
        return returns;
    }
    public static String[] basic_classic(){
        String[] returns = new String[8];
        returns[0] = "int";
        returns[1] = "boolean";
        returns[2] = "float";
        returns[3] = "double";
        returns[4] = "long";
        returns[5] = "short";
        returns[6] = "char";
        returns[7] = "java.lang.String";
        return returns;
    }
    public static<T extends Object, V extends Object> void setinField(T to_use, V value, Field to_take){
        try {
            to_take.setAccessible(true);
            if(to_take.getType().isAssignableFrom(value.getClass())){
                to_take.set(to_use, value);
            }else{
                for (int i = 0; i < basic_classes().length; i++) {
                    // I forgot why i need this
                    // Class<?> toc_use = Class.forName("java.lang." + basic_classes()[i]);
                    try {
                        switch (i) {
                            case 0:
                                to_take.setInt(to_use, Integer.valueOf((String) value));
                                break;
                            case 1:
                                to_take.setBoolean(to_use, Boolean.valueOf((String) value));
                                break;
                            case 2:
                                to_take.setFloat(to_use, Float.valueOf((String) value));
                                break;
                            case 3:
                                to_take.setDouble(to_use, Double.valueOf((String) value));
                                break;
                            case 4:
                                to_take.setLong(to_use, Long.valueOf((String) value));
                                break;
                            case 5:
                                to_take.setShort(to_use, Short.valueOf((String) value));
                                break;
                            
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        //TODO: handle exception
                        //e.printStackTrace();
                    }
                    
                }
            }
            to_take.setAccessible(false);
        } catch (Exception e) {
            //TODO: handle exception
            //e.printStackTrace();
        }

    }
   
    public static<T extends Object> boolean isBasicClasses(T to_use) {
        for (String basic_classe : basic_classic()) {
            if(to_use.getClass().getName().compareTo(basic_classe) == 0){
                return true;
            }
        }
        for (String basic_classe : basic_classes()) {
            if(to_use.getClass().getName().compareTo("java.lang." + basic_classe) == 0){
                return true;
            }
        }
        return false;
    }
    public static boolean isBasicClasses(Field to_use) {
        for (String basic_classe : basic_classic()) {
            if(to_use.getType().getName().compareTo(basic_classe) == 0){
                return true;
            }
        }
        for (String basic_classe : basic_classes()) {
            if(to_use.getType().getName().compareTo("java.lang." + basic_classe) == 0){
                return true;
            }
        }
        return false;
    }
    public static<T extends Object> HashMap<String, Object> getInInfoField(T object){
        Field[] fields = getAllFields(object);
        HashMap<String, Object> returns = new HashMap<String, Object>();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                returns.put(fields[i].getName(), fields[i].get(object));
            } catch (Exception e) {
                //TODO: handle exception
                //e.printStackTrace();
            }
            fields[i].setAccessible(false);
        }
        return returns;
    }
    public static<T extends Object> void showObject_info(T object){
        System.out.println(object.getClass().getName() + " > " + object.toString());
        Field[] fields = getAllFields(object);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println("==> " + field.getType().getName() + " : " + field.get(object));
            } catch (Exception e) {
                //TODO: handle exception
                //e.printStackTrace();
            }
            field.setAccessible(false);
        }
        System.out.println("---------------------");
    }
    public static BufferedWriter BufferedFileWriter(File file) {
        BufferedWriter returns = null;
        try {
            returns = new BufferedWriter(new FileWriter(file, true));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return returns;
    }
    public static void write_to_file(File to_write, String input) {
        BufferedWriter to_use = BufferedFileWriter(to_write);
        try {
            to_use.write(input);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        try {
            to_use.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }
    public static BufferedReader bufferedFileReader(File file) {
        BufferedReader returns = null;
        try {
            returns = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return returns;
    }
    public static String getLine(File file){
        String returns = "";
        try {
            returns = bufferedFileReader(file).readLine();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return returns;
    }
    public static String remplace_str(String to_use, String to_replace, String to_insert){
        to_use = to_use.replaceAll(to_replace, to_insert);
        return to_use;
    }
    public static int rand(int limits){
        Random random = new Random();
        return random.nextInt(limits);
    }
    public static<T extends Object> double isum(T[] objects, String arg){
        double returns = 0;
        for (Object object : objects) {
            try {
                Field input = getFieldbyName(object, arg);
                input.setAccessible(true);
                System.out.println((input.get(object)));
                returns = returns + Double.valueOf(String.valueOf((input.get(object))));
                input.setAccessible(false);
            } catch (Exception e) {
                //TODO: handle exception
               // e.printStackTrace();
            }
        }
        return returns;
    }
    public static<T extends Object> double imoyern(T[] objects, String arg){
        double returns = 0;
        for (Object object : objects) {
            try {
                Field input = getFieldbyName(object, arg);
                input.setAccessible(true);
                returns = returns + Double.valueOf(String.valueOf((input.get(object))));
                input.setAccessible(false);
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Tutils.sum()");
            }
        }
        return (returns / objects.length);
    }
    public static<T extends Object> double imax(T[] objects, String arg) {
        double max = 0;
        for (int i = 0; i < objects.length; i++) {
            if(objects[i] != null){
                Field to_use = getFieldbyName(objects[i], arg);
                to_use.setAccessible(true);
                double to_cmp = 0;
                try {
                    to_cmp = Double.valueOf(String.valueOf((to_use.get(objects[i]))));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                } 
                if (max < to_cmp) {
                    max = to_cmp;
                }
                to_use.setAccessible(false);
            }
        }
        return max;
    }
    public static<T extends Object> double imin(T[] objects, String arg) {
        double min = imax(objects, arg);
        for (int i = 0; i < objects.length; i++) {
            if(objects[i] != null){
                Field to_use = getFieldbyName(objects[i], arg);
                to_use.setAccessible(true);
                double to_cmp = 0;
                try {
                    to_cmp = Double.valueOf(String.valueOf((to_use.get(objects[i]))));
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
                if (min > to_cmp) {
                    min = to_cmp;
                }
                to_use.setAccessible(false);
            }
        }
        return min;
    }
    public static<T extends Object> T[] add(T[] array, T value){
        Class<?> to_use = array[0].getClass();
        T[] returns = (T[]) Array.newInstance(to_use, array.length + 1);
        for (int i = 0; i < array.length; i++) {
            returns[i] = array[i];
        }
        returns[returns.length - 1] = value;
        return returns;
    }
    public static<T extends Object> T[] addNew(T[] array){
        Constructor<?> to_use = null;
        try {
            to_use = array[0].getClass().getConstructor();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        
        T returns = null;
        try {
            returns = (T) to_use.newInstance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return add(array, returns);
    }
    public static<T extends Object> T[] clone(T[] array){
        ArClass_File<T> exp = new ArClass_File<T>(array, "data/");
        ArFile_Class<T> clone = new ArFile_Class<T>(exp.getArray_file());
        return clone.getTo_create();
    }
    public static<T extends Object> T[] sortDESC(T[] array, String arg){
        //Object[] to_use = array.clone();
        T[] to_use = clone(array);
        T[] returns = (T[]) Array.newInstance(array[0].getClass(), array.length);
        for (int i = 0; i < returns.length; i++) {
            double max = imax(to_use, arg);
            System.out.println(max);
            try {
                for (int z = 0; z < to_use.length; z++) {
                    Field to_fuse = getFieldbyName(array[z], arg);
                    to_fuse.setAccessible(true);
                    double to_cmp = 0;
                    try {
                        to_cmp = Double.valueOf(String.valueOf((to_fuse.get(to_use[z]))));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        //e.printStackTrace();
                    }
                    to_fuse.setAccessible(false);
                    if(to_cmp == max){
                        returns[i] = array[z];
                        to_use[z] = null;
                        break;
                    }
                }
            } catch (Exception e) {
                //TODO: handle exception
                //e.printStackTrace();
            }
        }
        return returns;
    }
    public static<T extends Object> T[] invOrder(T[] objects){
        T[] returns = (T[]) Array.newInstance(objects[0].getClass(), objects.length);
        for (int i = 0; i < returns.length; i++) {
            returns[i] = objects[(returns.length - 1) - i];
        }
        return returns;
    }
    public static<T extends Object> T[] sortASC(T[] array, String arg){
        return invOrder(sortDESC(array, arg));
    }
    public static<T extends Object> int search_length(T[] array, String text){
        int number = 0;
        for (T object : array) {
            TCeutils<T> to_use = new TCeutils<T>(object);
            for (int i = 0; i < to_use.getFieldslength() ; i++) {
                if(String.valueOf(to_use.getInField(to_use.getFields()[i])).indexOf(text) != -1){
                    number++;
                    break;
                }
            }
        }
        return number;
    }
    public static<T extends Object> T[] search(T[] array, String text){
        if(search_length(array, text) <= 0 ) {
            throw new NoSuchElementException("No fields that have the letter " + text);
        } else {
            T[] returns = (T[]) Array.newInstance(array[0].getClass(), search_length(array, text));
            int number = 0;
            for (T object : array) {
                TCeutils<T> to_use = new TCeutils<T>(object);
                for (int i = 0; i < to_use.getFieldslength() ; i++) {
                    if(String.valueOf(to_use.getInField(to_use.getFields()[i])).indexOf(text) != -1){
                        returns[number] = object;
                        number++;
                        break;
                    }
                }
            }
            return returns;
        }
    }
    public static<T extends Object> T[] deleteN(T[] array, int to_remove){
        T[] clone = array.clone();
        T[] returns = (T[]) Array.newInstance(array[0].getClass(), array.length - 1);
        if (to_remove >= array.length && to_remove < 0) {
            throw new ArrayIndexOutOfBoundsException("can't delete the index " + to_remove + " of the array " + array.toString());
        } else {
            clone[to_remove] = null;
            int index = 0;
            for (int i = 0; i < clone.length; i++) {
                if (clone[i] != null) {
                    returns[index] = clone[i];
                    index++;
                }
            }
        }
        return returns;
    }
    public static<T extends Object> T[] deleteN(T[] array, int[] to_remove){
        T[] clone = array.clone();
        T[] returns = (T[]) Array.newInstance(array[0].getClass(), array.length - to_remove.length);
        for (int to_remodve : to_remove) {
            try {
                if(to_remodve >= array.length && to_remodve < 0){
                    throw new ArrayIndexOutOfBoundsException("can't delete the index " + to_remodve + " of the array " + array.toString());
                }else{
                    clone[to_remodve] = null;
                }
            } catch (Exception e) {
                //TODO: handle exception
                //e.printStackTrace();
            }
        }
        int index = 0;
        for (int i = 0; i < clone.length; i++) {
            if (clone[i] != null) {
                returns[index] = clone[i];
                index++;
            }
        }
        return returns;
    }
    public static<T extends Object> T[] split(T[] objects, int i1, int i2) throws ArrayIndexOutOfBoundsException{
        if(i1 != i2){
            T[] returns = objects.clone();
            returns[i1] = objects[i2];
            returns[i2] = objects[i1];
            return returns;
        }else{
            throw new ArrayIndexOutOfBoundsException("Cannot split in the same index");
        }
    }
    public static int get_filteredFieldsBy_Annotations_length(Field[] to_filter, Class<? extends Annotation> annotation){
        int returns = 0;
        for (Field field : to_filter) {
            if(field.isAnnotationPresent(annotation)){
                returns = returns + 1;
            }
        }
        return returns;
    }
    public static Field[] get_filteredFieldsBy_Annotations(Field[] to_filter, Class<? extends Annotation> annotation){
        int length = TUtils.get_filteredFieldsBy_Annotations_length(to_filter, annotation);
        int index = 0;
        Field[] returns = new Field[length];
        for (Field field : to_filter) {
            if(field.isAnnotationPresent(annotation)){
                returns[index] = field;
                index = index + 1;
            }
        }
        return returns;
    }
}
