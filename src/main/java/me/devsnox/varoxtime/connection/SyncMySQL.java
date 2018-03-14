package me.devsnox.varoxtime.connection;

import java.sql.*;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class SyncMySQL {

    private Connection connection;
    private VaroxConnection varoxConnection;

    public SyncMySQL(VaroxConnection varoxConnection, Connection connection) {
        this.connection = connection;
        this.varoxConnection = varoxConnection;
    }

    public void update(String statment) {
        refresh();
        if (statment != null) {
            try {
                Statement st = this.connection.createStatement();
                st.executeUpdate(statment);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void preparedUpdate(String statment) {
        refresh();
        if (statment != null) {
            try {
                PreparedStatement st = this.connection.prepareStatement(statment);
                st.executeUpdate(statment);
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void preparedUpdate(PreparedStatement statment) {
        refresh();
        if (statment != null) {
            try {
                statment.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public ResultSet query(String statment) {
        refresh();
        if (connection != null) {
            if (statment == null) {
                return null;
            } else {
                ResultSet resultSet = null;

                try {
                    Statement st = this.connection.createStatement();
                    resultSet = st.executeQuery(statment);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return resultSet;
            }
        }
        return null;
    }

    private void refresh() {
        try {
            if (!this.connection.isValid(2) || this.connection.getMetaData() == null) {
                this.varoxConnection.disconnect();
                this.varoxConnection.connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
