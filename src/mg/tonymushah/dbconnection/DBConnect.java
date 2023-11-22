package mg.tonymushah.dbconnection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;

import mg.tonymushah.dbconnection.utils.Insert_into;
import mg.tonymushah.dbconnection.utils.Predicate;
import mg.tonymushah.dbconnection.utils.SQL_Function;
import mg.tonymushah.dbconnection.utils.annotations.Column;
import mg.tonymushah.dbconnection.utils.annotations.PrimaryKey;
import mg.tonymushah.utils.TCeutils;
import mg.tonymushah.utils.TUtils;

public class DBConnect {
    private String api;
    private String dbtype;
    private String pilot;
    private String host;
    private int port;
    private String dbName;
    private Connection connection;
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApi() {
        return api;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbtype() {
        return dbtype;
    }

    public String getHost() {
        return host;
    }

    public String getPilot() {
        return pilot;
    }

    public int getPort() {
        return port;
    }

    public DBConnect(
            String api,
            String dbtype,
            String pilot,
            String host,
            int port,
            String dbName,
            String username,
            String password) {
        this.setApi(api);
        this.setDbName(dbName);
        this.setDbtype(dbtype);
        this.setHost(host);
        this.setPilot(pilot);
        this.setPort(port);
        this.setUsername(username);
        this.setPassword(password);
    }

    public void connect() throws Exception {
        String url = "" + this.getApi() + ":" + this.getDbtype() + ":" + this.getPilot() + ":" + this.getHost() + ":"
                + this.getPort() + ":" + this.getDbName();
        this.setConnection(DriverManager.getConnection(url, this.getUsername(), this.getPassword()));
        this.connection.setAutoCommit(false);
    }

    public void close() throws Exception {
        this.connection.close();
        this.setConnection(null);
    }

    public Statement createStatement() throws Exception {
        return this.connection.createStatement();
    }

    @Deprecated
    public Object[] executeQuery(String query, Class<?> to_use) throws Exception {
        try {
            this.close();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            this.connect();
        }
        Object[] array = (Object[]) java.lang.reflect.Array.newInstance(to_use, 1);
        int index = 0;
        Statement stmt = this.createStatement();
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            // System.out.println("index :" + index);
            if (index == 0) {
                array[0] = to_use.getConstructor().newInstance();
                TCeutils<?> toUse = new TCeutils<Object>(array[0]);
                for (Field object : toUse.getFields()) {
                    try {
                        toUse.setInField(object.getName(), res.getString(object.getName()));
                        // System.out.println(object.getName() + " : " + toUse.getInField(object) + "");
                    } catch (Exception e) {
                        // TODO: handle exception
                        // System.err.println(e.getMessage());
                    }
                }
            } else {
                TCeutils<?> toUse = new TCeutils<Object>(to_use.getConstructor().newInstance());
                for (Field object : toUse.getFields()) {
                    try {
                        toUse.setInField(object.getName(), res.getString(object.getName()));
                        // System.out.println(object.getName() + " : " + toUse.getInField(object) +
                        // "\n");
                    } catch (Exception e) {
                        // TODO: handle exception
                        // System.err.println(e.getMessage());
                    }

                }
                array = TUtils.add(array, toUse.getTo_use());
            }
            index = index + 1;
        }
        this.close();

        return array;
    }

    @Deprecated
    public Object[] executeQuery_withoutConnect_Close(String query, Class<?> to_use) throws Exception {
        Object[] array = (Object[]) java.lang.reflect.Array.newInstance(to_use, 1);
        int index = 0;
        Statement stmt = this.createStatement();
        ResultSet res = stmt.executeQuery(query);
        while (res.next()) {
            // System.out.println("index :" + index);
            if (index == 0) {
                array[0] = to_use.getConstructor().newInstance();
                TCeutils toUse = new TCeutils(array[0]);
                for (Field object : toUse.getFields()) {
                    try {
                        toUse.setInField(object.getName(), res.getString(object.getName()));
                        // System.out.println(object.getName() + " : " + toUse.getInField(object) + "");
                    } catch (Exception e) {
                        // TODO: handle exception
                        // System.err.println(e.getMessage());
                    }
                }
            } else {
                TCeutils toUse = new TCeutils(to_use.getConstructor().newInstance());
                for (Field object : toUse.getFields()) {
                    try {
                        toUse.setInField(object.getName(), res.getString(object.getName()));
                        // System.out.println(object.getName() + " : " + toUse.getInField(object) +
                        // "\n");
                    } catch (Exception e) {
                        // TODO: handle exception
                        // System.err.println(e.getMessage());
                    }

                }
                array = TUtils.add(array, toUse.getTo_use());
            }
            index = index + 1;
        }
        return array;
    }

    @Deprecated
    public boolean executeQuery(String query) throws Exception {
        try {
            this.close();
        } catch (Exception e) {
            // TODO: handle exception

        } finally {
            this.connect();
        }
        this.connect();
        Statement stmt = this.createStatement();
        boolean modified = stmt.execute(query);
        // ResultSet res = stmt.executeQuery(query);
        this.close();
        return modified;
    }

