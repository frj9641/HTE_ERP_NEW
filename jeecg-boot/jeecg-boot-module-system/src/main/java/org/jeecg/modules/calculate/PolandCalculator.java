package org.jeecg.modules.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * 字符串算式计算器
 */
public class PolandCalculator {
    public static void main(String[] args) {

        //将此中缀表达式转换为后缀表达式，先将中缀表达式放入list里
        String expression = "1.3+((2.15+3.3)*4)-5.25";
        List list = changeToList(expression);
        System.out.println("中缀表达式为：" + list);
        List postfixExpression = postfixExpression(list);
        System.out.println("后缀表达式为：" + postfixExpression);

        //计算后缀表达式的结果
        System.out.println(calculator(postfixExpression));
    }

    /**
     * 计算方法接口
     *
     * @param exp 字符串算式
     * @return 计算结果
     */
    public static double calculate(String exp) {
        return calculator(postfixExpression(changeToList(exp)));
    }

    public static List<String> changeToList(String s) {

        int i = 0; //表示遍历string的指针
        List<String> ls = new ArrayList<String>();//定义一个新的List
        char c;//字符串的拼接放入到ch中,每遍历一个字符，就放到ch中
        String str;//多位数的拼接
        do {
            //如果是数字
            if ((c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57 || c == '.') {
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57 || c == '.') {
                    str += c;
                    i++;
                }
                ls.add(str);
            } else {
                ls.add(c + "");
                i++;
            }
        } while (i < s.length());
        return ls;
    }

    //将中缀表达式转换为后缀表达式
    public static List<String> postfixExpression(List<String> ls) {

        //初始化栈和List，运算符栈和储存中间结果的list
        Stack<String> s1 = new Stack<String>();
        List<String> s2 = new ArrayList<String>();

        //遍历ls
        for (String ietm : ls) {
            //如果是一个数，则入栈
            //正则匹配所有整数以及带小数点的小数
            if (ietm.matches("[1-9]\\d*\\.?\\d*")) {
                s2.add(ietm);
            } else if (ietm.equals("(")) {
                s1.push(ietm);
            } else if (ietm.equals(")")) {
                //如果是右括号，则一次弹出s1栈顶的运算符并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();  //将(弹出s1，消除小括号
            } else {
                //当ietm的优先级小于等于s1栈顶运算符，将s1栈顶的运算符弹出并压入s2，再次转到4.1中与
                //s1新的栈顶运算符相比较
                //缺少一个比较优先级高低的方法
                while (s1.size() != 0 && Oper.getValue(s1.peek()) >= Oper.getValue(ietm)) {
                    s2.add(s1.pop());
                }
                //还需要将ietm压入栈
                s1.push(ietm);
            }
        }
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        //输出s2，结果为后缀表达式
        return s2;
    }

    //计算后缀表达式的值
    //完成对逆波兰表达式的运算
    /*1.从左至右扫描，将3 4压栈
     * 2.遇到运算符，弹出4 3 ，计算4+3值，得7，将7入栈
     * 3.将5入栈
     * 4.X运算符，弹出5 7 计算7*5值，将35入栈
     * 5.将6入栈
     * 6.-运算符，计算35 - 6值，得到最终结果*/
    public static double calculator(List<String> ls) {
        // double res = 0;
        Stack<String> stack = new Stack<String>();
        for (String item : ls) {
            if (item.matches("[1-9]\\d*\\.?\\d*")) {
                stack.push(item);
            } else {
                //栈顶数据
                double num2 = Double.parseDouble(stack.pop());
                //次顶数据
                double num1 = Double.parseDouble(stack.pop());
                double res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                stack.push(res + "");
            }
        }
        double result = Double.parseDouble(stack.pop());

        return Double.valueOf(String.format("%.2f", result));

    }

}

//比较优先级的类
class Oper {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String s) {
        int res = 0;
        switch (s) {
            case "+":
                res = ADD;
                break;
            case "-":
                res = SUB;
                break;
            case "*":
                res = MUL;
                break;
            case "/":
                res = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;

        }
        return res;
    }


}

