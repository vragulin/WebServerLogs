
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;
import java.util.HashMap;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     private String dir;
     
     /**
      * Constructor: 
      * Initialize ArrayList to store LogEntry objects.
      * Initialize default path to file
      */
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
         dir = "C:/Users/vragu/OneDrive/Desktop/Proj/Java/Duke/Course3/WebLogProgram/data/";
     }
        
     /** 
      * Read file and parse lines as LogEntry objects.
      * Using FileResource to select a file to be processed. 
      */
     public void readFile(String filename) {
         FileResource fr = new FileResource(dir+filename);
         for (String line : fr.lines())
             records.add(WebLogParser.parseEntry(line));
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     /**
      * This method returns an integer representiing the number of unique IP addresses.
      */
     public int countUniqueIPs(){
         ArrayList<String> unique = new ArrayList<String>();
         for (LogEntry le : records){
             String ip = le.getIpAddress();
             if (!unique.contains(ip)) unique.add(ip);
         }
         return unique.size();
     }
     
     /**
      * This method should examine all the web log entries in records and print those LogEntries
      * that have status code greater than num
      */
     public void printAllHigherThanNum(int num){
         System.out.println("Status>"+num+":");
         for (LogEntry le : records){
             int status = le.getStatusCode();
             if (status > num) System.out.println(le);
         }
     }
     
     /**
      * This method accesses the web logs in records and returns and ArrayList of String of
      * unique IP addresses that were accessed on a given day (MMM DD).
      */
     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
         ArrayList<String> ipsOneDay = new ArrayList<String>();
         
         for (LogEntry le : records) {
             String leTime = le.getAccessTime().toString();
             String leIP = le.getIpAddress();
             if(leTime.indexOf(someday)!=-1)
                 if(!ipsOneDay.contains(leIP)) ipsOneDay.add(leIP);
         }
         return ipsOneDay;
     }
     
     /**
      * This method returns the number of unique IP addresses in records that have
      * status code in the range from log to high, inclusive.
      */
     public int countUniqueIPsInRange(int low, int hi) {
        ArrayList<String> unique = new ArrayList<String>();
        for (LogEntry le: records) {
            String ip = le.getIpAddress();
            if (!unique.contains(ip)) {
                int status = le.getStatusCode();
                if(status >= low && status <= hi) unique.add(ip);
            }
        }
        return unique.size();
     }
     
     /**
      * Maps an IP address to the number of times that IP address appears in the web log file.
      * @return map of IP to visit counts
      */
     public HashMap<String, Integer> countVisitsPerIP(){
         HashMap<String, Integer> map = new HashMap<String, Integer>();
         
         for (LogEntry le: records) {
             String ip = le.getIpAddress();
             if (!map.keySet().contains(ip)) map.put(ip,1);
             else map.put(ip, map.get(ip)+1);
         }
         return map;
     }
     
     /**
      * Helper method, produces a map of IP address to the number of times it appears in the file, but for a given day only.
      */
    public HashMap<String, Integer> countVisitsPerIP(String day){
         HashMap<String, Integer> map = new HashMap<String, Integer>();
         
         for (LogEntry le: records) {
             if (!getDay(le).equals(day)) continue;
             
             String ip = le.getIpAddress();
             if (!map.keySet().contains(ip)) map.put(ip,1);
             else map.put(ip, map.get(ip)+1);
         }
         return map;
     }
     
     /**
      * This method returns the maximum number of visits to this website over all IPs
      */
     public int mostNumberVisitsByIP(HashMap<String, Integer> map) {
         int maxVisits = 0;
         for (int visits : map.values())
             if (visits > maxVisits) maxVisits = visits;
             
         return maxVisits;
     }
     
     /**
      * This method returns the ip with most visits
      */
     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map) {
         int maxVisits = mostNumberVisitsByIP(map);
         ArrayList<String> list = new ArrayList<String>();
         for (String ip : map.keySet()) 
             if (map.get(ip) == maxVisits) list.add(ip);
        
         return list;
     }
     
     /**
      * Helper function to get day in format "MMM DD" from argument.
      */
     private String getDay(LogEntry le) {
         String date = le.getAccessTime().toString();
         return date.substring(4, 10);
     }
     
     /**
      * This method returns a map that uses records and maps days from web logs
      * to an ArrayList of IP addresses that occurred on that day.
      */
     public HashMap<String, ArrayList<String>> iPsForDays(){
         HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
         
         for (LogEntry le : records ) {
             String day = getDay(le);
             String ip = le.getIpAddress();
             
             if (!map.containsKey(day)) {
                 ArrayList<String> list = new ArrayList<String>();
                 list.add(ip);
                 map.put(day, list);
             } else {
                 //if(!map.get(day).contains(ip)) map.get(day).add(ip); <- this would be for unique addresses
                 map.get(day).add(ip);
             }
         }
         return map;
     }
     
     /**
      * This method returns the day with the most IP visits
      */
     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
         int maxSize = 0;
         String most = null;
         
         for (String day : map.keySet()) {
             int size = map.get(day).size();
             if (size > maxSize) {
                 maxSize = size;
                 most = day;
             }
         }
         return most;
     }
     /**
      * This method returns an ArrayList of IP addresses that had the highest number of accesses on a given day.
      */
     public ArrayList<String> iPsWithMostVisitsOnDay(String day){
         
         ArrayList<String> list = new ArrayList<String>();
         HashMap<String, Integer> visits = countVisitsPerIP(day);
                
         // find max visits count
         int maxCount = 0;
         for (int count : visits.values())
             if (count > maxCount) maxCount = count;
             
         // fill output list
         for (String ip: visits.keySet())
             if (visits.get(ip) == maxCount) list.add(ip);
             
         return list;
     }
}

