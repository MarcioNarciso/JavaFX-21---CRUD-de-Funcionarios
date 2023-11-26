package br.dev.marcionarciso.utils;

import java.util.Objects;

public abstract class StringUtils {

	public static Boolean isEmpty(String str) {
		return Objects.isNull(str) || str.length() == 0;
	}
	
}
