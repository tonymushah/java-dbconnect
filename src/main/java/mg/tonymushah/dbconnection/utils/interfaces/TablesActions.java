package mg.tonymushah.dbconnection.utils.interfaces;

import mg.tonymushah.dbconnection.DBConnect;

public interface TablesActions {
    public default boolean insert(DBConnect connect) throws Exception{
        return connect.insert(this);
    }
    public default boolean patch(DBConnect connect) throws Exception{
        return connect.update(this);
    }
    public default boolean delete(DBConnect connect) throws Exception{
        return connect.delete(this);
    }
}
