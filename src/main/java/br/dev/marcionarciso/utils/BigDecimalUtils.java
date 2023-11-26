package br.dev.marcionarciso.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Locale.Builder;

public abstract class BigDecimalUtils {
	
	private static final String PADRAO_MONETARIO_BRASIL = "#,##0.00";
	private static final DecimalFormat FORMATADOR_MONETARIO = getDecimalFormat();
	
	private static DecimalFormat getDecimalFormat() {
		Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
		
		DecimalFormat df = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		
		df.applyPattern(PADRAO_MONETARIO_BRASIL);
		df.setParseBigDecimal(true);
		return df;
	}
    
    public static String formatarEmMoeda(BigDecimal valor) {
    	try {
    		return FORMATADOR_MONETARIO.format(valor);
    	} catch (Exception e) {
    		return null;
    	}
    }

	public static BigDecimal converterDeMoeda(String moeda) {
		try {
			return (BigDecimal) FORMATADOR_MONETARIO.parse(moeda);
		} catch (ParseException e) {
			return null;
		}
	}
    
}