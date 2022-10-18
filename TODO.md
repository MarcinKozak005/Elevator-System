Logic:
- [Case] zły user. Wzywa windę i naciska zły przycisk (na 5tym wciska górę, a w windzie 1)
- [Case] pickUp: z X na X wsiadając na X
- [Feature] Różne techniki schedulowania wind
- [Feature] Różne tryby pracy wind:
  - Windy w jednym miejscu, lub rozrzucone po budynku
  - Winda jeżdząca co 2 piętra
  - Windy jeżdzące w pewnym zakresie pięter (od 0-10, od 10-20 itd)
  - buffor dla windy jak szybko może przyjąć nowe zlecenie do realizacji
- [Idea] Dodanie paneli kontrolnych jako klas?
- [Idea] Śledzenie id wind żeby sie nie powtarzały?
- Elevator
    - [TOOD] Więcej rzeczy do ustawiania - większa konfiguracja początkowego stanu
    - [Case] pickUp: fromFloor i toFloor są równe
    - [Idea] `bulkPickUp(ArrayList<Tuple<Integer, Integer>> collection)`, `ArrayList` na coś bardziej ogólnego?

Input:
- [TODO] Czytanie poleceń z terminala
- [TODO] Czytanie poleceń z pliku
- [TOOD] Wyplucie outputu do pliku

CLI?
- [Idea] argumenty dodawane przy uruchomieniu aplikacji?


Serwer/SpringBoot?

Inne:
- Readme
- Dokumentacja funkcji


Done:
- Logic:
  - [TODO] Błędne argumenty wywołania metod
