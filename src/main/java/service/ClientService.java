package service;

import org.apache.log4j.Logger;

/**
 * Created by alexm on 07.11.2019.
 */
public class ClientService {
    private final static Logger logger = Logger.getLogger(ClientService.class);
    private volatile static ClientService instance;

    private ClientService() {

    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static ClientService getInstance() {
        if (instance == null) {
            synchronized (ClientService.class) {
                if (instance == null) {
                    return instance = new ClientService();
                }
            }
        }
        return instance;
    }


}
