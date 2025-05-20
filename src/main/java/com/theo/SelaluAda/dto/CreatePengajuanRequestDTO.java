package com.theo.SelaluAda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePengajuanRequestDTO {
    private int amount;
    private Integer tenor; // kelipatan 3 bulan = 3, 6, 9, 12
}