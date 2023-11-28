// 최종 2차 테스트 코드
package last_nano_test;

import java.util.*;

public class TestSnakeAndLadderGame2 {
    static long StartTime = System.nanoTime(); // [test code] 클래스 시간 측정
    static int row;
    static int column;
    static int numberSnake;
    static int numberLadder;
    static int[][] pan;
    static boolean[][] snake_position;
    static boolean[][] snake_prohibit_position;
    static boolean[][] ladder_position;
    static boolean[][] ladder_prohibit_position;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("행(열)의 크기를 입력하세요: ");

        row = scanner.nextInt(); // 가로 크기 입력
        column = row; // 세로 크기 == 가로 크기

        numberSnake = (row * column) / 20; // 전체 크기의 5% 만큼 뱀 생성
        numberLadder = (row * column) / 20; // 전체 크기의 5% 만큼 사다리 생성

        pan = new int[row + 1][column + 1]; // 주사위 굴린 횟수를 계산하기 위한 배열. 게임판 1,1 방문시 pan[1][1]에 +1 기록
        snake_position = new boolean[row + 1][column + 1]; // 뱀 위치 배열
        snake_prohibit_position = new boolean[row + 1][column + 1]; // 뱀 생성 불가능 한 위치 배열
        ladder_position = new boolean[row + 1][column + 1]; // 사다리 위치 배열
        ladder_prohibit_position = new boolean[row + 1][column + 1]; // 사다리 생성 불가능 한 위치 배열
        int now_position_x = 0; // 현재 말의 x 위치
        int now_position_y = 1; // 현재 말의 y 위치
        int a = 1; // [test code]


        long MethodStartTime = System.nanoTime(); // [test code] 뱀, 사다리 생성시간 측정
        makeSnakePosition(); // 뱀 무작위 위치 생성
        makeLadderPosition(); // 사다리 무작위 위치 생성
        long MethodEndTime = System.nanoTime(); // [test code] 뱀, 사다리 생성시간 측정

        long WhileStartTime = System.nanoTime(); // [test code] 주사위 던지는 반복문 시간 측정
        while (now_position_y != row + 1) { // 현재 말의 y 위치가 최대 세로 길이를 초과하는 경우
            int dice = (int) (Math.random() * 6 + 1); // 1~6 사이의 주사위 굴리기
            System.out.println("!!!!!!!"+a+"번째 게임!!!!!!!"); // [test code]
            System.out.println("주사위 눈 : " + dice);
            a++; // [test code]

            if((now_position_x + dice) <= column){ // 주사위를 굴려 윗 줄로 이동하지 않는 경우
                now_position_x += dice;
            } else { // 주사위를 굴려 윗 줄로 이동하는 경우(가로 칸을 넘을 경우)
                now_position_x = (now_position_x+dice) - column;
                now_position_y++;
            }
            if(now_position_y == row+1) break;
            if(isPositonSnake(now_position_y, now_position_x)==true){
                System.out.println("[뱀 발견] " + "y 좌표 : " + now_position_y + ", x 좌표 : " + now_position_x+ "   ---->   ");
                if(now_position_y!=1 && now_position_x-1==0){ // 첫번째 행을 제외하고 맨 왼쪽 열에서 뱀을 탈 경우
                    now_position_y --;
                    now_position_x = column;
                } else {
                    now_position_x --;
                }
            }

            if(isPositionladder(now_position_y, now_position_x)==true){
                System.out.println("[사다리 발견] " + "y 좌표 : " + now_position_y + ", x 좌표 : " + now_position_x+ "   ---->   ");
                now_position_y ++;
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
        System.out.println("프로그램 전체 평균 수행시간 : " + (EndTime - StartTime)/1000000000.0 + "초"); // [test code]

        int cnt = 0; // 주사위 굴린 횟수 저장하는 변수 선언 및 초기화
        for (int i = 1; i <= row; i++) { // 이중 for문을 돌면서 pan 배열에 있는 숫자를 더해서 저장
            for (int j = 1; j <= column; j++) {
                cnt += pan[i][j];
            }
        }
        System.out.println("주사위 굴린 횟수 : " + (cnt + 1)); // 마지막 주사위를 던져 나가는 횟수도 계산 해줘야 함
    }

    public static void makeSnakePosition() { // 뱀 위치 랜덤 생성 함수
        for (int i = 0; i < numberSnake; i++) { // 총 공간에서 5%의 뱀을 생성
            int y = (int) (Math.random() * row + 1); // 1 ~  row 사이의 무작위 값
            int x = (int) (Math.random() * column + 1); // 1 ~ column 사이의 무작위 값

            if (snake_position[y][x] || snake_prohibit_position[y][x]) {
                i--;
            } else if (x == column) { // 제일 우측 열에 뱀 생성
                if (y == row) { // [row][column]에 뱀 생성
                    snake_position[y][x] = true;
                    snake_prohibit_position[y][x - 1] = true;
                } else { // [가장 위에 있는 열을 제외한 열][column]에 뱀 생성
                    snake_position[y][x] = true;
                    snake_prohibit_position[y][x - 1] = true;
                    snake_prohibit_position[y + 1][1] = true;
                }
            } else if (x == 1) { // 제일 왼쪽 열에 뱀 생성
                if (y == 1) { // [1][1]에 뱀 생성
                    snake_position[y][x] = true;
                    snake_prohibit_position[y][x + 1] = true;
                } else { // [1을 제외한 열][1]
                    snake_position[y][x] = true;
                    snake_prohibit_position[y - 1][column] = true;
                }
            } else { // 제일 왼쪽 열과 제일 우측 열이 아닌 공간의 뱀 생성
                snake_position[y][x] = true;
                snake_prohibit_position[y][x - 1] = true;
                snake_prohibit_position[y][x + 1] = true;
            }
        }
    }

    public static void makeLadderPosition(){ // 사다리 위치 랜덤 생성 함수
        for(int i=0; i<numberLadder; i++){
            int y = (int) (Math.random()*row+1);
            int x = (int) (Math.random()*column+1);

            if(snake_position[y][x]==false || ladder_position[y][x] == false || ladder_prohibit_position[y][x] == false){
                if(y==1){
                    ladder_position[y][x] = true;
                    ladder_prohibit_position[y+1][x] = true;
                } else if(y==row) { // 사다리가 맨 위의 줄에 생성되는 경우
                    i--; // 생성 불가능 하기 때문에 한번 더 생성하도록 함
                } else {
                    ladder_position[y][x] = true;
                    ladder_prohibit_position[y-1][x] = true;
                    ladder_prohibit_position[y+1][x] = true;
                }
            } else {
                i--;
            }
        }
    }

    public static boolean isPositonSnake(int y, int x){ // 해당 위치에 뱀이 있는 지 확인하는 함수
        if(snake_position[y][x]==true){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPositionladder(int y, int x){ // 해당 위치에 사다리가 있는지 확인하는 함수
        if(ladder_position[y][x]==true){
            return true;
        } else {
            return false;
        }
    }
}
