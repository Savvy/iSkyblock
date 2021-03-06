package me.savvy.iskyblock.storage.enums;

import me.savvy.iskyblock.ISkyblock;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum QueryStatements {

    ISLAND_SELECT("SELECT * FROM `islands` WHERE `id` = ?");

    private String query;

    QueryStatements(String query) {
        this.query = query;
    }

    public PreparedStatement getStatement(Object...params) {
        PreparedStatement ps = null;
        try {
            ps = ISkyblock.getInstance().getSqlBuilder().getPreparedStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ps;
    }
}
