package mg.tonymushah.dbconnection.utils;

import java.sql.Connection;
import java.sql.ResultSet;

public class SQL_Function {
    public static ResultSet invoke(Connection connect, String name, Object ...args) throws Exception{
        String query = name + "(" ;
        for (int i = 0; i < args.length; i++) {
            Object object = args[i];
            if(object instanceof String){
                query = query + "'%s'";
            }else if (object instanceof Float){
                query = query + "%f";
            }else{
                query = query + "%d";
            }
            if(i != args.length - 1){
                query = query + ",";
            }
        }
        query = String.format("select * from " + query + ")", args);
        return connect.createStatement().executeQuery(query);
    }
    public static Object invoke_getObject(Connection connect, String name, Object ...args) throws Exception{
        ResultSet resultSet = SQL_Function.invoke(connect, name, args);
        resultSet.next();
        return resultSet.getObject(name);
    }
}
