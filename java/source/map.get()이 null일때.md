computeIfAbsent

기존 코드
```java
if (itemsMenuMap.get(ItemMenu.ETC) == null) {
    itemsMenuMap.put(ItemMenu.ETC, new ArrayList<>());
}
itemsMenuMap.get(ItemMenu.ETC).add(item);
```

개선
```java
itemsMenuMap
    .computeIfAbsent(ItemMenu.ETC, k -> new ArrayList<>())
    .add(item);
```
