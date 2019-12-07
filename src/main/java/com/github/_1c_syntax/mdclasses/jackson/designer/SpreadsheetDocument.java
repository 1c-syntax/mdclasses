

package com.github._1c_syntax.mdclasses.jackson.designer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SpreadsheetDocument {

    protected String distributedKey;
//    protected LanguageSettings languageSettings;
    protected String languageCode;
    protected List<Columns> columns;
    protected List<RowsItem> rowsItem;
    protected List<Drawing> drawing;
    protected Object backPicture;
    protected Boolean fixedBackground;
    protected Cell leftHeader;
    protected Cell centerHeader;
    protected Cell rightHeader;
    protected Cell leftFooter;
    protected Cell centerFooter;
    protected Cell rightFooter;
    protected Boolean templateMode;
    protected SpreadsheetDocumentStepDirectionType stepDirection;
    protected Boolean totalsRight;
    protected Boolean totalsBelow;
    protected BigDecimal defaultFormatIndex;
    protected BigDecimal height;
    protected BigDecimal vgLevels;
    protected BigDecimal vgRows;
    protected List<GroupDS> vg;
    protected List<GroupDS> hg;
    protected List<PageBreak> verticalPageBreak;
    protected List<PageBreak> horizontalPageBreak;
    protected List<Merge> merge;
    protected List<Merge> verticalUnmerge;
    protected List<Merge> horizontalUnmerge;
    protected List<NamedItem> namedItem;
    protected String printSettingsName;
    protected PrintSettings printSettings;
    protected Area printArea;
    protected Area repeatRows;
    protected Area repeatColumns;
    protected List<DrawingsDataSource> drawingDataSource;
    protected List<String> groupsBackColor;
    protected List<String> groupsColor;
    protected List<String> headersBackColor;
    protected List<String> headersColor;
    protected Boolean saveViewSettings;
    protected ViewSettings viewSettings;
    protected List<EmbeddedTableItem> embeddedTable;
    protected List<Line> line;
    protected List<Font> font;
    protected List<Format> format;
    protected List<PictureDS> picture;
    protected String usedFileName;
    protected UseOutput output;
    protected BigDecimal nextInsertionRow;
    protected BigDecimal nextInsertionCol;
    protected Boolean readOnly;
    protected Boolean protection;
    protected SpreadsheetDocument baseSheet;
    protected SpreadsheetDocumentExtensionAlgorithm extensionMethod;


}
