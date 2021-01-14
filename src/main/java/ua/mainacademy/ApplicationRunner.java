package ua.mainacademy;

import ua.mainacademy.service.ItemPageParsingService;

public class ApplicationRunner
{
    public static void main( String[] args )
    {
        String url = "https://www.amazon.com/Samsung-Chromebook-Processor-32Gb-Emmc-XE350XBA-K01US/dp/B07XW266D9";

        System.out.println(ItemPageParsingService.getItemFromPage(url));


    }
}
