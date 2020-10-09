package com.github._1c_syntax.mdclasses.mdo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XStreamAlias("v8:item")
public class MDODesignerSynonym {

    @XStreamAlias("v8:lang")
    private String language = "";
    @XStreamAlias("v8:content")
    private String content = "";
}
