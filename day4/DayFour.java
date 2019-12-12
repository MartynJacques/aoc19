public class DayFour {
  public static boolean containsDouble(String intAsString) {
    for (int j = 0; j < intAsString.length() - 1; j++) {
      if (intAsString.charAt(j) == intAsString.charAt(j + 1)) {
        return true;
      }
    }
    return false;
  }

  public static boolean containsStrictDouble(String intAsString) {
    int consecutive = 1;
    char lastChar = intAsString.charAt(0);
    for (int j = 1; j < intAsString.length(); j++) {
      char curChar = intAsString.charAt(j);
      if (curChar == lastChar) {
        consecutive++;
      } else {
        if (consecutive == 2) {
          return true;
        }
        consecutive = 1;
      }
      lastChar = curChar;
    }

    // We could have had two consecutive chars at the end
    if (consecutive == 2) {
      return true;
    }

    return false;
  }

  public static boolean alwaysIncreasing(String intAsString) {
    for (int j = 0; j < intAsString.length() - 1; j++) {
      if (!(Character.getNumericValue(intAsString.charAt(j))
          <= Character.getNumericValue(intAsString.charAt(j + 1)))) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    String startString = "240298";
    int startInt = Integer.parseInt(startString);

    String endString = "784956";
    int endInt = Integer.parseInt(endString);

    int validCount = 0;
    for (int i = startInt; i < endInt; i++) {
      String intAsString = new Integer(i).toString();
      if (containsDouble(intAsString) && alwaysIncreasing(intAsString)) {
        validCount++;
      }
    }
    System.out.println("Part one:");
    System.out.println(validCount);

    validCount = 0;
    for (int i = startInt; i < endInt; i++) {
      String intAsString = new Integer(i).toString();
      if (containsStrictDouble(intAsString) && alwaysIncreasing(intAsString)) {
        validCount++;
      }
    }
    System.out.println("Part two:");
    System.out.println(validCount);
  }
}
