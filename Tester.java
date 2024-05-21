
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
        String fname;
        LogAnalyzer la;
         
        // Constructor
        public Tester() {
            //fname = "short-test_log.txt";
            //fname = "weblog3-short_log";
            fname = "weblog2_log";
            la = new LogAnalyzer();
            la.readFile(fname);
        }
        
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // complete method
        la.printAll();
    }
    
    public void testUniqueIp(){
        System.out.println("Unique IP's in file:\t"+la.countUniqueIPs());
    }

    public void testPrintAllHigherThanNum(){
        System.out.println("\nTesting Statuses1:");
        la.printAllHigherThanNum(400);

        System.out.println("\nTesting Statuses2:");
        la.printAllHigherThanNum(300);
    }
    
    public void testUniqueIPVisitsOneDay() {
        System.out.println("Ips on: Sep 27");
        System.out.println(la.uniqueIPVisitsOnDay("Sep 27").size());
        // System.out.println("Ips on: Sep 14");
        // System.out.println(la.uniqueIPVisitsOnDay("Sep 30").size());
    }
    
    public void testCountUniqueIPsInRange() {
        System.out.println("Testing ips in range 200-299");
        System.out.println(la.countUniqueIPsInRange(200, 299));
        System.out.println("Testing ips in range 300-399");
        System.out.println(la.countUniqueIPsInRange(300, 399));
    }
    
    public void testCountVisitsByIP() {
        HashMap<String, Integer> map = la.countVisitsPerIP();
        System.out.println(map);
    }
    
    public void testMostNumberVisitsByIP() {
        int max = la.mostNumberVisitsByIP(la.countVisitsPerIP());
        System.out.println("Max # visits from ip: "+max);
    }
    
    public void testIPsMostVisits() {
        ArrayList<String> list = la.iPsMostVisits(la.countVisitsPerIP());
        System.out.println("IPs with most visits: "+list);
    }
    
    public void testIPsForDays() {
        HashMap<String, ArrayList<String>> map = la.iPsForDays();
        System.out.println("Mapping day-visit:");
        System.out.println(map);
    }
    
    public void testDaysWithMostIPVisits(){
        HashMap<String, ArrayList<String>> map = la.iPsForDays();
        System.out.println("Day with the most visits");
        System.out.println(la.dayWithMostIPVisits(map));
    }
    
    public void testIPsWithMostVisitsOnDay() {
        ArrayList<String> list = la.iPsWithMostVisitsOnDay("Sep 29");
        System.out.println("IPs with most visits on Sep 29:");
        System.out.println(list);
    }
}
