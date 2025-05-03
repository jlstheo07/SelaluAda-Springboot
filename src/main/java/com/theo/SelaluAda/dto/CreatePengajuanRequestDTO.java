package com.theo.SelaluAda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePengajuanRequestDTO {
    private int amount;
    private Integer tenor; // dalam bulan, misalnya 12
}