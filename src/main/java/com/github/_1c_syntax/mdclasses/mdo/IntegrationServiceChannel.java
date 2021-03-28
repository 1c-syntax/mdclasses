package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.MessageDirection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class IntegrationServiceChannel extends MDObjectBase{

    /**
     * Направление сообщения
     */
    private MessageDirection messageDirection = MessageDirection.SEND;

    /**
     * Обработчик получения сообщения
     */
    private String receiveMessageProcessing = "";

    /**
     * Имя канала внешнего сервиса интеграции
     */
    private String externalIntegrationServiceChannelName = "";

    public IntegrationServiceChannel(DesignerMDO designerMDO) {
        super(designerMDO);
        messageDirection = designerMDO.getProperties().getMessageDirection();
        receiveMessageProcessing = designerMDO.getProperties().getReceiveMessageProcessing();
        externalIntegrationServiceChannelName = designerMDO.getProperties().getExternalIntegrationServiceChannelName();
    }

    @Override
    public MDOType getType() {
        return MDOType.INTEGRATION_SERVICE_CHANNEL;
    }
}
