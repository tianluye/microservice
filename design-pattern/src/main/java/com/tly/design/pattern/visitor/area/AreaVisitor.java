package com.tly.design.pattern.visitor.area;

import com.tly.design.pattern.visitor.master.Circle;
import com.tly.design.pattern.visitor.IVisitor;
import com.tly.design.pattern.visitor.master.Rectangle;

/**
 *
 */
public class AreaVisitor implements IVisitor {

    public AreaVisitor() {

    }

    @Override
    public void visitor(Rectangle rectangle) {
        int area = rectangle.getX() * rectangle.getY();
        System.out.println("长方形计算面积结束...面积为: " + area);
    }

    @Override
    public void visitor(Circle circle) {
        double area = circle.getHalfLong() * circle.getHalfLong() * 3.14;
        System.out.println("圆形计算面积结束...面积为: " + area);
    }
}
