

package com.github._1c_syntax.mdclasses.jackson.designer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class InputFieldData {

    protected List<Event> event;
    @JsonProperty(required = true)
    protected Object minVal;
    @JsonProperty(required = true)
    protected Object maxVal;
    protected Picture choiceBtnPic;
    @JsonProperty(required = true)
    protected String choiceFormId;
    protected ChoiceParameterLinksMAC choiceParamLinks;
    protected ChoiceParameters choiceParams;
    protected TypeDescription avlTypes;
    @JsonProperty(required = true)
    protected ValueListType valList;
    protected String txtClr;
    protected String bkClr;
    protected String brdClr;
    protected Font fnt;
    protected TypeLinkMAC linkByType;
    @JsonProperty("width")
    protected BigDecimal width;
    @JsonProperty("rowsCount")
    protected BigDecimal rowsCount;
    @JsonProperty("horStretch")
    protected BWAValue horStretch;
    @JsonProperty("verStretch")
    protected BWAValue verStretch;
    @JsonProperty("autoMaxWidth")
    protected Boolean autoMaxWidth;
    @JsonProperty("maxWidth")
    protected BigDecimal maxWidth;
    @JsonProperty("minWidth")
    protected BigDecimal minWidth;
    @JsonProperty("autoMaxHeight")
    protected Boolean autoMaxHeight;
    @JsonProperty("maxHeight")
    protected BigDecimal maxHeight;
    @JsonProperty("wrap")
    protected Boolean wrap;
    @JsonProperty("psw")
    protected BWAValue psw;
    @JsonProperty("mlt")
    protected BWAValue mlt;
    @JsonProperty("extEdit")
    protected BWAValue extEdit;
    @JsonProperty("markNeg")
    protected BWAValue markNeg;
    @JsonProperty("choiceListBtn")
    protected BWAValue choiceListBtn;
    @JsonProperty("dropListBtn")
    protected BWAValue dropListBtn;
    @JsonProperty("choiceBtn")
    protected BWAValue choiceBtn;
    @JsonProperty("choiceBtnRepresentation")
    protected ChoiceButtonRepresentation choiceBtnRepresentation;
    @JsonProperty("clearBtn")
    protected BWAValue clearBtn;
    @JsonProperty("spinBtn")
    protected BWAValue spinBtn;
    @JsonProperty("openBtn")
    protected BWAValue openBtn;
    @JsonProperty("createBtn")
    protected BWAValue createBtn;
    @JsonProperty("mask")
    protected String mask;
    @JsonProperty("listChoice")
    protected Boolean listChoice;
    @JsonProperty("choiceListHeight")
    protected BigDecimal choiceListHeight;
    @JsonProperty("choiceListMinWidth")
    protected BigDecimal choiceListMinWidth;
    @JsonProperty("autoChoice")
    protected BWAValue autoChoice;
    @JsonProperty("quickChoice")
    protected BWAValue quickChoice;
    @JsonProperty("choice")
    protected FoldersAndItems choice;
    @JsonProperty("format")
    protected String format;
    @JsonProperty("inputFormat")
    protected String inputFormat;
    @JsonProperty("autoMark")
    protected BWAValue autoMark;
    @JsonProperty("choiceType")
    protected Boolean choiceType;
    @JsonProperty("incompleteChoice")
    protected IncompleteItemChoiceMode incompleteChoice;
    @JsonProperty("typeDomainEnabled")
    protected Boolean typeDomainEnabled;
    @JsonProperty("textEdit")
    protected Boolean textEdit;
    @JsonProperty("editTextUpdateMode")
    protected EditTextUpdate editTextUpdateMode;
    @JsonProperty("inputHint")
    protected String inputHint;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;
    @JsonProperty("choiceHistory")
    protected ChoiceHistoryOnInput choiceHistory;
    @JsonProperty("autoShowClearButtonMode")
    protected AutoShowClearButtonMode autoShowClearButtonMode;
    @JsonProperty("autoShowOpenButtonMode")
    protected AutoShowOpenButtonMode autoShowOpenButtonMode;
    @JsonProperty("autoCorrectionOnTextInput")
    protected AutoCorrectionOnTextInput autoCorrectionOnTextInput;
    @JsonProperty("spellCheckingOnTextInput")
    protected SpellCheckingOnTextInput spellCheckingOnTextInput;
    @JsonProperty("autoCapitalizationOnTextInput")
    protected AutoCapitalizationOnTextInput autoCapitalizationOnTextInput;
    @JsonProperty("specialTextInputMode")
    protected SpecialTextInputMode specialTextInputMode;
    @JsonProperty("onScreenKeyboardReturnKeyText")
    protected OnScreenKeyboardReturnKeyText onScreenKeyboardReturnKeyText;
    @JsonProperty("heightControlVariant")
    protected HeightControlVariant heightControlVariant;

}
