import entity.Contacts;
import entity.UserInfo;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by igoryan on 28.02.16.
 */
public class ServiceTest {
    public EntityManager em = Persistence.createEntityManagerFactory("Database").createEntityManager();

    @Test
    public void addTest() {

    }
}
