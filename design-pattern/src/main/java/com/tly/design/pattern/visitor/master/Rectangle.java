package com.tly.design.pattern.visitor.master;

import com.tly.design.pattern.visitor.IVisitor;
import com.tly.design.pattern.visitor.Shape;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Rectangle implements Shape {

    private Integer x;

    private Integer y;

    @Override
    public void visitor(IVisitor visitor) {
        visitor.visitor(this);
    }
}
