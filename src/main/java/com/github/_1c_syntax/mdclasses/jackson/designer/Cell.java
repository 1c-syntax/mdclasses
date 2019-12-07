

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class Cell {

    protected BigDecimal f;
    protected LocalStringType tl;
    protected String t;
    protected String parameter;
    protected Object v;
    protected String detailParameter;
    protected Object d;
    protected BigDecimal r;
    protected Drawing note;
    protected byte[] control;
    protected String pictureParameter;


}
