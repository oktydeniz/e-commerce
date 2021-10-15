## E-ticaret uygulaması

Bu uygulamada android tasarım örüntülerinden MVVM kullanandım. Veri tabanı olarak Firebase Realtime Database uzerinde islemler yaptım. Uygulama temel olarak kullanıcının veri tabanına bir ürünü girmesi, listelenmesi gibi islemleri yapar. işlemlerin kullanıcı arayüzünü kitlememesi için Kotlin Coroutines yapısını birkaç yerde kullandım. Sayfalar arası geçişleri Navigation Component, sayfalar arası veri alışverişi için Sade Args yapısını kullandım. 
 
## Veri tabanı yapısı 

Kullanıcılar ve Ürünle adı alıntan iki ana dal ve bu dallarda kullanıcıları ve ürünleri tutan ayrı dallar ekledim. Her bir veri kendisine ait benzersiz bir numara ile işlemlere tabi tutulur.

## XML yapısı ve kullanılan yapılar 

Her bir XML sayfası ve Kotlin sayfası ViewBinding ve DataBinding gibi yapılar ile kodlanmış, temsili görsel tutucu icin Glide kütüphanesini kullandım. Aynı zamanda XML sayfalarında ConstraintLayout, RecyclerView sınıfını kullanarak da görüntüleme işlemlerini gerçekleştirdim. 
