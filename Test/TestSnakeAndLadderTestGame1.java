package last_nano_test;
// 최종 1차 코드
import java.util.HashMap;
import java.util.Scanner;

public class test1 {
    static long StartTime = System.nanoTime(); // [test code] 클래스 시간 측정
  
    static int row;
    static int column;
    static int numberSnake;
    static int numberLadder;
    static int[][] pan;
    static HashMap<Integer, Integer> snakeMap = new HashMap<Integer, Integer>();
    static HashMap<Integer, Integer> ladderMap = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("행(열)의 크기를 입력하세요: ");

        row = scanner.nextInt();
        column  = row;
        pan = new int[row + 2][column + 1]; // 주사위 굴린 횟수를 계산하기 위한 배열
        numberSnake = (row * column) / 20; // 전체 크기의 5% 만큼 뱀 생성
        numberLadder = (row * column) / 20; // 전체 크기의 5% 만큼 뱀 생성
        int now_position_x = 0; // 현재 말의 x 위치
        int now_position_y = 1; // 현재 말의 y 위치
        int a  = 1;  // [test code]


        long MethodStartTime = System.nanoTime(); // [test code] 뱀, 사다리 생성시간 측정 
        makeSnakePosition(); // 뱀 무작위 위치 생성
        makeLadderPosition(); // 사다리 무작위 위치 생성
        long MethodEndTime = System.nanoTime(); // [test code] 뱀, 사다리 생성시간 측정


        long WhileStartTime = System.nanoTime(); // [test code] 주사위 던지는 반복문 시간 측정
        while (now_position_y != row+1) { // 현재 말의 y 위치가 최대 세로 길이를 초과하는 경우
            int dice = (int) (Math.random()*6+1); // 1~6 사이의 주사위 굴리기
            System.out.println("!!!!!!!"+a+"번째 게임!!!!!!!");
            System.out.println("주사위 눈 : " + dice);
            a++; // [test code]

            if((now_position_x + dice) <= column){ // 주사위를 굴려도 윗 줄로 이동하지 않는 경우
                now_position_x += dice;
            } else { // 주사위를 굴리면 윗 줄로 이동하는 경우(가로 칸을 넘을 경우)
                now_position_x = (now_position_x+dice) - column;
                now_position_y++;
            }
            if(now_position_y == row+1) break;

            boolean foundSnake = false;
            boolean foundLadder = false;

            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= column; j++) {
                    int tmp = (i-1)*row + j;
                    if(i == now_position_y && j == now_position_x && (snakeMap.containsKey(tmp) && snakeMap.get(tmp)==1)){
                        foundSnake = true;
                        break;
                    }
                }
                if(foundSnake) break;
            }


            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= column; j++) {
                    int tmp = (i-1)*row + j;
                    if(i == now_position_y && j == now_position_x && (ladderMap.containsKey(tmp) && ladderMap.get(tmp)==1)){
                        foundLadder = true;
                        break;
                    }
                }
                if(foundSnake) break;
            }

            if (foundSnake) {
                System.out.println("[뱀 발견] " + "y 좌표 : " + now_position_y + ", x 좌표 : " + now_position_x+ "   ---->   ");

                if (now_position_y != 1 && now_position_x - 1 == 0) { // y == 1이지 않고 x-1 == 0)일 때
                    now_position_y--; // 한 줄 내려감
                    now_position_x = column; // x 좌표를 마지막으로 이동
                } else {
                    now_position_x--; // (1,1) 과 나머지 경우
                }
            }

            if (foundLadder) {
                System.out.println("[사다리 발견] " + "y 좌표 : " + now_position_y + ", x 좌표 : " + now_position_x+ "   ---->   ");
                now_position_y++;
            }

            System.out.println("y 좌표 : " + now_position_y + ", x 좌표 : " + now_position_x);
            System.out.println();
            pan[now_position_y][now_position_x] = pan[now_position_y][now_position_x] + 1;
        } // while문 종료

        System.out.println("--------------게임 종료--------------");
        System.out.println("[게임 결과]");
        long EndTime = System.nanoTime(); // [test code] 클래스 시간, 반복문 시간 측정에 사용

        System.out.println("뱀 및 사다리 위치 생성 함수 이전 수행시간 : " + (MethodStartTime - StartTime)/1000000000.0 + "초"); // [test code]
        System.out.println("뱀 및 사다리 위치 생성 함수 평균 수행시간  : " + (MethodEndTime - MethodStartTime)/1000000000.0 + "초"); // [test code]
        System.out.println("주사위 반복 평균 수행시간 : " + (EndTime - WhileStartTime)/1000000000.0 + "초"); // [test code]
        System.out.println("프로그램 실행 시간 : " + (EndTime - StartTime)/1000000000.0 + "초"); // [test code]

        int cnt = 0; // 주사위 굴린 횟수 저장하는 변수 선언 및 초기화
        for (int i = 1; i <= row; i++) { // 이중 for문을 돌면서 pan 배열에 있는 숫자를 더해서 저장
            for (int j = 1; j <= column; j++) {
                cnt += pan[i][j];
            }
        }
        System.out.println("주사위 굴린 횟수 : " + (cnt + 1)); // 마지막 탈출 할 때 한번 던지는 것...
    }

    public static void makeSnakePosition(){ // 뱀 위치 랜덤 생성 함수
        for(int i=0; i<numberSnake; i++){ // 총 공간에서 5%의 뱀을 생성
            int n = (int) (Math.random()*row*column+1); // 1 ~  (가로*세로) 사이의 무작위 값

            if(snakeMap.containsKey(n) && snakeMap.get(n) == 1){
                i--;
            } else {
                if(n<=1){ // 뱀이 1,1에 있는 경우
                    snakeMap.put(1, 1);
                    snakeMap.put(n+1, 2);
                } else {
                    snakeMap.put(n, 1);
                    snakeMap.put(n-1, 2);
                    snakeMap.put(n+1, 2);
                }
            }
        }
    }

    public static void makeLadderPosition(){ // 사다리 위치 랜덤 생성 함수
        for(int i=0; i<numberSnake; i++){ // 총 공간에서 5%의 뱀을 생성
            int n = (int) (Math.random()*row*column+1); // 1 ~  (가로*세로) 사이의 무작위 값

            if((snakeMap.containsKey(n)&&snakeMap.get(n)==1) || (ladderMap.containsKey(n)&&ladderMap.get(n)==1) || (ladderMap.containsKey(n)&&ladderMap.get(n)==2)){
                i--;
            } else {
                if(n<=column) { // 가장 아래에 있는 행에 사다리 생성
                    ladderMap.put(n, 1);
                    ladderMap.put(n+column, 2);
                } else if(n>(column-1)*row){ // 가장 위에 있는 행에 사다리 생성
                    i--;
                } else {
                    ladderMap.put(n, 1);
                    ladderMap.put(n+column, 2);
                    ladderMap.put(n-column, 2);
                }
            }
        }
    }
}


