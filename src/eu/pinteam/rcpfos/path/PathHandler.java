package eu.pinteam.rcpfos.path;

public class PathHandler {

	private static final String ABSOLUTE_PATH = "C:\\Users\\AndradaValcan\\git\\rcpfos\\";
	
	private static final String ABSOLUTE_PATH_XMI_FILE = "C:\\Users\\AndradaValcan\\git\\rcpfos\\files\\data.xmi";

	private static final String DEFAULT_IMAGE = "images\\defaultimage.png";

	private static final String PATH_TO_CALENDAR_ICON = "icons\\calendar_month_FILL0_wght400_GRAD0_opsz24.png";

	private static final String PATH_TO_CLOCK_ICON = "icons\\alarm_FILL0_wght400_GRAD0_opsz24.png";

	private static final String PATH_TO_CALCULATOR_ICON = "icons\\calculate_FILL0_wght400_GRAD0_opsz24.png";

	private static final String PATH_TO_RATING_MENU_ICON = "icons\\menuRating.png";

	private static final String PATH_TO_RATING_RESTAURANT_ICON = "icons\\star.png";

	private static final String RATING_VALUE_PATH = "C:\\Users\\AndradaValcan\\git\\rcpfos\\icons\\ratingValueIcons\\";

	public static String getPathToRatingRestaurantIcon() {
		return ABSOLUTE_PATH + PATH_TO_RATING_RESTAURANT_ICON;
	}

	public static String getPathToRatingMenuIcon() {
		return ABSOLUTE_PATH + PATH_TO_RATING_MENU_ICON;
	}

	public static String getPathToCalendarIcon() {
		return ABSOLUTE_PATH + PATH_TO_CALENDAR_ICON;
		
	}
	
	public static String getAbsolutePathXmiFile() {
		return ABSOLUTE_PATH_XMI_FILE;
	}
	

	public static String getDefaultImage() {
		return ABSOLUTE_PATH + DEFAULT_IMAGE;
	}

	public static String getPathToClockIcon() {
		return ABSOLUTE_PATH + PATH_TO_CLOCK_ICON;
	}

	public static String getPathToCalculatorIcon() {
		return ABSOLUTE_PATH + PATH_TO_CALCULATOR_ICON;
	}

	public static String getAbsolutePath() {
		return ABSOLUTE_PATH;
	}

	public static String getRatingValuePath() {
		return RATING_VALUE_PATH;
	}

}
