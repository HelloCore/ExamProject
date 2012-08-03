package th.ac.kbu.cs.ExamProject.Util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BeanUtils {
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	
	public static Object getDefaultValueIfNull(final Object value, final Object defaultValue) {
		Object result = defaultValue;
		if (value != null) {
			result = value;
		}
		return (result);
	}

	public static boolean isEmpty(final BigDecimal d) {
		final boolean b = BeanUtils.isNull(d);
		return (b);
	}

	public static boolean isEmpty(final Byte byt) {
		final boolean b = BeanUtils.isNull(byt);
		return (b);
	}

	public static boolean isEmpty(final Character c) {
		final boolean b = BeanUtils.isNull(c);
		return (b);
	}

	public static boolean isEmpty(final Double d) {
		final boolean b = BeanUtils.isNull(d);
		return (b);
	}

	public static boolean isEmpty(final Float f) {
		final boolean b = BeanUtils.isNull(f);
		return (b);
	}

	public static boolean isEmpty(final Integer integer) {
		final boolean b = BeanUtils.isNull(integer);
		return (b);
	}

	public static boolean isEmpty(final List<?> ls) {
		boolean b = true;
		if ((ls != null) && !ls.isEmpty()) {
			b = false;
		}
		return (b);
	}

	public static boolean isEmpty(final Long l) {
		final boolean b = BeanUtils.isNull(l);
		return (b);
	}

	public static boolean isEmpty(final Map<?, ?> map) {
		boolean b = true;
		if ((map != null) && !map.isEmpty()) {
			b = false;
		}
		return (b);
	}

	public static boolean isEmpty(final Number num) {
		final boolean b = BeanUtils.isNull(num);
		return (b);

	}

	public static boolean isEmpty(final Short s) {
		final boolean b = BeanUtils.isNull(s);
		return (b);
	}

	public static boolean isEmpty(final String st) {
		boolean b = true;
		if ((st != null) && (st.trim().length() > 0)) {
			b = false;
		}
		return (b);
	}

	public static boolean isEmpty(final StringBuilder st) {
		boolean b = true;
		if ((st != null) && (st.toString().trim().length() > 0)) {
			b = false;
		}
		return (b);
	}
	
	public static boolean isEmpty(final String[] st) {
		boolean b = true;
		if ((st != null) && (st.length > 0)) {
			b = false;
		}
		return (b);
	}

	public static boolean isNotEmpty(final BigDecimal d) {
		final boolean b = BeanUtils.isNotNull(d);
		return (b);
	}

	public static boolean isNotEmpty(final Byte byt) {
		final boolean b = BeanUtils.isNotNull(byt);
		return (b);
	}

	public static boolean isNotEmpty(final Character c) {
		final boolean b = BeanUtils.isNotNull(c);
		return (b);

	}

	public static boolean isNotEmpty(final Double d) {
		final boolean b = BeanUtils.isNotNull(d);
		return (b);

	}

	public static boolean isNotEmpty(final Float f) {
		final boolean b = BeanUtils.isNotNull(f);
		return (b);

	}

	public static boolean isNotEmpty(final Integer integer) {
		final boolean b = BeanUtils.isNotNull(integer);
		return (b);

	}

	public static boolean isNotEmpty(final List<?> ls) {
		boolean b = false;
		if ((ls != null) && !ls.isEmpty()) {
			b = true;
		}
		return (b);

	}

	public static boolean isNotEmpty(final Long l) {
		final boolean b = BeanUtils.isNotNull(l);
		return (b);

	}

	public static boolean isNotEmpty(final Map<?, ?> map) {
		boolean b = false;
		if ((map != null) && !map.isEmpty()) {
			b = true;
		}
		return (b);
	}

	public static boolean isNotEmpty(final Number num) {
		final boolean b = BeanUtils.isNotNull(num);
		return (b);

	}

	public static boolean isNotEmpty(final Object obj) {
		boolean b = false;
		if (obj != null) {
			b = true;
		}
		return (b);
	}

	public static boolean isNotEmpty(final Short s) {
		final boolean b = BeanUtils.isNotNull(s);
		return (b);
	}

	public static boolean isNotEmpty(final String st) {
		boolean b = true;
		if ((st == null) || (st.trim().length() == 0)) {
			b = false;
		}
		return (b);
	}
	
	public static boolean isNotEmpty(final String[] st) {
		boolean b = true;
		if ((st == null) || (st.length == 0)) {
			b = false;
		}
		return (b);
	}

	public static boolean isNotNull(final Object obj) {
		boolean b = false;
		if (obj != null) {
			b = true;
		}
		return (b);
	}

	public static boolean isNull(final Object obj) {
		boolean b = true;
		if (obj != null) {
			b = false;
		}
		return (b);
	}
	
	public static String toString(Object obj) {
		String r = "";
		if(BeanUtils.isNotNull(obj)) {
			r = obj.toString();
		}
		return r;
	}

	
	public static BigDecimal toBigDecimal(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return new BigDecimal(BeanUtils.toNumber(obj).toString());
	}

	public static BigInteger toBigInteger(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return new BigInteger(BeanUtils.toNumber(obj).toString());
	}

	
	public static Boolean toBoolean(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return Boolean.parseBoolean(obj.toString());
	}
	
	public static Byte toByte(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return BeanUtils.toNumber(obj).byteValue();
	}

	public static Double toDouble(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return BeanUtils.toNumber(obj).doubleValue();
	}

	public static Float toFloat(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return BeanUtils.toNumber(obj).floatValue();
	}

	public static Integer toInteger(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return BeanUtils.toNumber(obj).intValue();
	}

	public static Long toLong(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return BeanUtils.toNumber(obj).longValue();
	}
	
	private static Number toNumber(final Object obj) {
		try {
			return numberFormat.parse(obj.toString());
		} catch (final ParseException e) {
			throw new IllegalArgumentException("Cannot parse text [" + obj.toString() + "] to number", e);
		}
	}

	public static Short toShort(final Object obj) {
		if (BeanUtils.isNull(obj)) {
			return null;
		}
		return BeanUtils.toNumber(obj).shortValue();
	}

	public static String toString(final Number number) {
		String result = null;
		if (!BeanUtils.isNull(number)) {
			final DecimalFormat df = new DecimalFormat();
			df.setGroupingSize(0);
			df.setMaximumFractionDigits(0);
			result = df.format(number);
		}
		return result;
	}

	public static String toString(final Number number, final int fraction) {
		String result = null;
		if (!BeanUtils.isNull(number)) {
			final DecimalFormat df = new DecimalFormat();
			df.setGroupingSize(0);
			df.setMaximumFractionDigits(fraction);
			df.setMinimumFractionDigits(fraction);
			result = df.format(number);
		}
		return result;
	}
}
