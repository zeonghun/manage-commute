package repository;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;

import entity.CommuteInfo;
import entity.Member;
import query.Query;
import service.SHA256;

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

    Member member = new Member();
    CommuteInfo commute = new CommuteInfo();
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    /**
     * 회원가입
     * 
     * @param id 입력받은 아이디
     * @param password 입력받은 패스워드
     * @param name 입력받은 이름
     * @param joinDate 현재 시간
     * 
     * @return 회원가입 결과
     * 
     * @throws NoSuchAlgorithmException
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public Boolean join(String id, String password, String name, Timestamp joinDate) throws NoSuchAlgorithmException{
        Boolean result = false;

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(Query.MEMBER_INSERT);) {
            // parameter 설정
            stmt.setString(1, id);
            // 패스워드 암호화
            stmt.setString(2, SHA256.encrypt(password));
            stmt.setString(3, name);
            stmt.setTimestamp(4, joinDate);
            stmt.executeUpdate();
            
            result = true;

            // 예외처리
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println();
        } catch (SQLException e) {
            System.err.println();
        }

		return result;
    }

    /**
     * 로그인
     * 
     * @param id 입력받은 아이디
     * @param password 입력받은 패스워드
     * 
     * @return 쿼리 실행 결과
     * 
     * @throws NoSuchAlgorithmException
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public ResultSet login(String id, String password) throws NoSuchAlgorithmException {
        ResultSet result = null;

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID,UserRepository.DB_USER_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(Query.MEMBER_SELECT);) {
            stmt.setString(1, id);
            stmt.setString(2, SHA256.encrypt(password));
            result = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

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
            PreparedStatement stmt = con.prepareStatement(Query.OFF_TIME_UPDATE);) {
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
     * @return 쿼리 실행 결과
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public ResultSet readCommuteList(Boolean isAdmin, String id) {
        
        ResultSet result = null;
        
        // 관리자일 경우
        if (isAdmin) {
                try {
                    Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
                    PreparedStatement stmt = con.prepareStatement(Query.COMMUTE_READ_ALL);
                    result = stmt.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        // 일반 회원일 경우
        else {
                try {
                    Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
                    PreparedStatement stmt = con.prepareStatement(Query.COMMUTE_READ); 
                    stmt.setString(1, id);
                    result = stmt.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    } 
}