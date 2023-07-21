package com.luan.bnkng.utils;

/**
 *
 * @author luanp
 */
public abstract class Formatter {
    /**
     * Removes dots and dashes of the CPF number.
     * @param cpf the number of CPF unformatted
     * @return the formatted CPF with numbers only
     */
    public static String formatCpf(String cpf){
        return cpf.replaceAll("[^0-9]", "");
    }
}
