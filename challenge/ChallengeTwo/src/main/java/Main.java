import data.Dao;
import data.model.L1Service;
import data.model.L2Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validate.ValidateService;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Dao<L1Service> l1ServiceDao = Dao.getInstance(Dao.class);
        Dao<L2Service> l2ServiceDao = Dao.getInstance(Dao.class);
        List<L1Service> l1 = l1ServiceDao.select("Select l FROM L1Service l", L1Service.class);
        List<L2Service> l2 = l2ServiceDao.select("Select l FROM L2Service l", L2Service.class);

        ValidateService validateService = new ValidateService();
        validateService.validateData(l1,l2);
    }
}
