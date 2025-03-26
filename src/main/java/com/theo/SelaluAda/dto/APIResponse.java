package com.theo.SelaluAda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Tambahkan <T> agar bisa menampung berbagai jenis data
public class APIResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
