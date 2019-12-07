

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class DimensionTableProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("NameInDataSource")
    protected String nameInDataSource;
    @JsonProperty("PresentationField")
    protected String presentationField;
    @JsonProperty("HierarchyNameInDataSource")
    protected String hierarchyNameInDataSource;
    @JsonProperty("LevelNumber")
    protected BigDecimal levelNumber;
    @JsonProperty("Hierarchical")
    protected boolean hierarchical;
    @JsonProperty("UnfilledParentValue")
    protected Object unfilledParentValue;
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("QuickChoice")
    protected boolean quickChoice;
    @JsonProperty("DefaultObjectForm")
    protected String defaultObjectForm;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("DefaultChoiceForm")
    protected String defaultChoiceForm;
    @JsonProperty("ObjectPresentation")
    protected LocalStringType objectPresentation;
    @JsonProperty("ExtendedObjectPresentation")
    protected LocalStringType extendedObjectPresentation;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;

}
