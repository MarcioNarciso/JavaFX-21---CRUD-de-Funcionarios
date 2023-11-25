package br.dev.marcionarciso.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public abstract class BigDecimalUtils {
    
    public static String formatarEmMoeda(BigDecimal valor) {
        return new DecimalFormat("R$ #,###.00").format(valor);
    }
    
}