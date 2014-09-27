import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by tish on 23.09.2014.
 */
public class DBMapper implements IMapper{

    private static Connection connection;

    {
        InputStream is = getClass().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Object o) {
        String query = QueryBuilder.insertQuerySQL(o);

        PreparedStatement ps;
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            ps = connection.prepareStatement(query);

            int count = 1;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (!fields[i].getName().equals("id")){
                    if (fields[i].getType().equals(String.class)) {
                        ps.setString(count, String.valueOf(fields[i].get(o)));
                    } else if (fields[i].getType().equals(int.class)) {
                        ps.setInt(count, Integer.parseInt(String.valueOf(fields[i].get(o))));
                    } else if (fields[i].getType().equals(long.class)) {
                        ps.setLong(count, Long.parseLong(String.valueOf(fields[i].get(o))));
                    } else if (fields[i].getType().equals(byte.class)) {
                        ps.setByte(count, Byte.parseByte(String.valueOf(fields[i].get(o))));
                    } else if (fields[i].getType().equals(double.class)) {
                        ps.setDouble(count, Double.parseDouble(String.valueOf(fields[i].get(o))));
                    } else if (fields[i].getType().equals(float.class)) {
                        ps.setFloat(count, Float.parseFloat(String.valueOf(fields[i].get(o))));
                    } else if (fields[i].getType().equals(short.class)) {
                        ps.setShort(count, Short.parseShort(String.valueOf(fields[i].get(o))));
                    } else if (fields[i].getType().equals(boolean.class)) {
                        ps.setBoolean(count, Boolean.parseBoolean(String.valueOf(fields[i].get(o))));
                    }
                    count++;
                }

            }

            ps.executeUpdate();
            connection.close();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object load(int id, Class clazz) throws IllegalAccessException {
        return null;
    }

    @Override
    public List<Object> loadAll(Class clazz) {
        return null;
    }

    @Override
    public void update(int id, Object o) {

    }

    @Override
    public void delete(int id, Class clazz) {

    }
}
