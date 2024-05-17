package com.api.RecordTimeline.domain.member.domain;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public enum Interest {
    Marketing_Promotion,
    Accounting_Tax_Finance,
    GeneralAffairs_LegalAffairs_Affairs,
    IT_Data,
    Design,

    Service,
    Construction_Architecture,
    MedicalCare,
    Education,
    Media_Culture_Sports,
}
