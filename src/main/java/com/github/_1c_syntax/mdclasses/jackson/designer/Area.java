

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class Area {

    @JsonProperty(required = true)
    protected SpreadsheetDocumentCellAreaType type;
    protected BigDecimal beginRow;
    protected BigDecimal endRow;
    protected BigDecimal beginColumn;
    protected BigDecimal endColumn;
    protected String columnsID;


}
