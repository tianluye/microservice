package com.tly.design.pattern.visitor.master;

import com.tly.design.pattern.visitor.IVisitor;
import com.tly.design.pattern.visitor.Shape;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Circle implements Shape {

    private int halfLong;

    @Override
    public void visitor(IVisitor visitor) {
        visitor.visitor(this);
    }
}