    @Deprecated
    public boolean executeQuery_withoutConnect_Close(String query) throws Exception {
        Statement stmt = this.createStatement();
        boolean modified = stmt.execute(query);
        // ResultSet res = stmt.executeQuery(query);
        return modified;
    }

    public Object[] select(Class to_use, String... columns_names) throws Exception {
        if (to_use.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false) {
            throw new Exception("@Table not specified in class " + to_use.getName());
        } else {
            String table_touse = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_use
                    .getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
            if (columns_names == null) {
                Class to_useClass = to_use;
                String query = "select * from " + table_touse;
                Statement stmt = this.createStatement();
                ResultSet res = stmt.executeQuery(query);
                return this.resultset_toObjects(res, to_useClass);
            } else if (columns_names.length == 0) {
                Class to_useClass = to_use;
                String query = "select * from " + table_touse;
                Statement stmt = this.createStatement();
                ResultSet res = stmt.executeQuery(query);
                return this.resultset_toObjects(res, to_useClass);
            } else {

                Class to_useClass = to_use;
                String query = "select ";
                for (int i = 0; i < columns_names.length; i++) {
                    if (i == columns_names.length - 1) {
                        query = query + columns_names[i] + " ";
                    } else {
                        query = query + columns_names[i] + ", ";
                    }
                }
                query = query + "from " + table_touse;
                // System.out.println(query);
                Statement stmt = this.createStatement();
                ResultSet res = stmt.executeQuery(query);
                return this.resultset_toObjects(res, to_useClass);
            }
        }
    }

    public Object[] selectWhere(Class to_use, String[] columns_names, Predicate[] predicates) throws Exception {
        if (to_use.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false) {
            throw new Exception("@Table not specified in class " + to_use.getName());
        } else {
            String table_touse = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_use
                    .getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
            if (columns_names == null) {

                Class to_useClass = to_use;
                String query = "select * from " + table_touse + " where ";
                for (int i = 0; i < predicates.length; i++) {
                    if (i == predicates.length - 1) {
                        query = query + predicates[i].to_string_withoutSuffix();
                    } else {
                        query = query + predicates[i].to_string_withSuffix();
                    }
                }
                Statement stmt = this.createStatement();
                ResultSet res = stmt.executeQuery(query);
                return this.resultset_toObjects(res, to_useClass);
            } else if (columns_names.length == 0) {
                Class to_useClass = to_use;
                String query = "select * from " + table_touse + " where ";
                for (int i = 0; i < predicates.length; i++) {
                    if (i == predicates.length - 1) {
                        query = query + predicates[i].to_string_withoutSuffix();
                    } else {
                        query = query + predicates[i].to_string_withSuffix();
                    }
                }
                Statement stmt = this.createStatement();
                ResultSet res = stmt.executeQuery(query);
                return this.resultset_toObjects(res, to_useClass);
            } else {
                Class to_useClass = to_use;
                String query = "select ";
                for (int i = 0; i < columns_names.length; i++) {
                    if (i == columns_names.length - 1) {
                        query = query + columns_names[i] + " ";
                    } else {
                        query = query + columns_names[i] + ", ";
                    }
                }
                query = query + "from " + table_touse + " where ";
                for (int i = 0; i < predicates.length; i++) {
                    if (i == predicates.length - 1) {
                        query = query + predicates[i].to_string_withoutSuffix();
                    } else {
                        query = query + predicates[i].to_string_withSuffix();
                    }
                }
                Statement stmt = this.createStatement();
                ResultSet res = stmt.executeQuery(query);
                return this.resultset_toObjects(res, to_useClass);
            }
        }
    }

    public boolean insert(Object to_use) throws Exception {
        String part_1 = Insert_into.insert_into_part(to_use.getClass());
        String part_2 = Insert_into.values_part(to_use);
        return this.executeQuery_withoutConnect_Close(part_1 + part_2);
    };

    public boolean delete(Object to_use) throws Exception {
        Class to_useClass = to_use.getClass();
        if (to_useClass.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false) {
            throw new Exception("@Table not specified in class " + to_useClass.getName());
        }
        TCeutils to_useCeutils = new TCeutils(to_use);
        String query = new String();
        Field[] primarKeys = TUtils.get_filteredFieldsBy_Annotations(to_useCeutils.getFields(), PrimaryKey.class);
        if (primarKeys == null || primarKeys.length == 0) {
            throw new Exception("the object has no primary key");
        }
        String table_name = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_useClass
                .getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
        query = "delete from " + table_name + " where ";
        for (int index = 0; index < primarKeys.length; index++) {
            if (primarKeys[index].getAnnotationsByType(Column.class)[0].isNumber()) {
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = "
                        + to_useCeutils.getInField(primarKeys[index]);
            } else {
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = '"
                        + to_useCeutils.getInField(primarKeys[index]) + "'";
            }
            if (index != primarKeys.length - 1) {
                query = query + ",";
            }
        }
        return this.executeQuery_withoutConnect_Close(query);
    }

