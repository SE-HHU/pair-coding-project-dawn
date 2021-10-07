package code;

import code.Sizeyunsuan;
import org.junit.Assert;
import org.junit.Test;

public class SizeyunsuanTest {
    Sizeyunsuan s = new Sizeyunsuan();
    //测试约分
    @Test
    public void testYueFen() throws Exception{
        Assert.assertEquals("2/3",s.yueFen(12,18));
    }
    //测试中缀转后缀
    @Test
    public void testExchange() throws Exception{
        Assert.assertEquals("1 2 +",s.exchange("1+2"));
        Assert.assertEquals("1 2 *",s.exchange("1*2"));
        Assert.assertEquals("1 2 + 3 ÷",s.exchange("(1+2)÷3"));
        Assert.assertEquals("1/3 1 2 + ÷",s.exchange("1/3÷(1+2)"));
        Assert.assertEquals("1 2 3 * +",s.exchange("1+2*3"));
        Assert.assertEquals("1 2 3 * + 4 -",s.exchange("1+2*3-4"));
        Assert.assertEquals("1 2 + 3 4 * +",s.exchange("1+2+3*4"));
        Assert.assertEquals("3 1 2 + * 4 -",s.exchange("3*(1+2)-4"));
        Assert.assertEquals("4 3 1 2 + * +",s.exchange("4+3*(1+2)"));
        Assert.assertEquals("4 1 2 + 3 * +",s.exchange("4+(1+2)*3"));
        Assert.assertEquals("1 2 + 1/3 4 + *",s.exchange("(1+2)*(1/3+4)"));
    }
    //测试后缀计算
    @Test
    public void testJiSuan() throws Exception{
        Assert.assertEquals("3",s.jiSuan("1 2 +"));
        Assert.assertEquals("2",s.jiSuan("1 2 *"));
        Assert.assertEquals("1",s.jiSuan("1 2 + 3 ÷"));
        Assert.assertEquals("1/9",s.jiSuan("1/3 1 2 + ÷"));
        Assert.assertEquals("7",s.jiSuan("1 2 3 * +"));
        Assert.assertEquals("3",s.jiSuan("1 2 3 * + 4 -"));
        Assert.assertEquals("15",s.jiSuan("1 2 + 3 4 * +"));
        Assert.assertEquals("5",s.jiSuan("3 1 2 + * 4 -"));
        Assert.assertEquals("13",s.jiSuan("4 3 1 2 + * +"));
        Assert.assertEquals("13",s.jiSuan("4 1 2 + 3 * +"));
        Assert.assertEquals("13",s.jiSuan("1 2 + 1/3 4 + *"));
    }
    //测试自定义四则运算
    @Test
    public void testcalculate() throws Exception{
        Assert.assertEquals("3",s.calculate("1","2","+"));
        Assert.assertEquals("1",s.calculate("2","1","-"));
        Assert.assertEquals("6",s.calculate("2","3","*"));
        Assert.assertEquals("2/3",s.calculate("2","3","÷"));
        Assert.assertEquals("1",s.calculate("1/2","1/2","+"));
        Assert.assertEquals("1/2",s.calculate("1","1/2","-"));
        Assert.assertEquals("1",s.calculate("1/2","2","*"));
        Assert.assertEquals("2",s.calculate("1","1/2","÷"));
    }
}