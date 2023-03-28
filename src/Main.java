import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    /**
     * (non-javadoc)
     * 
     * @author zeonghun
     * @since 2023.03.28
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        Join join = new Join();
        int index;
        
        do {
            try {
                printMenu();
                index = sc.nextInt();

                switch (index) {
                    // 로그인
                    case 1:
                        login.login();
                        break;
                    // 회원가입
                    case 2:
                        join.join();
                        break;
                    // 종료
                    case 3:
                        System.out.println();
                        System.out.println("종료합니다.");
                        System.out.println();
                        break;
                    default:
                        System.out.println();
                        System.out.println("1~3중에서 입력하세요.");
                }
                // 예외처리
            } catch (InputMismatchException ime) {
                System.out.println();
                System.err.println("숫자만 입력하세요.");
                sc.nextLine();
                index = 1;
            }
        } while (index != 3);
        sc.close();
    }

    /**
     * 메뉴 출력
     * 
     * @author zeonghun
     * @since 2023.03.28
     */
    public static void printMenu() {
        System.out.println();
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");
        System.out.println();
        System.out.print("항목 선택: ");
    }
}
