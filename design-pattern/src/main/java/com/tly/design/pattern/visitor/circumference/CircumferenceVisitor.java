package com.tly.design.pattern.visitor.circumference;

import com.tly.design.pattern.visitor.IVisitor;
import com.tly.design.pattern.visitor.master.Circle;
import com.tly.design.pattern.visitor.master.Rectangle;

/**
 *
 */
public class CircumferenceVisitor implements IVisitor {

    @Override
    public void visitor(Rectangle rectangle) {
        int circumference = 2 * (rectangle.getX() + rectangle.getY());
        System.out.println("长方形计算周长结束...周长为: " + circumference);
    }

    @Override
    public void visitor(Circle circle) {
        double circumference = 2 * circle.getHalfLong() * 3.14;
        System.out.println("圆形计算周长结束...周长为: " + circumference);
    }
}
