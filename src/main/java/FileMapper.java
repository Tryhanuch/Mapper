import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by tish on 01.09.2014.
 */
public class FileMapper implements IMapper {

    public static final String DATA_EXT = ".txt";
    public static final String CONF_EXT = ".conf";

    @Override
    public void save(Object o) {
        String dataFileName = o.getClass().getSimpleName() + DATA_EXT;
        File dataFile = new File(getPath() + dataFileName);

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(dataFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> fieldsName = readConfigFile(o);
        List<Field> fields = new ArrayList<>();

        for (int i = 0; i < fieldsName.size(); i++) {
            try {
                Field f = o.getClass().getDeclaredField(fieldsName.get(i));
                f.setAccessible(true);
                fields.add(f);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        assert bw != null;
        for (int i = 0; i < fields.size(); i++) {
            try {
                bw.write(String.valueOf(fields.get(i).get(o)));
                if (i != fields.size() - 1){
                    bw.write(":");
                }
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        try {
            bw.write("\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object load(int id, Class clazz) throws IllegalAccessException {
        String dataFileName = clazz.getSimpleName() + DATA_EXT;
        File dataFile = new File(getPath() + dataFileName);

        Object result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String s;
        String[] fields = null;
        assert br != null;
        try {
            while((s = br.readLine()) != null){
                if (s.startsWith(String.valueOf(id) + ":")){
                    fields = s.split(":");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<String> fieldsName = readConfigFile(result);

        for (int i = 0; i < fieldsName.size(); i++) {
            Field f = null;
            try {
                assert result != null;
                f = result.getClass().getDeclaredField(fieldsName.get(i));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            assert f != null;
            assert fields != null;
            f.setAccessible(true);

            if (f.getType().equals(String.class)) {
                f.set(result, fields[i]);
            } else if (f.getType().equals(int.class)) {
                f.set(result, Integer.parseInt(fields[i]));
            } else if (f.getType().equals(long.class)) {
                f.set(result, Long.parseLong(fields[i]));
            } else if (f.getType().equals(byte.class)) {
                f.set(result, Byte.parseByte(fields[i]));
            } else if (f.getType().equals(double.class)) {
                f.set(result, Double.parseDouble(fields[i]));
            } else if (f.getType().equals(float.class)) {
                f.set(result, Float.parseFloat(fields[i]));
            } else if (f.getType().equals(short.class)) {
                f.set(result, Short.parseShort(fields[i]));
            } else if (f.getType().equals(boolean.class)) {
                f.set(result, Boolean.parseBoolean(fields[i]));
            }
        }

        return result;
    }

    @Override
    public List<Object> loadAll(Class clazz) {
        String dataFileName = clazz.getSimpleName() + DATA_EXT;
        File dataFile = new File(getPath() + dataFileName);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String s;
        List<Integer> id = new ArrayList<>();
        assert br != null;
        try {
            while ((s = br.readLine()) != null){
                String[] line = s.split(":");
                id.add(Integer.parseInt(line[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Object> result = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            try {
                result.add(load(id.get(i), clazz));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public void update(int id, Object o) {
        String dataFileName = o.getClass().getSimpleName() + DATA_EXT;
        File dataFile = new File(getPath() + dataFileName);

        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        String s;
        StringBuilder sb = new StringBuilder();
        assert br != null;
        try {
            while ((s = br.readLine()) != null){
                if (s.startsWith(String.valueOf(id))){
                    List<String> fieldsName = readConfigFile(o);
                    List<Field> fields = new ArrayList<>();

                    for (int i = 0; i < fieldsName.size(); i++) {
                        try {
                            Field f = o.getClass().getDeclaredField(fieldsName.get(i));
                            f.setAccessible(true);
                            fields.add(f);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }

                    s = "";
                    for (int i = 0; i < fields.size(); i++) {
                        s += String.valueOf(fields.get(i).get(o));
                        if (i != fields.size() - 1){
                            s += ":";
                        }
                    }
                }
                sb.append(s);
                sb.append("\n");
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
            bw.write(String.valueOf(sb));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id, Class clazz) {
        String dataFileName = clazz.getSimpleName() + DATA_EXT;
        File dataFile = new File(getPath() + dataFileName);

        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        String s;
        StringBuilder sb = new StringBuilder();
        assert br != null;
        try {
            while ((s = br.readLine()) != null){
                if (!s.startsWith(String.valueOf(id))){
                    sb.append(s);
                    sb.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
            bw.write(String.valueOf(sb));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readConfigFile(Object o) {
        String configFileName = o.getClass().getSimpleName() + CONF_EXT;
        File configFile = new File(getPath() + configFileName);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(configFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> fieldsName = new ArrayList<>();
        String s;
        try {
            assert br != null;
            while((s = br.readLine()) != null){
                fieldsName.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fieldsName;
    }

    private String getPath(){
        String path = null;
        InputStream is;
        Properties properties = new Properties();

        try {
            is = getClass().getResourceAsStream("config.properties");
            properties.load(is);

            path = properties.getProperty("path");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}
