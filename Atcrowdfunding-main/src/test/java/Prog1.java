public class Prog1 {
    /**
     * 题目：古典问题：有一对兔子，
     * 从出生后第3个月起每个月都生一对兔子，
     * 小兔子长到第三个月后每个月又生一对兔子，
     * 假如兔子都不死，问每个月的兔子对数为多少？
     程序分析： 兔子的规律为数列1,1,2,3,5,8,13,21....
     */
    public static void main(String[] args) {
        int month=10;
        System.out.println("每个月的["+month+"]兔子对数:"+fun(month));

    }

    private static   int fun(int month) {
        if (month==1||month==2) {
            return 1;
        }
        return fun(month-1)+fun(month-2);
    }
}
