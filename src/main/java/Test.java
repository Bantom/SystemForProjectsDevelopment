
import com.bulgakov.model.LightVersion.UserLight;

import java.util.List;

import static com.bulgakov.db.dbOracle.OracleUtils.getAllUsers;


/**
 * @author Bulgakov
 * @since 23.03.2017
 */
public class Test {


    public static void main(String[] args) {
        List<UserLight> list = getAllUsers();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).name);
        }
    }
}
