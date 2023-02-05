Logic:
- [Feature] Różne tryby pracy wind:
  - Winda jeżdząca co 2 piętra
  - Windy jeżdzące w pewnym zakresie pięter (od 0-10, od 10-20 itd)
  - buffor dla windy jak szybko może przyjąć nowe zlecenie do realizacji
- [Idea] Śledzenie id wind żeby sie nie powtarzały?
- Elevator
    - [TODO] Więcej rzeczy do ustawiania - większa konfiguracja początkowego stanu
    - [Case] pickUp: fromFloor i toFloor są równe

Input:
- [TODO] Czytanie poleceń z terminala
- [TODO] Czytanie poleceń z pliku
- [TODO] Wyplucie outputu do pliku

CLI?
- [Idea] argumenty dodawane przy uruchomieniu aplikacji?


Serwer/SpringBoot?

Inne:
- Kolejność metod w klasach
- Readme
- Dokumentacja funkcji


Done:
- Logic:
  - [TODO] Błędne argumenty wywołania metod
  - [Case] pickUp: z X na X wsiadając na X -> 
  - [Case] zły user. Wzywa windę i naciska zły przycisk (na 5tym wciska górę, a w windzie 1)
  - [Case] Winda jedzie w górę żeby odebrać kogoś i zwieść na dół. W czasie jazdy w górę przychodzi zamówienie na jazdę w górę
  - [Case] ktos naciska i nie wsiada
  - [Case] Ktoś wsiada i naciska kilka przycisków - jak to imputowo ogarnąć?
      - ktoś naciska przycisk dopiero po przejechaniu kilku pięter
  - [Feature] Różne techniki schedulowania wind
  - [Feature] Różne tryby pracy wind:
      - Windy w jednym miejscu, lub rozrzucone po budynku
  - [Idea] generalizacja tych wszystkich ArrayList?
  - [TODO] triplet na bardziej znacząca nazwę klasy
