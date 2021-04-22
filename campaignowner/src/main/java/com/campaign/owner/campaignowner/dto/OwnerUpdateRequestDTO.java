package com.campaign.owner.campaignowner.dto;

import com.campaign.owner.campaignowner.constants.ErrorConstants;
import com.campaign.owner.campaignowner.constraint.EnumValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OwnerUpdateRequestDTO {
    private String contact;

    @NotEmpty(message = ErrorConstants.VALID_LIST_OF_CHANNELS)
    private List<@Valid String> channels;

    @EnumValidator(
            acceptedValues = ErrorConstants.ACTIVE_TERMINATED_SUSPENDED,
            enumClass = StateType.class,
            message =  ErrorConstants.VALID_STATE
    )
    private String state;


}
