package 课程.listen07;

public class TestView {

    public static class TextContext {

    }

    public static class TestAttributeSet {

    }


    public TestView(TextContext context) {

    }

    public TestView(TextContext context, TestAttributeSet attributeSet) {

    }

    public TestView(TextContext context, TestAttributeSet attributeSet,int defAttributeSet) {

    }

    public static void main(String[] args) {
         MyTestView myTestView1 = new MyTestView(new TextContext());
         MyTestView myTestView2 = new MyTestView(new TextContext(),new TestAttributeSet());
         MyTestView myTestView3 = new MyTestView(new TextContext(),new TestAttributeSet(),0);
    }
}
