package ua.mainacademy;

import ua.mainacademy.model.Item;
import ua.mainacademy.service.ItemPageParsingService;
import ua.mainacademy.service.RouterService;

//import org.jsoup.nodes.*
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationRunner
{
    public static void main( String[] args )
    {
//        String url = "https://www.amazon.com/Samsung-Chromebook-Processor-32Gb-Emmc-XE350XBA-K01US/dp/B07XW266D9";
//        String url ="https://www.amazon.com/dp/B0872JDR2M";
        String keyWord = args.length == 0 ? "hp omen 15": args[0];
        String url ="https://www.amazon.com";
        String searchUrl= url + "/s?k=" + keyWord.replaceAll(" ","+") +"&page=1";
//        System.out.println(ItemPageParsingService.getItemFromPage(url));

        List<Item> items = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
        RouterService routerService = new RouterService(items, threads, searchUrl);
        threads.add(routerService);
        routerService.start();

//        do {
//            try {
//                Thread.sleep(100000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } while (!threadsAreNotActive(threads));
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Items were extracted. Amount =" + items.size());
        for (Item item:items ) {
            System.out.println("Parsed : " + item.toString());

        }
//        System.out.println(RouterService.parsePage(url));


    }

    private static boolean threadsAreNotActive(List<Thread> threads) {
        for (Thread thread :threads ) {
            if (thread.isAlive() || thread.getState().equals(Thread.State.NEW)){
                return false;
            }

        }
        return true;
    }
}
