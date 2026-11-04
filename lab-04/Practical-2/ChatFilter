public class ChatFilter {
    public static String[] splitline(String line){
        String[] parts=line.split(" ",3);
        if(parts.length<=2)
            return null;
        return parts;
    }
    public static boolean hasKeyword(String message, String keyword){
        return message.toLowerCase().contains(keyword.toLowerCase());
    }
}