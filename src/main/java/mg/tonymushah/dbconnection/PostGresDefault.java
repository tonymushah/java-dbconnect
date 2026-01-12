package mg.tonymushah.dbconnection;

import java.lang.reflect.Field;
import java.sql.DriverManager;

import mg.tonymushah.dbconnection.utils.Insert_into_Postgre;
import mg.tonymushah.dbconnection.utils.annotations.Column;
import mg.tonymushah.dbconnection.utils.annotations.PrimaryKey;
import mg.tonymushah.utils.TCeutils;
import mg.tonymushah.utils.TUtils;

public class PostGresDefault extends DBConnect{
    public PostGresDefault(){
        super("jdbc", 
            "postgresql", 
            null, 
            "127.0.0.1", 
            5432, 
            "test_tony", 
            "test_tony", 
            "etu001844"
        );
    }
    @Override
    public void connect() throws Exception{
        String url = "" + this.getApi() + ":" + this.getDbtype() + "://" + this.getHost() + ":" + this.getPort() + "/" + this.getDbName();
        this.setConnection(DriverManager.getConnection(url, this.getUsername(), this.getPassword()));
        //System.out.println(url);
        this.getConnection().setAutoCommit(false);
    }
    @Override
    public boolean insert(Object to_use) throws Exception{
        String part_1 = Insert_into_Postgre.insert_into_part(to_use.getClass());
        String part_2 = Insert_into_Postgre.values_part(to_use);
        return this.executeQuery_withoutConnect_Close(part_1 + part_2);
    }
    @Override
    public boolean delete(Object to_use) throws Exception{
        Class<?> to_useClass = to_use.getClass();
        if(to_useClass.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false){
            throw new Exception("@Table not specified in class " + to_useClass.getName());
        }
        TCeutils<?> to_useCeutils = new TCeutils<Object>(to_use);
        String query = new String();
        Field[] primarKeys = TUtils.get_filteredFieldsBy_Annotations(to_useCeutils.getFields(), PrimaryKey.class);
        if(primarKeys == null || primarKeys.length == 0){
            throw new Exception("the object has no primary key");
        }
        String table_name = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_useClass.getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
        query = "delete from " + table_name + " where ";
        for (int index = 0; index < primarKeys.length; index++) {
            if(primarKeys[index].getAnnotationsByType(Column.class)[0].isNumber()){
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = " + to_useCeutils.getInField(primarKeys[index]);
            }else{
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = '" + to_useCeutils.getInField(primarKeys[index]) + "'";
            }
            if(index != primarKeys.length - 1){
                query = query + ",";
            }
        }
        return this.executeQuery_withoutConnect_Close(query);
    }
    @Override
    public boolean update(Object to_use) throws Exception{
        Class<?> to_useClass = to_use.getClass();
        if(to_useClass.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false){
            throw new Exception("@Table not specified in class " + to_useClass.getName());
        }
        TCeutils<?> to_useCeutils = new TCeutils<Object>(to_use);
        String query = new String();
        Field[] primarKeys = TUtils.get_filteredFieldsBy_Annotations(to_useCeutils.getFields(), PrimaryKey.class);
        if(primarKeys == null || primarKeys.length == 0){
            throw new Exception("the object has no primary key");
        }
        String table_name = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_useClass.getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
        query = "update " + table_name + " set " ;


        TCeutils<?> uCeutils = new TCeutils<Object>(to_use);
            Field[] fields = TUtils.get_filteredFieldsBy_Annotations(uCeutils.getFields(), Column.class);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];String values = "" + uCeutils.getInField(field); 
                String column_name = field.getAnnotationsByType(Column.class)[0].name(); 
                if(field.isAnnotationPresent(PrimaryKey.class) == false){
                    if(field.getAnnotationsByType(Column.class)[0].isNumber()){
                        query = query + " " + column_name + " = " + values;
                    }else if (values.compareTo("null") == 0){
                        query = query + " " + column_name + " = null";
                    }else if(field.getAnnotationsByType(Column.class)[0].isDate()){
                        //System.out.println(values);
                        //Date date = Date.valueOf(values);
                        query = query + " " + column_name + " = "  + "'" + values.split("\\s+")[0] + "'";
                    }else{
                        query = query + " " + column_name + " = "  + "'" + values + "'";
                    }
                    if(i != fields.length - 1){
                        query = query + ", ";
                    }
                }
            }

        query = query + " where ";
        for (int index = 0; index < primarKeys.length; index++) {
            if(primarKeys[index].getAnnotationsByType(Column.class)[0].isNumber()){
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = " + to_useCeutils.getInField(primarKeys[index]);
            }else{
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = '" + to_useCeutils.getInField(primarKeys[index]) + "'";
            }
            if(index != primarKeys.length - 1){
                query = query + ",";
            }
        }
        //System.out.println(query);
        //return true;
        return this.executeQuery_withoutConnect_Close(query);
    }

}
