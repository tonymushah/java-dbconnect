package mg.tonymushah.dbconnection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Oracle11gConnect extends DBConnect {
    private String api;
    private String dbtype;
    private String pilot;
    private String host;
    private int port;
    private String dbName;
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

    public Oracle11gConnect(
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

    public void connect() throws SQLException {
        String url = "" + this.getApi() + ":" + this.getDbtype() + ":" + this.getPilot() + ":" + this.getHost() + ":"
                + this.getPort() + ":" + this.getDbName();
        this.setConnection(DriverManager.getConnection(url, this.getUsername(), this.getPassword()));
        this.getConnection().setAutoCommit(false);
    }
}
