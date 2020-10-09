package com.github._1c_syntax.mdclasses.mdo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MDOSynonym {
    @XStreamAlias("key")
    private String language;
    @XStreamAlias("value")
    private String content;
}
