package code;
import java.io.*;
import java.util.*;

public class Sizeyunsuan {

    //main函数
    public static void main(String[] args)throws FileNotFoundException ,IOException,NullPointerException {
        File file1=new File("D:\\四则运算中级作业\\Exercises.txt");
        File file2=new File("D:\\四则运算中级作业\\Answers.txt");
        File file3=new File("D:\\四则运算中级作业\\MyAnswers.txt");
        File file4=new File("D:\\四则运算中级作业\\Grade.txt");
        Scanner sr = new Scanner(System.in);
        System.out.println("输入生成运算题目个数:");
        int n = sr.nextInt();
        System.out.println("请输入运算数的数值范围");
        int m=sr.nextInt();
        String str []=new  String[n] ;
        String an[] = new String[n] ;
        start:
        for(int h=0;h<n;) {          //生成符合要求的算数表达式及答案
            str[h]= creatExp(m);
            String s = " ";
            h++;
            boolean z = false;
            for (int j = 0; j < h - 1; j++) {
                z = str[h - 1].equals(str[j]);
                if (z) {
                    h--;  continue start;
                }
            }
            s= exchange(str[h-1]);
            an[h-1]= jiSuan(s);
            int p = str[h-1].indexOf("'");
            int q = an[h-1].indexOf("'");
            int j = an[h-1].indexOf("-");
            if(p!=-1||q!=-1||j!=-1){
                h--;  continue start;
            }
            boolean flag=false;            //判断答案是否为整数
            if(an[h-1].indexOf("/")!=-1)
                flag=true;
            int a=1,b=1;
            if(flag==false){          //答案为整数，判断是否在要求数值范围内
                a=Integer.parseInt(an[h-1].trim());
            }
            if(flag==true){            //答案为分数，分子分母分开分别判断是否在数值要求范围内
                int len=an[h-1].indexOf("/");
                a=Integer.parseInt(an[h-1].substring(0, len));
                b=Integer.parseInt(an[h-1].substring(len+1, an[h-1].length()));
            }
            if(a>m||b>m){
                h--;  continue start;
            }
        }
        FileOutputStream f1 = new FileOutputStream(file1);
        FileOutputStream f2 = new FileOutputStream(file2);
        for(int i=0;i<n;i++){       //将表达式和答案分别写入两个文件中
            String fstr;
            fstr = i+1+". "+str[i]+"\r\n";
            f1.write(fstr.getBytes("GBK"));
            fstr=i+1+". " + an[i]+"\r\n";
            f2.write(fstr.getBytes("GBK"));
        }
        System.out.println("请输入指令：");
        while(sr.hasNext()){
            switch(sr.next()){
                case "g":
                    file4.delete();
                    fileNew(file2, file3, file4);            //生成Grade文件
                    break;
                case "e":
                    break;
            }
            break;
        }
    }


    //随机生成四个运算符
    public static String getChar()
    {
        Random r = new Random();
        int i = r.nextInt(4);
        String op=null;
        switch(i)
        {
            case 0:op="+";break;
            case 1:op="-";break;
            case 2:op="*";break;
            case 3:op="÷";break;
        }
        return op;
    }


    //是否生成括号
    public static boolean getBracket()
    {
        boolean bk=false;
        Random r=new Random();
        if(r.nextInt(2)<1)
            bk=true;
        return bk;
    }


    //约分分数
    public static String yueFen(int a, int b)
    {
        String s="";
        int gys=1,c;
        c=a/b;
        a=a%b;
        if(c<0){
            a=a*-1;
        }
        for (int i = 1; i <= a; i++) {            //最小公约数
            if (a % i == 0 && b % i == 0) {
                gys = i;
            }
        }
        a=a/gys;            //最简分数
        b=b/gys;
        if(a==0){
            s=Integer.toString(c);
        }else if(c==0){
            s=Integer.toString(a)+"/"+Integer.toString(b);             //真分数
        }else{
            s=Integer.toString(c)+"'"+Integer.toString(a)+"/"+Integer.toString(b);    //带分数
        }
        return s;
    }


