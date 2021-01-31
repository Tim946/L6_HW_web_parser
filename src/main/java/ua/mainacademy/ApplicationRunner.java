package ua.mainacademy;

import ua.mainacademy.model.Item;
import ua.mainacademy.service.RouterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationRunner
{
    public static void main( String[] args )
    {
        String keyWord = args.length == 0 ? "accer laptop": args[0];
        String url ="https://www.amazon.com";
        String searchUrl= url + "/s?k=" + keyWord.replaceAll(" ","+") +"&page=1";

        List<Item> items = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
        RouterService routerService = new RouterService(items, threads, searchUrl);
        threads.add(routerService);
        routerService.start();

        do {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!threadsAreNotActive(threads));

        System.out.println("Items were extracted. Amount =" + items.size());
        for (Item item:items ) {
            System.out.println();
            System.out.println("Parsed : " + item.toString());

        }
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
