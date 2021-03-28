package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class IntegrationService extends MDObjectBSL {

    /**
     * Адрес внешнего сервиса интеграции
     */
    private String externalIntegrationServiceAddress = "";

    /**
     * Каналы сервиса интеграции
     */
    @XStreamImplicit(itemFieldName = "integrationServiceChannels")
    private List<IntegrationServiceChannel> integrationChannels = Collections.emptyList();

    public IntegrationService(DesignerMDO designerMDO) {
        super(designerMDO);
        var channels = new ArrayList<>(integrationChannels);
        designerMDO.getChildObjects().getIntegrationChannels().forEach((DesignerMDO channel) ->
                channels.add(new IntegrationServiceChannel(channel)));
        integrationChannels = channels;
        externalIntegrationServiceAddress = designerMDO.getProperties().getExternalIntegrationServiceAddress();
    }

    @Override
    public MDOType getType() {
        return MDOType.INTEGRATION_SERVICE;
    }
}
