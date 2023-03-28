package repository;

/**
 * DB 연동 모듈
 * 
 * @author zeonghun
 * @since 2023.03.28
 */
public class UserRepository {
    
    // DB drive 경로
    public final static String DB_DRIVE_PATH = "org.mariadb.jdbc.Driver";

    /** DB 접속정보 */
    public final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/work_manager";
    public final static String DB_USER_ID = "root";
    public final static String DB_USER_PASSWORD = "1234";
}
