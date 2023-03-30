package service;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Scanner;

import entity.Member;
import repository.UserRepository;

public class Join {

    /**
     * 회원가입
     * 
     * @throws NoSuchAlgorithmException
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void join(Scanner sc) throws NoSuchAlgorithmException {
        // Scanner sc = new Scanner(System.in);
        Member member = new Member();
        UserRepository userRepo = new UserRepository();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        System.out.println();
        System.out.print("아이디 : ");
        member.setId(sc.next());
        System.out.print("비밀번호 : ");
        member.setPassword(sc.next());
        System.out.print("이름 : ");
        member.setName(sc.next());
        member.setJoinDate(currentTimestamp);

        boolean joinResult = userRepo.join(member.getId(), member.getPassword(), member.getName(), member.getJoinDate());

        if (joinResult){
            System.out.println();
            System.out.println("[ 회원가입 완료 ]");
        } else {
            System.err.println("[ 아이디 중복 ]");
        }
    }
}
