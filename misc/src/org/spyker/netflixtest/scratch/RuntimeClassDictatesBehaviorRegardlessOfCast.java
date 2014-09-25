package org.spyker.netflixtest.scratch;

public class RuntimeClassDictatesBehaviorRegardlessOfCast {

    public static void main(String[] args) {
        new RuntimeClassDictatesBehaviorRegardlessOfCast().doit();
    }
        
    public void doit() {
        Shape s1 = new Shape();
        s1.sayShape();
        Shape s2 = new Triangle();
        s2.sayShape();
        ((Shape)s2).sayShape();
        Shape s3 = new Square();
        s3.sayShape();
    }
    
    class Shape {
        public void sayShape() {
            System.out.println("I'm a shape!!");
        }
    }
    
    class Triangle extends Shape {
        @Override
        public void sayShape() {
            System.out.println("I'm a triangle!!");
        }
    }

    class Square extends Shape {
        @Override
        public void sayShape() {
            super.sayShape();
            System.out.println("Actually, I lied, I'm a square!!");
        }
    }
}
