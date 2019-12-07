

package com.github._1c_syntax.mdclasses.jackson.designer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UUIDAndStringsVectorType {

    @JsonProperty("UUID")
    protected String uuid;
    protected List<UserFavoriteItem> item;

}
