

package com.github._1c_syntax.mdclasses.jackson.designer;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PrintSettings {

    protected PageOrientation pageOrientation;
    protected BigDecimal scale;
    protected Boolean collate;
    protected BigDecimal copies;
    protected BigDecimal perPage;
    protected BigDecimal topMargin;
    protected BigDecimal leftMargin;
    protected BigDecimal bottomMargin;
    protected BigDecimal rightMargin;
    protected BigDecimal headerSize;
    protected BigDecimal footerSize;
    protected Boolean fitToPage;
    protected Boolean blackAndWhite;
    protected String printerName;
    protected BigDecimal paper;
    protected String pageSize;
    protected BigDecimal paperSource;
    protected BigDecimal pageWidth;
    protected BigDecimal pageHeight;
    protected PrintAccuracy printAccuracy;
    protected DuplexPrintingType duplexType;
    protected PagePlacementAlternation pagePlacementAlternation;

}
