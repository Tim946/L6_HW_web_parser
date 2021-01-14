package ua.mainacademy.model;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String code;
    private String name;
    private int price;
    private int initPrice;
    private String url;
    private String group;
    private String seller;
    private String imageUrl;

}
