

import java.io.InputStream;
import java.util.Scanner;

public class Test04 {

    public static void main(String[] args) {
        //注意字符串和任意字符拼接都是字符串
        //如果任何字符串和字符串进行拼接也是字符串
        // 注意只不过先计算任意字符结果在和字符串进行拼接
      /*  System.out.println(5+5+"=5+5");//5+5=55
        System.out.println(5+5+"=5+5");//10=5+5
        System.out.println('a'+1);//98
        System.out.println('a');//97
        System.out.println("hello"+'a'+1);//hella1
        System.out.println('a'+1+"hello");//98hello*/
        //System.out.println(2<<3);//16
        int a = 1;
        int b = 2;
       /* a=b+a;//3
        b=a-b;//2
        a=a-b;//1
        System.out.println("a:"+a+" b:"+b);//a=2 b=1*/
       /* a=a^b;
        b=a^b;
        a=a^b;
        System.out.println("a:"+a+" b:"+b);//a=2 b=1
*/

       /* System.out.println("键盘中输入的第一个数字");
        Scanner sc1=new Scanner(System.in);
        int x = sc1.nextInt();
        System.out.println("键盘中输入的第二个数字");
        Scanner sc2=new Scanner(System.in);
        int y = sc2.nextInt();
        int sum=y+x;
        System.out.println(sum);*/
        /*System.out.println("键盘中输入的数字");
        Scanner scanner=new Scanner(System.in);
        int x=scanner.nextInt();
        if (x==65) {
            System.out.println("A");
        }else if (x==66){
            System.out.println("B");
        }else if (x==67){
            System.out.println("C");
        }else if (x==68){
            System.out.println("D");
        }
*/
       /* char ch='D';
        switch(ch){
            case 'A':
                System.out.println("你错了");
                break;
            case 'B':
                System.out.println("你错了");
                break;
            case 'D':
                System.out.println("你错了");
                break;
            case 'C':
                System.out.println("你对了");
                break;
            default:
                System.out.println("恭喜你，没有这个答案");


        }*/
       /* System.out.println("请输入月份：\n");
        Scanner scanner = new Scanner(System.in);
        int month=scanner.nextInt();
        switch(month){
            case 3:
            case 4:
            case 5:
                System.out.println("该月份为春天");
                break;
            case 6:
            case 7:
            case 8:
                System.out.println("该月份为夏天");
                break;
            case 9:
            case 10:
            case 11:
                System.out.println("该月份为秋天");
                break;
            case 12:
            case 1:
            case 2:
                System.out.println("该月份为冬天");
                break;
            default:
                System.out.println("没有该季节");
        }*/
      /* for (int j=1;j<5;j++){
           for (int i=1;i<=j;i++){
               System.out.print("*");
           }
           System.out.println();
       }*/
      /*for (int line=1;line<=9;line++){
          for (int innerline=1;innerline<line;innerline++){
              System.out.print(innerline+"*"+line+"="+innerline*line+"\t");
          }
          System.out.println();
      }*/
        //break使用
     /* for(int i=1;i<=6;i++){
          System.out.println("你好！");
          for (int j=1;j<=i;j++){
              System.out.println("世界你好！");
          }
          break;//中断循环（退出当前循环）
      }
        System.out.println("===============");
    }*/
        for(int i=1;i<=6;i++){
            System.out.println("你好！");
            for (int j=1;j<=i;j++){
                System.out.println("世界你好！");
            }
            //continue;//继续（退出本次循环）
        }
        System.out.println("===============");
    }


}
