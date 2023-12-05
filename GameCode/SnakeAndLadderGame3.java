// 최종 3차 코드
package last;

import java.util.Scanner;

public class SnakeAndLadderGame3 {
    static int len;
    static int numberSnake;
    static int numberLadder;
    static short[] snake_position;
    static short[] ladder_position;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("행(열)의 크기를 입력하세요: ");

        len = scanner.nextInt();

        numberSnake = (len * len) / 20; // 전체 크기의 5% 만큼 뱀 생성
        numberLadder = (len * len) / 20; // 전체 크기의 5% 만큼 뱀 생성
        snake_position = new short[len*len+2];
        ladder_position = new short[len*len+2];
        int now_position_x = 0; // 현재 말의 x 위치
        int now_position_y = 1; // 현재 말의 y 위치
        int cnt = 0; // 주사위 굴린 횟수

        makeSnakePosition(); // 뱀 무작위 위치 생성
        makeLadderPosition(); // 사다리 무작위 위치 생성

        while (now_position_y != len+1) { // 현재 말의 y 위치가 최대 세로 길이를 초과하는 경우
            int dice = (int) (Math.random()*6+1); // 1~6 사이의 주사위 굴리기
            System.out.println("주사위 눈 : " + dice);

            if((now_position_x + dice) <= len){ // 주사위를 굴려 윗 줄로 이동하지 않는 경우
                now_position_x += dice;
            } else { // 주사위를 굴려 윗 줄로 이동하는 경우(가로 칸을 넘을 경우)
                now_position_x = (now_position_x+dice) - len;
                now_position_y++;
            }
            cnt++;
            if(now_position_y == len+1) break;

            if(isPositonSnake(now_position_y, now_position_x)==true){
                System.out.println("[뱀 발견] " + "y 좌표 : " + now_position_y + ", x 좌표 : " + now_position_x+ "   ---->   ");
                if(now_position_y!=1 && now_position_x-1==0){ // 첫번째 행을 제외하고 맨 왼쪽 열에서 뱀을 탈 경우
                    now_position_y --;
                    now_position_x = len;
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
        } // while문 종료

        System.out.println("--------------게임 종료--------------");
        System.out.println("[게임 결과]");

        System.out.println("주사위 굴린 횟수 : " + cnt);
    }

    public static void makeSnakePosition(){ // 뱀 위치 랜덤 생성 함수
        for(int i=0; i<numberSnake; i++){ // 총 공간에서 5%의 뱀을 생성
            int n = (int) (Math.random()*len*len+1); // 1 ~  (가로*세로) 사이의 무작위 값

            if(snake_position[n]==1 || snake_position[n]==2){
                i--;
            } else {
                if(n<=1){ // 뱀이 1,1에 있는 경우
                    snake_position[n] = 1;
                    snake_position[n+1] = 2;
                } else {
                    snake_position[n] = 1;
                    snake_position[n-1] = 2;
                    snake_position[n+1] = 2;
                }
            }
        }
    }

    public static void makeLadderPosition(){ // 사다리 위치 랜덤 생성 함수
        for(int i=0; i<numberLadder; i++){ // 총 공간에서 5%의 사다리를 생성
            int n = (int) (Math.random()*len*len+1); // 1 ~  (가로*세로) 사이의 무작위 값

            if(snake_position[n]==1 || ladder_position[n]==1 || ladder_position[n]==2){
                i--;
            } else {
                if(n<=len) { // 가장 아래에 있는 행에 사다리 생성
                    ladder_position[n] = 1;
                    ladder_position[n + len] = 2;
                } else if(n>(len-1)*len){ // 가장 위에 있는 행에 사다리 생성
                    i--;
                } else {
                    ladder_position[n] = 1;
                    ladder_position[n+len] = 2;
                    ladder_position[n-len] = 2;
                }
            }
        }
    }

    public static boolean isPositonSnake(int y, int x){ // 해당 위치에 뱀이 있는 지 확인하는 함수
        int tmp = (y-1)*len + x;
        if(snake_position[tmp]==1){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPositionladder(int y, int x){ // 해당 위치에 사다리가 있는지 확인하는 함수
        int tmp = (y-1)*len + x;
        if(ladder_position[tmp]==1){
            return true;
        } else {
            return false;
        }
    }
}
