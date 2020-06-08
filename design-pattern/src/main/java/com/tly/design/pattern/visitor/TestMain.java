package com.tly.design.pattern.visitor;

import com.tly.design.pattern.visitor.area.AreaVisitor;
import com.tly.design.pattern.visitor.circumference.CircumferenceVisitor;
import com.tly.design.pattern.visitor.master.Rectangle;

public class TestMain {

    public static void main(String[] args) {
        Shape shape = new Rectangle(2, 3);
        IVisitor visitor = new AreaVisitor();
        shape.visitor(visitor);
        IVisitor visitor1 = new CircumferenceVisitor();
        shape.visitor(visitor1);
    }

}
