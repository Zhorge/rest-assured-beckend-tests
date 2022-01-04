package ru.annachemic.dto;

import lombok.*;

@Data // класс, содержащий данные - т.е. вкл. все необходимое для сериализации, десериализации
@NoArgsConstructor // при компиляции обязует создать конструктор без аргументов
@AllArgsConstructor // создается конструктор со всеми аргументами
@With // создает методы "with" - мы можем модифицировать нашь продукт во "fluent" стиле
@ToString // создает метод toString
public class Product {
    Integer id;
    String title;
    Integer price;
    String categoryTitle;

}
