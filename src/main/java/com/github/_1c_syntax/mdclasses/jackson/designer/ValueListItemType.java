

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ValueListItemType {

    @JsonProperty(required = true)
    protected Object value;
    protected String presentation;
    protected BigDecimal checkState;
    protected Object picture;
    protected Long id;
    protected Boolean formatPresentationSpecified;
    protected String formatPresentation;
}
