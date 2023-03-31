package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import entity.Member;
import repository.UserRepository;

public class Login {

    /**
     * 로그인
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void login(Scanner sc) {
        // Scanner sc = new Scanner(System.in);
        UserRepository userRepo = new UserRepository();
        Member member = new Member();
        Commute commute = new Commute();
        int rowCount = 0;

        System.out.println();
        System.out.print("아이디 : ");
        member.setId(sc.next());
        System.out.print("비밀번호 : ");
        member.setPassword(sc.next());
        System.out.println();

        ResultSet loginQueryResult = userRepo.login(member.getId(), member.getPassword());
        
        // 행 개수 카운트
        try {
            loginQueryResult.last();
            rowCount = loginQueryResult.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 아이디가 존재할 경우
        if (rowCount == 1) {
            System.out.println("[ 로그인 성공 ]");
            commute.commute(member.getId(), sc);

        // 아이디가 존재하지 않을 경우
        } else {
            System.out.println("[ 로그인 실패 ]");
        }
    }
}
