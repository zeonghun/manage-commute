package service;
import java.util.InputMismatchException;
import java.util.Scanner;

import repository.UserRepository;

/**
 * 출퇴근 등록 및 조회
 * 
 * @author zeonghun
 * @since 2023.03.30
 */
public class Commute {

    private UserRepository userRepo;

    /**
     * 출근등록, 퇴근등록, 출퇴근조회 선택
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void commute(String id) {
        Scanner sc = new Scanner(System.in);
        int option = 0;

        while (option != 4) {
            try {
                printOption();
                option = sc.nextInt();

                switch (option) {
                    // 출근 입력
                    case 1:
                        workIn(id);
                        break;
                    // 퇴근 입력
                    case 2:
                        workOut(id);
                        break;
                    // 출퇴근 조회
                    case 3:
                        readCommuteList(id);
                        break;
                    // 로그아웃
                    case 4:
                        System.out.println();
                        System.out.println("[ 로그아웃 완료 ]");
                        break;
                    default:
                        System.out.println();
                        System.out.println("[ 입력 범위 : 1 ~ 4 ]");
                }
                // 예외처리
            } catch (InputMismatchException ime) {
                System.out.println();
                System.err.println("[ 숫자만 입력 가능 ]");
                sc.nextLine();
                option = 1;
            }
        }
    }

    /**
     * 옵션 출력
     * 
     * @author zeonghun
     * @since 2023.03.29
     */
    public void printOption() {
        System.out.println();
        System.out.println("1. 출근");
        System.out.println("2. 퇴근");
        System.out.println("3. 조회");
        System.out.println("4. 로그아웃");
        System.out.println();
        System.out.print("항목 선택: ");
    }

    /**
     * 출근 등록
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void workIn(String id) {
        this.userRepo = new UserRepository();
        boolean workInResult =  this.userRepo.workIn(id);

        if (workInResult) {
            System.out.println();
            System.out.printf("[ 출근 등록 완료 : %s ]\n", id);
        } else {
            System.out.println("[ 출근 등록 실패 ]");
        }
    }

    /**
     * 퇴근 등록
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void workOut(String id) {
        this.userRepo = new UserRepository();
        boolean workOutResult =  this.userRepo.workOut(id);

        if (workOutResult) {
            System.out.println();
            System.out.printf("[ 퇴근 등록 완료 : %s ]\n", id);
        } else {
            System.out.println("[ 퇴근 등록 실패 ]");
        }
    }

    /**
     * 출퇴근 조회
     * 
     * @param id 로그인한 아이디
     * 
     * @author zeonghun
     * @since 2023.03.30
     */
    public void readCommuteList(String id) {
        this.userRepo = new UserRepository();
        boolean isAdmin =  this.userRepo.isAdmin(id);
        userRepo.readCommuteList(isAdmin, id);
    }
}
