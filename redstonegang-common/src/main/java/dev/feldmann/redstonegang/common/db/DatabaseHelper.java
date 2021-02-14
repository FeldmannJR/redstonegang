package dev.feldmann.redstonegang.common.db;

import dev.feldmann.redstonegang.common.db.annotations.DBField;
import dev.feldmann.redstonegang.common.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    DatabaseManager db;

    public DatabaseHelper(DatabaseManager db) {
        this.db = db;
    }

    private void extract(ResultSet rs, Field f, String fieldName, Object ob) throws SQLException, IllegalAccessException {
        if (f.getType() == Integer.class || f.getType() == int.class) {
            f.set(ob, rs.getInt(fieldName));
        } else if (f.getType() == String.class) {
            f.set(ob, rs.getString(fieldName));
        } else if (f.getType() == Boolean.class || f.getType() == boolean.class) {
            f.set(ob, rs.getBoolean(fieldName));
        }
    }

    public String getType(Field f, Object ex) throws IllegalAccessException {
        String tipo;
        if (f.getType() == String.class) {
            String def = "null";
            if (f.get(ex) != null) {
                def = "'" + f.get(ex) + "'";
            }
            DBField sf = f.getAnnotation(DBField.class);
            if (sf != null && sf.isText()) {
                tipo = "TEXT DEFAULT " + def;
            } else {
                tipo = "VARCHAR(255) DEFAULT " + def;
            }

        } else if (f.getType() == boolean.class) {
            tipo = "BOOLEAN DEFAULT " + f.get(ex);
        } else if (f.getType() == int.class) {
            tipo = "INTEGER NOT NULL DEFAULT " + f.get(ex) + " ";
        } else {
            return null;
        }
        return tipo;
    }

    public void addColumn(Connection c, String table, String name, String tipo) {
        try {
            String query = "ALTER TABLE `" + table + "` ADD `" + name + "` " + tipo;
            c.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        public void alterTable(String table, Class classe, Object base) {
            try {
                for (Field f : ReflectionUtils.getFieldsWithAnnotation(classe, DBField.class)) {
                    String nome = f.getName();
                    DBField save = f.getAnnotation(DBField.class);
                    if (save.onlyRead()) {
                        continue;
                    }
                    if (!save.nome().isEmpty()) {
                        nome = save.nome();
                    }
                    if (!columnExists(table, nome)) {
                        addColumn(table, nome, getType(f, base));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    */
    public void extract(ResultSet rs, Class c, Object ob, String prefix) {

        for (Field f : ReflectionUtils.getFieldsWithAnnotation(c, DBField.class)) {
            try {
                String nome = f.getName();
                DBField sf = f.getAnnotation(DBField.class);
                if (!sf.nome().isEmpty()) {
                    nome = sf.nome();
                }
                extract(rs, f, prefix + nome, ob);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }


    public static boolean columnExists(Connection c, String table, String column) {
        try {
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getColumns(null, null, table, column);
            c.close();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
