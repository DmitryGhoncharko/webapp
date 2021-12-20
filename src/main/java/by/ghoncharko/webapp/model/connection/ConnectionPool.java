package by.ghoncharko.webapp.model.connection;

import java.sql.Connection;

public interface ConnectionPool {
    /**
     * @return connection from connection pool
     */
    Connection getConnection();

    /**
     * @param connection add connection to connection pool
     */
    void releaseConnection(Connection connection);

    /**
     * Destroy pool - close all connections and deregister drivers
     */
    void destroyPool();

    /**
     * @return Instance of connection pool
     */
    static ConnectionPool getInstance() {
        return BlockingConnectionPool.getInstance();
    }

}