    //随机生成数字（整数或分数）
    public static String cetNumber(int m)
    {
        String s="";
        Random rd=new Random();
        switch(rd.nextInt(2)){        //整数或分数
            case 0:
                s=Integer.toString(rd.nextInt(m-1)+1);        //整数
                break;
            case 1:       //分数
                int a,b;
                a=rd.nextInt(m-1)+1;
                b=rd.nextInt(m-1)+1;
                s= yueFen(a,b);     //约分分数
                break;
        }
        return s;
    }


    //两个运算符的2种加括号的情况
    /*
        0. (a+b)+c
        1. a+(b+c)
     */
    public static String two(int m)
    {
        String result=null;
        Random r = new Random();
        int flag=r.nextInt(2);
        switch(flag)
        {
            case 0:      //前两个数字加括号
                result= "("+ cetNumber(m)+ getChar()+ cetNumber(m)+")"+ getChar()+ cetNumber(m);break;
            case 1:      //后两个数字加括号
                result= cetNumber(m)+ getChar()+"("+ cetNumber(m)+ getChar()+ cetNumber(m)+")";break;
        }
        return result;
    }


    //3个运算符的5种加括号情况
    /*
       0. (a+b)+c+d
       1. (a+b+c)+d
       2. a+(b+c)+d
       3. a+(b+c+d)
       4. a+b+(c+d)
    */
    public static String three(int m)
    {
        String result=null;
        Random r = new Random();
        int flag=r.nextInt(5);
        switch(flag)
        {
            case 0:
                result= "("+ cetNumber(m)+ getChar()+ cetNumber(m)+")"+ getChar()+ cetNumber(m)+ getChar()+ cetNumber(m);break;
            case 1:
                result="("+ cetNumber(m)+ getChar()+ cetNumber(m)+ getChar()+ cetNumber(m)+")"+ getChar()+ cetNumber(m);break;
            case 2:
                result= cetNumber(m)+ getChar()+"("+ cetNumber(m)+ getChar()+ cetNumber(m)+")"+ getChar()+ cetNumber(m);break;
            case 3:
                result= cetNumber(m)+ getChar()+"("+ cetNumber(m)+ getChar()+ cetNumber(m)+ getChar()+ cetNumber(m)+")";break;
            case 4:
                result= cetNumber(m)+ getChar()+ cetNumber(m)+ getChar()+"("+ cetNumber(m)+ getChar()+ cetNumber(m)+")";break;
        }
        return result;
    }


    //生成四则运算表达式
    public static String creatExp(int m)
    {
        String result = "";          //表达式
        Random r1=new Random();       //用于判断是否生成括号
        Random r2=new Random();       //用于判断生成运算符个数
        int t1=r1.nextInt(2);
        int t2=r1.nextInt(3);
        boolean flag=false;           //是否生成括号
        if(t1>0)
            flag= getBracket();
        if(flag==true)               //生成括号
        {
            switch(t2)
            {
                case 0:
                    result= cetNumber(m)+ getChar()+ cetNumber(m);break;
                case 1:
                    result= two(m);break;
                case 2:
                    result= three(m);break;
            }
        }else      //没有括号
        {
            switch(t2)
            {
                case 0:
                    result= cetNumber(m)+ getChar()+ cetNumber(m);break;
                case 1:
                    result= cetNumber(m)+ getChar()+ cetNumber(m)+ getChar()+ cetNumber(m);break;
                case 2:
                    result= cetNumber(m)+ getChar()+ cetNumber(m)+ getChar()+ cetNumber(m)+ getChar()+ cetNumber(m);break;
            }
        }

        return result;
    }


