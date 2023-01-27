package StringCodes;

public class replaceNewLineWithSpace {
    public static void main(String[] args) {
        String in = "This is my text.\n\nAnd here is a new line";
        System.out.println(in);

        String out = in.replaceAll("[\\t\\n\\r]+"," ");
        System.out.println(out);
    }

}
