import java.io.*;
import java.util.*;

public class CosineSimilarity {

    public static void main(String[] args) throws FileNotFoundException {
        
        //scan documents and create hash maps for each
        File doc1 = new File("doc1.txt");
        Map<String, Integer> map1 = makeMap(doc1);
        File doc2 = new File("doc2.txt");
        Map<String, Integer> map2 = makeMap(doc2);
        File doc3 = new File("doc3.txt");
        Map<String, Integer> map3 = makeMap(doc3);
        File doc4 = new File("doc4.txt");
        Map<String, Integer> map4 = makeMap(doc4);
        File doc5 = new File("doc5.txt");
        Map<String, Integer> map5 = makeMap(doc5);
        File doc6 = new File("doc6.txt");
        Map<String, Integer> map6 = makeMap(doc6);
        
        
        System.out.printf("Cosine Similarity between doc 1 and doc 2: " + "%.4f\n", cosSim(map1, map2));
        System.out.printf("Cosine Similarity between doc 3 and doc 3: " + "%.4f\n", cosSim(map3, map3));
        System.out.printf("Cosine Similarity between doc 3 and doc 4: " + "%.4f\n", cosSim(map3, map4));
        System.out.printf("Cosine Similarity between doc 5 and doc 6: " + "%.4f\n", cosSim(map5, map6));
        System.out.printf("Cosine Similarity between doc 3 and doc 5: " + "%.4f\n", cosSim(map3, map5));
        System.out.println();
        
        System.out.println("10 most frequently occurring words with their frequencies in doc 3: ");
        String[] a3 = frequent(map3);
        for (int i = 0; i < a3.length; i++){
            System.out.print(a3[i]);
            if (i % 2 != 0)
                System.out.println();
            else
                System.out.print(" : ");
        }
        System.out.println();
        
        System.out.println("10 most frequently occurring words with their frequencies in doc 4: ");
        String[] a4 = frequent(map4);
        for (int i = 0; i < a4.length; i++){
            System.out.print(a4[i]);
            if (i % 2 != 0)
                System.out.println();
            else
                System.out.print(" : ");
        }
        System.out.println();
        
        System.out.println("10 most frequently occurring words with their frequencies in doc 5: ");
        String[] a5 = frequent(map5);
        for (int i = 0; i < a5.length; i++){
            System.out.print(a5[i]);
            if (i % 2 != 0)
                System.out.println();
            else
                System.out.print(" : ");
        }
        System.out.println();
        
        System.out.println("10 most frequently occurring words with their frequencies in doc 6: ");
        String[] a6 = frequent(map6);
        for (int i = 0; i < a6.length; i++){
            System.out.print(a6[i]);
            if (i % 2 != 0)
                System.out.println();
            else
                System.out.print(" : ");
        }
        
    }
    

    //method to create hash map of document
    static Map<String, Integer> makeMap(File doc) throws FileNotFoundException {
        //key: word, value: count
        Map<String, Integer> m = new HashMap<String, Integer>();
        Scanner doc1 = new Scanner(doc);
        while (doc1.hasNext()){
            //transform all words to lowercase
            String word = doc1.next().toLowerCase();
            //omit punctation at the end of words
            if (word.charAt(word.length()-1) < 65 || word.charAt(word.length()-1) > 122)
                word = word.substring(0, word.length()-1);
            Integer count = m.get(word);
            boolean isStop = false;
            Scanner stop = new Scanner(new File("stopWords.txt"));
            
            //check if word is a stop word
            while (stop.hasNext()){
                String stopWord = stop.next();
                if (word.equals(stopWord))
                    isStop = true;
            }
            
            if (!isStop && word.length() > 2){
                if (count != null)
                    m.put(word, count + 1);
                else
                    m.put(word, 1);
            }
        }
        
        //return map
        return m;
    }
    

    //method to find cosine similarity between two documents
    static double cosSim(Map<String, Integer> a, Map<String, Integer> b){
        
        //calculate dot product
        double dot = 0;
        for (Map.Entry<String, Integer> entry : a.entrySet())
            for (Map.Entry<String, Integer> entry2 : b.entrySet())
                if (entry.getKey().equals(entry2.getKey()))
                    dot += entry.getValue() * entry2.getValue();
        
        double x = 0;
        double y = 0;
        
        //calculate vector lengths
        for (Map.Entry<String, Integer> entry : a.entrySet())
                x += entry.getValue() * entry.getValue();
        
        for (Map.Entry<String, Integer> entry : b.entrySet())
                y += entry.getValue() * entry.getValue();
                
        //return cosine similarity
        double cos = (dot / (Math.sqrt(x) * Math.sqrt(y)));
        return cos;
    }
    

    //method to find ten most frequent words of document
    static String[] frequent(Map<String, Integer> m){
        String key = "";
        Integer largest = 0;
        String[] ten = new String[20];
        //keep track of word with largest count
        for (int i = 0; i < 20; i+=2){
            for (Map.Entry<String, Integer> entry : m.entrySet()){
                if (entry.getValue() > largest){
                    largest = entry.getValue();
                    key = entry.getKey();
                }
                
            }
            //add key-value pair to an array and remove it from the map
            ten[i] = key;
            ten[i+1] = largest.toString();
            m.remove(key);
            largest = 0;
        }
        
        //return array containing the ten most common words with their frequencies
        return ten;
    }
    
}