    //中缀转后缀表达式
    public static String exchange(String result) {
        Stack<Character> s = new Stack<Character>();        // 创建操作符堆栈
        String suffix = "";             // 存入后缀表达式
        int length = result.length();            // 计算中缀表达式长度
        for (int i = 0; i < length; i++) {
            char temp;
            char ch = result.charAt(i);          // 获取每个字符
            switch (ch) {
                case '(':
                    s.push(ch);
                    break;
                case '+':
                case '-':
                    suffix += " ";
                    while (s.size() != 0) {
                        temp = s.pop();
                        if (temp == '(') {
                            s.push('(');
                            break;
                        }
                        suffix += temp;
                        suffix += " ";
                    }
                    s.push(ch);
                    break;
                case '*':
                case '÷':
                    suffix += " ";
                    while (s.size() != 0) {
                        temp = s.pop();
                        if (temp == '+' || temp == '-' || temp == '(') {
                            s.push(temp);
                            break;
                        } else {
                            suffix += temp;
                            suffix += " ";
                        }
                    }
                    s.push(ch);
                    break;
                case ')':
                    while (!s.isEmpty()) {
                        temp = s.pop();
                        if (temp == '(') {
                            break;
                        } else {
                            suffix += " ";
                            suffix += temp;
                        }
                    }
                    break;
                default:
                    suffix += ch;
                    break;
            }
        }
        while (s.size() != 0) {
            suffix += " ";
            suffix += s.pop();
        }
        return suffix;
    }


