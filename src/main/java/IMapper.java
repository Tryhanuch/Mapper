import java.util.List;

/**
 * Created by tish on 01.09.2014.
 */
interface IMapper {
    
    void save(Object o);

    Object load(int id, Class clazz) throws IllegalAccessException;

    List<Object> loadAll(Class clazz);

    void update(int id,Object o);

    void delete(int id, Class clazz);
}
