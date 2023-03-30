package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import entity.Member;
import query.Query;
import repository.UserRepository;

public class Login {
    /**
     * 로그인
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void login() {
        Scanner sc = new Scanner(System.in);
        Member member = new Member();
        ResultSet rs = null;
        Commute commute = new Commute();

        System.out.println();
        System.out.print("아이디 : ");
        member.setId(sc.next());
        System.out.print("비밀번호 : ");
        member.setPassword(sc.next());
        System.out.println();

        try {
            Class.forName(UserRepository.DB_DRIVE_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
            Connection con = DriverManager.getConnection(UserRepository.DB_URL, UserRepository.DB_USER_ID, UserRepository.DB_USER_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(Query.MEMBER_SELECT);) {
            // parameter 설정
            stmt.setString(1, member.getId());
            stmt.setString(2, SHA256.encrypt(member.getPassword()));
            rs = stmt.executeQuery();

            // 행 개수 카운트
            rs.last();
            int rowCount = rs.getRow();
            //rs.beforeFirst();

            // 아이디가 존재할 경우
            if (rowCount == 1) {
                System.out.println("[ 로그인 성공 ]");
                commute.commute(member.getId());

            // 아이디가 존재하지 않을 경우
            } else {
                System.out.println("[ 로그인 실패 ]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
