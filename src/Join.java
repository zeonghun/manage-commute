import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Scanner;

import entity.Member;
import repository.Query;
import repository.SHA256;
import repository.UserRepository;

public class Join {
    /**
     * 회원가입
     * 
     * @author zeonghun
     * @throws NoSuchAlgorithmException
     * @since 2023.03.28
     */
    public void join() throws NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);
        Member member = new Member();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SHA256 sha256 = new SHA256();

        System.out.println();
        System.out.print("아이디: ");
        member.setId(sc.next());
        System.out.print("비밀번호: ");
        member.setPassword(sc.next());
        System.out.print("이름: ");
        member.setName(sc.next());
        member.setJoinDate(currentTimestamp);

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try (
            Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(Query.MEMBER_INSERT);) {
            // parameter 설정
            stmt.setString(1, member.getId());
            stmt.setString(2, sha256.encrypt(member.getPassword()));
            stmt.setString(3, member.getName());
            stmt.setTimestamp(4, member.getJoinDate());
            stmt.executeUpdate();
            System.out.println();
            System.out.println("회원가입이 완료되었습니다.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println();
            System.err.println("중복된 아이디입니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
