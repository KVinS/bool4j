package com.danilov.bool4j.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Util {

	public static final void Log(final String message) {
		System.out.println(message);
	}
	
	public static final void LogNoLn(final String message) {
		System.out.print(message);
	}
	
	public static <A, B> Map<A, B> cloneMap(final Map<A, B> map) {
		Map<A, B> cloned = new HashMap<A, B>();
		Set<A> keySet = map.keySet();
		for (A key : keySet) {
			cloned.put(key, map.get(key));
		}
		return cloned;
	}
	      public static boolean bracketValidator(String s ) {
    int bracketCounter = 0;
 
    for ( int i = 0; i < s.length(); i++ ) {
        switch ( s.charAt(i) ) {
            case '(':
                bracketCounter++;
                break;
            case ')':
                bracketCounter--;
                break;
        }
}
        // Проверяем, что количество закрывающих скобок
        // не превышает количество открывающих.
        if ( bracketCounter < 0 ) {
            return false;
        }
    // Если в счётчике ещё остались значения,
    // то значит в конце строки не хватает закрывающих скобок.
    if ( bracketCounter != 0 ) {
        return false;
    }
    // Сообщаем, что всё ок.
    return true;
}
}
