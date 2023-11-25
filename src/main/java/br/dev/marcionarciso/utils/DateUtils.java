package br.dev.marcionarciso.utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class DateUtils {
	
	public static final String PADRAO_DATA_BRASIL = "dd/MM/yyyy";
	private static final DateTimeFormatter FORMATADOR_BRASILEIRO = DateTimeFormatter.ofPattern(PADRAO_DATA_BRASIL); 
	
	public static String formatarPadraoBrasileiro(LocalDate data) {
		if (isNull(data)) {
			return null;
		}
		
		return data.format(FORMATADOR_BRASILEIRO);
	}

	/**
	 * Converte uma data em String no formato PADRAO_DATA_BRASIL para um LocalDate.
	 * @param dataString
	 * @return
	 */
	public static LocalDate converterData(String dataString) {
		try {
			return FORMATADOR_BRASILEIRO.parse(dataString, LocalDate::from);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Verifica se a data em String é valida.
	 * @param data
	 * @return
	 */
	public static Boolean isDataValida(String data) {
		return nonNull(converterData(data));
	}
}
