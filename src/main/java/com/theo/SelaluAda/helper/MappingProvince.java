package com.theo.SelaluAda.helper;

import com.theo.SelaluAda.enums.ProvinceToBranch;

import java.util.Set;

public class MappingProvince {
    public static ProvinceToBranch getAreaByProvince(String province) {
        Set<String> branchJawa = Set.of(
                "DKI Jakarta", "Jawa Barat", "Jawa Tengah", "DI Yogyakarta", "Jawa Timur", "Banten"
        );

        Set<String> branchSumatra = Set.of(
                "Aceh", "Sumatera Utara", "Sumatera Barat", "Riau", "Kepulauan Riau", "Jambi",
                "Sumatera Selatan", "Bengkulu", "Lampung", "Bangka Belitung"
        );

        Set<String> branchKalimantanNusaTenggara = Set.of(
                "Kalimantan Barat", "Kalimantan Tengah", "Kalimantan Selatan", "Kalimantan Timur", "Kalimantan Utara",
                "Nusa Tenggara Barat", "Nusa Tenggara Timur"
        );

        Set<String> branchSulawesiPapua = Set.of(
                "Sulawesi Utara", "Gorontalo", "Sulawesi Tengah", "Sulawesi Barat", "Sulawesi Selatan", "Sulawesi Tenggara",
                "Papua", "Papua Barat", "Papua Tengah", "Papua Pegunungan", "Papua Selatan", "Papua Barat Daya"
        );


        if (branchJawa.contains(province)) return ProvinceToBranch.JAWA;
        if (branchSumatra.contains(province)) return ProvinceToBranch.SUMATRA;
        if (branchKalimantanNusaTenggara.contains(province)) return ProvinceToBranch.KALIMANTAN_NUSA_TENGGARA;
        if (branchSulawesiPapua.contains(province)) return ProvinceToBranch.SULAWESI_PAPUA;


        throw new IllegalArgumentException("Provinsi tidak dikenali: " + province);
    }

}
