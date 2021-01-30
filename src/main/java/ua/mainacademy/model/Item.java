package ua.mainacademy.model;


import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement(name="item")
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
