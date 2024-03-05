package eu.pinteam.rcpfos.widgets;

public class SevenSegmentDisplay {

    private static final String[][] DIGIT_REPRESENTATIONS = {
            {" _ ", "| |", "|_|"},  // 0
            {"   ", "  |", "  |"},  // 1
            {" _ ", " _|", "|_ "},  // 2
            {" _ ", " _|", " _|"},  // 3
            {"   ", "|_|", "  |"},  // 4
            {" _ ", "|_ ", " _|"},  // 5
            {" _ ", "|_ ", "|_|"},  // 6
            {" _ ", "  |", "  |"},  // 7
            {" _ ", "|_|", "|_|"},  // 8
            {" _ ", "|_|", " _|"}   // 9
    };
    
    public static final String[][] ERROR_REPRESENTATION = {
    	    {" _ ", "|_ ", "|_ "},
    	    {" _ ", "|_|", "| |"},
    	    {" _ ", "|_|", "| |"},
    	    {" _ ", "| |", "|_|"},
    	    {" _ ", "|_|", "| |"}
    	};
    
    public String convertErrorToSevenSegmentString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {  
            for (int j = 0; j < 5; j++) {  
                sb.append(ERROR_REPRESENTATION[j][i]);  
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public String convertToSevenSegmentString(double number) {
        String numberString = String.valueOf(number);

        String[] row1 = new String[numberString.length()];
        String[] row2 = new String[numberString.length()];
        String[] row3 = new String[numberString.length()];

        for (int i = 0; i < numberString.length(); i++) {
            char c = numberString.charAt(i);

            if (c == '.') {
                row1[i] = " ";
                row2[i] = " ";
                row3[i] = ".";
            } else if (Character.isDigit(c)) {
                int digit = Character.getNumericValue(c);
                row1[i] = DIGIT_REPRESENTATIONS[digit][0];
                row2[i] = DIGIT_REPRESENTATIONS[digit][1];
                row3[i] = DIGIT_REPRESENTATIONS[digit][2];
            } else {
                row1[i] = "   ";
                row2[i] = "   ";
                row3[i] = "   ";
            }
        }
        return String.join("", row1) + "\n" +
               String.join("", row2) + "\n" +
               String.join("", row3);
    }
}
