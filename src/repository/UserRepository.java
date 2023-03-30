package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import entity.CommuteInfo;
import query.Query;

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

    CommuteInfo commute = new CommuteInfo();
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    /**
     * 출근 등록
     * 
     * @param id 로그인한 아이디
     * 
     * @return 등록 결과
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public Boolean workIn(String id){
        Boolean result = false;
    
        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        try (
            Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(Query.ON_TIME_INSERT);) {
            // parameter 설정
            stmt.setInt(1, commute.getCommuteIndex());
            stmt.setString(2, id);
            commute.setOnTime(currentTimestamp);
            stmt.setTimestamp(3, commute.getOnTime());
            stmt.executeUpdate();

            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return result;
    }

    /**
     * 퇴근 등록
     * 
     * @param id 로그인한 아이디
     * 
     * @return 등록 결과
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public Boolean workOut(String id){
        Boolean result = false;

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(Query.OFF_TIME_INSERT);) {
            // parameter 설정
            commute.setOffTime(currentTimestamp);
            stmt.setTimestamp(1, commute.getOffTime());
            stmt.setString(2, id);
            stmt.executeUpdate();
            
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 출퇴근 조회 관리자 인증
     * 
     * @param id 로그인한 아이디
     * 
     * @return 관리자 인증 결과
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public Boolean isAdmin(String id) {
        Boolean isAdmin = false;

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (id.equals("admin"))
            isAdmin = true;
        
        return isAdmin;
    } 

    /**
     * 출퇴근 조회
     * 
     * @param isAdmin 관리자 인증 결과
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void readCommuteList(Boolean isAdmin, String id){
        
        // 관리자일 경우
        if (isAdmin) {
            try (
                Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
                PreparedStatement stmt = con.prepareStatement(Query.COMMUTE_READ_ALL);) {
                // stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();

                System.out.println();
                System.out.println("========================================== Commute List ==========================================");
                while (rs.next()) {
                    System.out.println("아이디: " + rs.getString("id") + " / 이름: " + rs.getString("name") + " / 출근: " + rs.getString("on_time") + " / 퇴근: " + rs.getString("off_time"));
                }
                System.out.println("==================================================================================================");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        // 일반 회원일 경우
        } else {
            try (
                Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
                PreparedStatement stmt = con.prepareStatement(Query.COMMUTE_READ);) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();

                System.out.println();
                System.out.println("========================================== Commute List ==========================================");
                while (rs.next()) {
                    System.out.println("아이디: " + rs.getString("id") + " / 이름: " + rs.getString("name") + " / 출근: " + rs.getString("on_time") + " / 퇴근: " + rs.getString("off_time"));
                }
                System.out.println("==================================================================================================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}