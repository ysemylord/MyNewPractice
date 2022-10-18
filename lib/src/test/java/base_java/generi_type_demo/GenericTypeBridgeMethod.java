package base_java.generi_type_demo;

/**
 * 桥方法
 */
public class GenericTypeBridgeMethod {
    class Node<T> {


        public T data;

        public Node(T data) {
            this.data = data;
        }

        public void setData(T data) {
            System.out.println("Node.setData");
            this.data = data;
        }
    }

    class MyNode extends Node<Integer> {

        public MyNode(Integer data) {
            super(data);
        }


     /*反编译后会存在如下一个桥方法
       @Override
        public void setData(Object data) {
            super.setData(data);
        }*/

        public void setData(Integer data) {
            super.setData(data);
        }
    }


}
