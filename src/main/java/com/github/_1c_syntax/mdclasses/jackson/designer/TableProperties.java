

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TableProperties {

    @JsonProperty("Name")
    protected String name;
    @JsonProperty("Synonym")
    protected LocalStringType synonym;
    @JsonProperty("Comment")
    protected String comment;
    @JsonProperty("ObjectBelonging")
    protected ObjectBelonging objectBelonging;
    @JsonProperty("TableType")
    protected ExternalDataSourceTableType tableType;
    @JsonProperty("NameInDataSource")
    protected String nameInDataSource;
    @JsonProperty("ExpressionInDataSource")
    protected String expressionInDataSource;
    @JsonProperty("TableDataType")
    protected ExternalDataSourceTableDataType tableDataType;
    @JsonProperty("KeyFields")
    protected FieldList keyFields;
    @JsonProperty("PresentationField")
    protected String presentationField;
    @JsonProperty("ParentField")
    protected String parentField;
    @JsonProperty("UnfilledParentValue")
    protected Object unfilledParentValue;
    @JsonProperty("Characteristics")
    protected CharacteristicsDescriptions characteristics;
    @JsonProperty("UseStandardCommands")
    protected boolean useStandardCommands;
    @JsonProperty("QuickChoice")
    protected boolean quickChoice;
    @JsonProperty("InputByString")
    protected FieldList inputByString;
    @JsonProperty("CreateOnInput")
    protected CreateOnInput createOnInput;
    @JsonProperty("SearchStringModeOnInputByString")
    protected SearchStringModeOnInputByString searchStringModeOnInputByString;
    @JsonProperty("ChoiceDataGetModeOnInputByString")
    protected ChoiceDataGetModeOnInputByString choiceDataGetModeOnInputByString;
    @JsonProperty("ChoiceHistoryOnInput")
    protected ChoiceHistoryOnInput choiceHistoryOnInput;
    @JsonProperty("DefaultObjectForm")
    protected String defaultObjectForm;
    @JsonProperty("DefaultRecordForm")
    protected String defaultRecordForm;
    @JsonProperty("DefaultListForm")
    protected String defaultListForm;
    @JsonProperty("DefaultChoiceForm")
    protected String defaultChoiceForm;
    @JsonProperty("ObjectPresentation")
    protected LocalStringType objectPresentation;
    @JsonProperty("ExtendedObjectPresentation")
    protected LocalStringType extendedObjectPresentation;
    @JsonProperty("RecordPresentation")
    protected LocalStringType recordPresentation;
    @JsonProperty("ExtendedRecordPresentation")
    protected LocalStringType extendedRecordPresentation;
    @JsonProperty("ListPresentation")
    protected LocalStringType listPresentation;
    @JsonProperty("ExtendedListPresentation")
    protected LocalStringType extendedListPresentation;
    @JsonProperty("Explanation")
    protected LocalStringType explanation;
    @JsonProperty("ObjectModule")
    protected String objectModule;
    @JsonProperty("RecordSetModule")
    protected String recordSetModule;
    @JsonProperty("ManagerModule")
    protected String managerModule;
    @JsonProperty("IncludeHelpInContents")
    protected boolean includeHelpInContents;
    @JsonProperty("Help")
    protected String help;
    @JsonProperty("ReadOnly")
    protected boolean readOnly;
    @JsonProperty("TransactionsIsolationLevel")
    protected TransactionsIsolationLevel transactionsIsolationLevel;
    @JsonProperty("DataVersionField")
    protected String dataVersionField;
    @JsonProperty("EditType")
    protected EditType editType;
    @JsonProperty("BasedOn")
    protected MDListType basedOn;
    @JsonProperty("DataLockFields")
    protected FieldList dataLockFields;
    @JsonProperty("DataLockControlMode")
    protected DefaultDataLockControlMode dataLockControlMode;


}
