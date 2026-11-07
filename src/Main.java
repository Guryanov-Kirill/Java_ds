import HashSet.StringSet;

public class Main {
    public static void main(String[] args) {
        StringSet set = new StringSet();

        set.put("Привет");
        set.put("Мир");
        set.put("Java");
        set.put("1");

        System.out.println("size: " + set.size());
        System.out.println("Содержит Java: " + set.contains("Java"));
        System.out.println("Удаляем Java");
        boolean isRemoved = set.remove("Java");
        System.out.println("size: " + set.size());
        System.out.println("Содержит Java: " + set.contains("Java"));

        System.out.println("Очиcтка");
        set.clear();
        System.out.println("size: " + set.size());
    }
}