    public boolean update(Object to_use) throws Exception {
        Class to_useClass = to_use.getClass();
        if (to_useClass.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false) {
            throw new Exception("@Table not specified in class " + to_useClass.getName());
        }
        TCeutils to_useCeutils = new TCeutils(to_use);
        String query = new String();
        Field[] primarKeys = TUtils.get_filteredFieldsBy_Annotations(to_useCeutils.getFields(), PrimaryKey.class);
        if (primarKeys == null || primarKeys.length == 0) {
            throw new Exception("the object has no primary key");
        }
        String table_name = ((mg.tonymushah.dbconnection.utils.annotations.Table) (to_useClass
                .getAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Table.class)[0])).name();
        query = "update " + table_name + " set ";

        TCeutils uCeutils = new TCeutils(to_use);
        Field[] fields = TUtils.get_filteredFieldsBy_Annotations(uCeutils.getFields(), Column.class);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String values = "" + uCeutils.getInField(field);
            String column_name = field.getAnnotationsByType(Column.class)[0].name();
            if (field.isAnnotationPresent(PrimaryKey.class) == false) {
                if (field.getAnnotationsByType(Column.class)[0].isNumber()) {
                    query = query + " " + column_name + " = " + values;
                } else if (values.compareTo("null") == 0) {
                    query = query + " " + column_name + " = null";
                } else if (field.getAnnotationsByType(Column.class)[0].isDate()) {
                    // System.out.println(values);
                    // Date date = Date.valueOf(values);
                    query = query + " " + column_name + " = " + "to_date('" + values.split("\\s+")[0]
                            + "', 'yyyy-mm-dd')";
                } else {
                    query = query + " " + column_name + " = " + "'" + values + "'";
                }
                if (i != fields.length - 1) {
                    query = query + ", ";
                }
            }
        }

        query = query + " where ";
        for (int index = 0; index < primarKeys.length; index++) {
            if (primarKeys[index].getAnnotationsByType(Column.class)[0].isNumber()) {
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = "
                        + to_useCeutils.getInField(primarKeys[index]);
            } else {
                query = query + primarKeys[index].getAnnotationsByType(Column.class)[0].name() + " = '"
                        + to_useCeutils.getInField(primarKeys[index]) + "'";
            }
            if (index != primarKeys.length - 1) {
                query = query + ",";
            }
        }
        // System.out.println(query);
        // return true;
        return this.executeQuery_withoutConnect_Close(query);
    }

    public Object[] resultset_toObjects(ResultSet res, Class to_useClass) throws Exception {
        if (to_useClass.isAnnotationPresent(mg.tonymushah.dbconnection.utils.annotations.Table.class) == false) {
            throw new Exception("@Table not specified in class " + to_useClass.getName());
        } else {

            Object[] array = (Object[]) java.lang.reflect.Array.newInstance(to_useClass, 1);
            int index = 0;
            while (res.next()) {
                // System.out.println("index :" + index);
                if (index == 0) {
                    array[0] = to_useClass.getConstructor().newInstance();
                    TCeutils toUse = new TCeutils(array[0]);
                    for (Field object : toUse.getFields()) {
                        try {
                            toUse.setInField(object.getName(), res.getString(
                                    object.getDeclaredAnnotationsByType(mg.tonymushah.dbconnection.utils.annotations.Column.class)[0]
                                            .name()));
                            // System.out.println(object.getName() + " : " + toUse.getInField(object) + "");
                        } catch (Exception e) {
                            // TODO: handle exception
                            // System.err.println(e.getMessage());
                        }
                    }
                } else {
                    TCeutils toUse = new TCeutils(to_useClass.getConstructor().newInstance());
                    for (Field object : toUse.getFields()) {
                        try {
                            toUse.setInField(
                                    object.getName(),
                                    res.getString(
                                            object.getDeclaredAnnotationsByType(
                                                    mg.tonymushah.dbconnection.utils.annotations.Column.class)[0].name()));
                            // System.out.println(object.getName() + " : " + toUse.getInField(object) +
                            // "\n");
                        } catch (Exception e) {
                            // TODO: handle exception
                            // System.err.println(e.getMessage());
                        }

                    }
                    array = TUtils.add(array, toUse.getTo_use());
                }
                index = index + 1;
            }
            return array;
        }
    }

    public ResultSet invoke(String func_name, Object ...args) throws Exception{
        return SQL_Function.invoke(this, func_name, args);
    }
    public Object[] invoke(String func_name, Class to_use, Object ...args) throws Exception{
        ResultSet res = this.invoke(func_name, args);
        return this.resultset_toObjects(res, to_use);
    }
    public Object invoke_getObject(String func_name, Object ...args) throws Exception{
        return SQL_Function.invoke_getObject(this, func_name, args);
    }
}
