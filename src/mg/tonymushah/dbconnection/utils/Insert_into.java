package mg.tonymushah.dbconnection.utils;

import java.lang.reflect.Field;

import mg.tonymushah.dbconnection.utils.annotations.Column;
import mg.tonymushah.dbconnection.utils.annotations.Sequence;
import mg.tonymushah.dbconnection.utils.annotations.Table;
import mg.tonymushah.utils.TCeutils;
import mg.tonymushah.utils.TUtils;

public class Insert_into {
    public static String insert_into_part(Class<?> to_use) throws Exception{
        if(to_use.isAnnotationPresent(Table.class) == false){
            throw new Exception("@Table not specified in class " + to_use.getName());
        }else{ 
            String table_name = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_use.getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
            String query = "insert into " + table_name + " ( " ;
            Field[] fields = TUtils.get_filteredFieldsBy_Annotations(TUtils.getAllFields(to_use), Column.class);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                    String column_name = field.getAnnotationsByType(Column.class)[0].name(); 
                    query = query + column_name;
                    if(i != fields.length - 1){
                        query = query + ", ";
                    }
            }
            query = query + ") ";
            return query;
        }
    }
    public static String values_part(Object to_useObject) throws Exception{
        Class<?> to_use = to_useObject.getClass();
        if(to_use.isAnnotationPresent(Table.class) == false){
            throw new Exception("@Table not specified in class " + to_use.getName());
        }else{ 
            String query = "values ( " ;
            TCeutils<?> uCeutils = new TCeutils<Object>(to_useObject);
            Field[] fields = TUtils.get_filteredFieldsBy_Annotations(uCeutils.getFields(), Column.class);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];String values = "" + uCeutils.getInField(field); 
                    if(field.isAnnotationPresent(Sequence.class)){
                        mg.tonymushah.dbconnection.utils.Sequence sequence = (mg.tonymushah.dbconnection.utils.Sequence) ((Sequence) field.getAnnotationsByType(Sequence.class)[0]).to_use_Sequence().getConstructor().newInstance();
                        query = query + "(" + sequence.sequence_pattern() + ")";
                    }else if(values.compareTo("null") == 0){
                        query = query + "null";
                    }else if(field.getAnnotationsByType(Column.class)[0].isNumber()){
                        query = query + values;
                    }else if(field.getAnnotationsByType(Column.class)[0].isDate()){
                        //System.out.println(values);
                        //Date date = Date.valueOf(values);
                        query = query + "to_date('" + values.split("\\s+")[0] + "', 'yyyy-mm-dd')";
                    }else{
                        query = query + "'" + values + "'";
                    }
                    if(i != fields.length - 1){
                        query = query + ", ";
                    }
            }
            query = query + ") ";
            return query;
        }
    }
}