    //计算后缀表达式
    public static String jiSuan(String exp) {
        String[] strings = exp.split(" ");
        Stack<String> stack = new Stack<String>();            //创建操作数栈
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("+") || strings[i].equals("-") || strings[i].equals("*") || strings[i].equals("÷")) {
                String y = stack.pop();
                String x = stack.pop();
                String rus = calculate(x,y,strings[i]);            //调用自定义四则运算
                stack.push(rus);
            }
            else {
                stack.push(strings[i]);
            }
        }
        return stack.pop();
    }


    //自定义四则运算
    public static String calculate(String x, String y, String ch) {
        String rus="";
        boolean flag1=false,flag2=false;         //判断是否为参数
        if(x.indexOf("/")!=-1)
            flag1=true;
        if(y.indexOf("/")!=-1)
            flag2=true;
        int a=1,b=1,c=1,d=1,f1=0,f2=0;
        if(flag1==false&&flag2==false){          //1.都为整数
            a=Integer.parseInt(x.trim());
            b=Integer.parseInt(y.trim());
        }
        if(flag1==false&&flag2==true){            //2.x整数 y分数
            a=Integer.parseInt(x.trim());
            int lenf2=y.indexOf("'");             //判断是否为带分数
            if(lenf2!=-1){
                f2=Integer.parseInt(y.substring(0,lenf2));        //提取带分数前面整数
            }
            int len=y.indexOf("/");             //提取分号前后整数
            b=Integer.parseInt(y.substring(lenf2+1, len));
            d=Integer.parseInt(y.substring(len+1, y.length()));
            if(f2<0){          //判断是否为负数
                b=f2*d-b;
            }else{
                b=f2*d+b;
            }
        }
        if(flag1==true&&flag2==false){               //3.x分数 y整数
            int lenf1=x.indexOf("'");
            if(lenf1!=-1){                    //分数处理同上
                f1=Integer.parseInt(x.substring(0,lenf1));
            }
            int len=x.indexOf("/");
            a=Integer.parseInt(x.substring(lenf1+1, len));
            c=Integer.parseInt(x.substring(len+1, x.length()));
            if(f1<0){
                a=f1*c-a;
            }else{
                a=f1*c+a;
            }

            b=Integer.parseInt(y.trim());
        }
        if(flag1==true&&flag2==true){              //4.均为分数，分数处理同上
            int lenf1=x.indexOf("'");
            if(lenf1!=-1){
                f1=Integer.parseInt(x.substring(0,lenf1));
            }
            int len1=x.indexOf("/");
            a=Integer.parseInt(x.substring(lenf1+1, len1));
            c=Integer.parseInt(x.substring(len1+1, x.length()));
            if(f1<0){
                a=f1*c-a;
            }else{
                a=f1*c+a;
            }
            int lenf2=y.indexOf("'");
            if(lenf2!=-1){
                f2=Integer.parseInt(y.substring(0,lenf2));
            }
            int len2=y.indexOf("/");
            b=Integer.parseInt(y.substring(lenf2+1, len2));
            d=Integer.parseInt(y.substring(len2+1, y.length()));
            if(f2<0){
                b=f2*d-b;
            }else{
                b=f2*d+b;
            }
        }
        switch(ch){            //对提取的整数进行运算并约分
            case "+":
                rus= yueFen(a*d+c*b, c*d) ;
                break;
            case "-":
                rus= yueFen(a*d-c*b, c*d);
                break;
            case "*":
                rus= yueFen(a*b, c*d);
                break;
            case "÷":
                if(c*b==0){
                    rus="无解";
                    break;
                }else{
                    rus= yueFen(a*d, c*b);
                }
                break;
        }
        return rus;
    }


    //比较答案
    static List<String> exp =new ArrayList<String>();
    static List<String> ans =new ArrayList<String>();
    public static List checkAnswer(){                      //先比较答案是否相同
        File file1 = new File("D:\\四则运算中级作业\\Exercises.txt");
        File file2 = new File("D:\\四则运算中级作业\\Answers.txt");
        List<String> find=new ArrayList<String>();         //存重复表达式信息
        try{                     //题目与答案扫入list表中
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            String s=null;
            while((s = br1.readLine())!=null){
                s=s.substring(s.indexOf(":")+1,s.length());
                exp.add(s);
            }

            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            while((s = br2.readLine())!=null){
                s=s.substring(s.indexOf(":")+1,s.length());
                ans.add(s);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<ans.size()-1;i++){             //冒泡排序法查找相同答案
            String s="";
            for(int j=i+1;j<ans.size();j++){
                if(ans.get(i).equals(ans.get(j))){
                    if(checkExp(exp.get(i),exp.get(j)))
                        s+=(i+1)+","+exp.get(i)+" Repeat "+(j+1)+","+exp.get(j)+"  ";
                }
            }
            if(s.length()>0)
                find.add(s);
        }
        return find;
    }


    //中缀转后缀，比较两表达式字符
    public static boolean checkExp(String exp1, String exp2){
        exp1= exchange(exp1) ;
        exp2= exchange(exp2) ;
        String[] strings = exp1.split(" ");
        for(int i=0;i<strings.length;i++){
            if(exp2.indexOf(strings[i])==-1)
                return false;
        }
        return true;
    }


    //文件写入
    public static void fWrite(File file, String s){
        try {
            FileWriter  out=new FileWriter (file,true);
            BufferedWriter bw= new BufferedWriter(out);
            bw.write(s);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //比较结果并写入另一文件
    public static void fileNew(File file1, File file2, File file3){
        List<String> correct =new ArrayList<String>();
        List<String> wrong =new ArrayList<String>();
        try{
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            int i=1;
            String s = null;
            while((s = br1.readLine())!=null){
                if(s.equals(br2.readLine())){
                    correct.add(Integer.toString(i));
                }else{
                    wrong.add(Integer.toString(i));
                }
                i++;
            }
            fWrite(file3, "Correct:"+correct.size()+"(");
            for(int j=0;j<correct.size();j++){
                if(j==correct.size()-1){
                    fWrite(file3, correct.get(j));
                }else{
                    fWrite(file3, correct.get(j)+",");
                }
            }
            fWrite(file3, ")"+"\r\n");

            fWrite(file3, "Wrong:"+wrong.size()+"(");
            for(int j=0;j<wrong.size();j++){
                if(j==wrong.size()-1){
                    fWrite(file3, wrong.get(j));
                }else{
                    fWrite(file3, wrong.get(j)+",");
                }
            }
            fWrite(file3, ")"+"\r\n");

            List<String> find=new ArrayList<String>();
            find= checkAnswer();
            fWrite(file3,"Repeat:"+find.size()+"\r\n");
            fWrite(file3,"RepeatDetail:"+"\r\n");
            for(int k=1;k<=find.size();k++){
                fWrite(file3,"("+k+") "+find.get(k-1)+"\r\n");
            }
            br1.close();
            br2.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
