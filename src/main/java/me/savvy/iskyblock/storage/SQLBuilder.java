package me.savvy.iskyblock.storage;

import me.savvy.iskyblock.ISkyblock;

import java.sql.*;

public class SQLBuilder {

    private String userName, password, port, databaseName, hostName;
    private Connection connection;

    public SQLBuilder(String userName, String password, String port, String databaseName, String hostName) {
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.databaseName = databaseName;
        this.hostName = hostName;
    }

    public PreparedStatement getPreparedStatement(String query) {
        try {
            return getConnection().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SQLBuilder executeUpdate(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public SQLBuilder executeUpdates(String...queries) {
        for (String query : queries) {
            executeUpdate(query);
        }
        return this;
    }

    public SQLBuilder executeUpdate(String query) {
        return executeUpdate(query, null);
    }

    public SQLBuilder executeUpdate(String query, Object...params) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            if (params != null && params.length > 0) {
                for (int i = 1; i < params.length; i++) {
                    stmt.setObject(i, params[i]);
                }
            }
            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException ex) { ex.printStackTrace(); }
        return this;
    }

    public ResultSet executeQuery(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        return rs;
    }


    public Object getObject(PreparedStatement preparedStatement, String objectToGet) {
        Object result = null;
        try {
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                result = results.getObject(objectToGet);
            }
            results.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String getString(PreparedStatement preparedStatement, String stringToGet) {
        return String.valueOf(getObject(preparedStatement, stringToGet));
    }

    public Integer getInteger(PreparedStatement preparedStatement, String intToGet) {
        return (int) getObject(preparedStatement, intToGet);
    }

    public boolean getBoolean(PreparedStatement preparedStatement, String booleanToGet) {
        return (boolean) getObject(preparedStatement, booleanToGet);
    }


    public Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            ISkyblock.getInstance().getLogger().severe("Could not find JDBC Driver");
            e.printStackTrace();
            return;
        }
        try {
            connection = DriverManager.getConnection
                    (String.format("jdbc:mysql://%s:%s/%s", this.hostName, this.port, this.databaseName),
                            this.userName, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if ((getConnection() != null) && (!getConnection().isClosed())) {
                getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String table, String key, String value) {
        boolean exists = false;
        try {
            PreparedStatement ps = getPreparedStatement(String.format("SELECT `%s` FROM `%s` WHERE `%s` = ?", key, table, key));
            ps.setString(1, value);
            ResultSet results = ps.executeQuery();
            exists = results.next();
            ps.close();
            results.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }
}